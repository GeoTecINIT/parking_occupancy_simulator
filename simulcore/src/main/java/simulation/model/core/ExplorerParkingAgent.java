package simulation.model.core;

import java.util.List;

import org.junit.Assert;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.util.Bag;
import simulation.common.globals.SlotCurrentState;
import simulation.common.globals.SlotStatusStates;
import simulation.data.gisdata.routes.CarRouteCalulator;
import simulation.model.support.BuildingEntranceDestination;
import simulation.model.support.ParkingAgentStates;
import simulation.model.support.PathPoint;
import simulation.model.support.SimulRoute;
import simulation.model.support.Timing;
import simulation.model.wrapping.AgentChangeNotification;
import simulation.model.wrapping.ParkingActions;
import simulation.model.wrapping.StatisticNotification;

import com.esri.core.geometry.Point;

import static simulation.common.globals.SimulGlobalConstants.*;

public class ExplorerParkingAgent extends ParkingAgent {
	private static final long serialVersionUID = 2L;

	protected PathPoint currentPoint = null;
	protected CarRouteCalulator routeCalulator = null;
	protected SimulRoute pathToDestination = null;
	
	private List<BuildingEntranceDestination> currentBuildingEntraces = null;
	private List<String> buildingsForSeach = null;
	private String currentBuilding;
	
	private final double MAX_WALKING_DISTANCE = 50;			// meters	HAY QUE HACER CALCULO CON SERVICIO ARCGIS
	double maxWalkingDistance = MAX_WALKING_DISTANCE;
	private final double VISIBILITY_SEARCH_DISTANCE = 40;	// meters
	private final double PICKING_DISTANCE = 12;				// meters
	
	private SlotCurrentState chosenSlot = null;
	private Point end = null;
	private Point prevPoint = null;
	private boolean alreadyChosen = false;
	
	public ExplorerParkingAgent(AgentCreationProfileData agentCreationProfileData, double agentSpeed){
		super(agentCreationProfileData, agentSpeed);
		routeCalulator = CarRouteCalulator.getInstance();
		currentPoint = new PathPoint(-1, entrancePoint.getPoint());
		description = EXPLORER_AGENT_DESCRIPTION;
	}

	public void doStep(SimState state) {
		Timing stepTime = Timing.fromBaseTime(state.schedule.getTime());
		currentTime = stepTime.clone();
		
		ParkingModel parkingModel = (ParkingModel)state;
        
        switch (parkingAgentState) {
        // ---------------------------------------------------------------------------
		case Starting:
			parkingAgentState = ParkingAgentStates.Searching;
			parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_ADDING, this.clone()));
			// -----------------------------
			currentBuilding = goals.get(currentSchedule).getDestination().getBuilding();
			buildingsForSeach = parkingModel.destinationMap.getBuildings();
			buildingsForSeach.remove(currentBuilding);
			currentBuildingEntraces = parkingModel.destinationMap.getDestinationsFor(currentBuilding);
			currentBuildingEntraces.remove(goals.get(currentSchedule).getDestination());
			// Continue to next case
		// ---------------------------------------------------------------------------
		case Searching:
			prevPoint = currentPoint.getPoint();
			if (pathToDestination == null){
				end = goals.get(currentSchedule).getDestination().getPoint();
				currentDestDescription = goals.get(currentSchedule).getDestination().getDescription();	// Description
				pathToDestination = routeCalulator.getPath(currentPoint.getPoint(), end);
				if (pathToDestination == null){	// Communication error
					stop();
					parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
					parkingModel.notifySimpleError(ParkingAgent.ERROR_GETTING_ROUTE);
					parkingAgentState = ParkingAgentStates.Leaving;
					break;
				}
				maxWalkingDistance = MAX_WALKING_DISTANCE;
				prevPoint = null;
			}
			currentPoint = pathToDestination.calculatePointForDistance(currentPoint, getSpeed()); // Assuming speed in meters / time unit
			acummulatedDistance += getSpeed();
			if (alreadyChosen == true){
				// Heading to the chosen slot
				if ((pathToDestination.isLast(currentPoint))){	
					if (chosenSlot.getStatus() != SlotStatusStates.OCCUPIED.getValue()){
						// Let's take it
						alreadyChosen = false;
						parkingModel.changeSlotStatus(chosenSlot, SlotStatusStates.OCCUPIED);
						stop();
						parkingAgentState = ParkingAgentStates.Parked;
						Timing departureTimeLap = goals.get(currentSchedule).getDepartureTimeLap();
						Timing wakeUpFromParking = stepTime.getAddedTime(departureTimeLap);
						state.schedule.scheduleOnce(Timing.toBaseTime(wakeUpFromParking), this);
						currentPoint = new PathPoint(-1, chosenSlot.getPoint());
						parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
						pathToDestination = null;
						currentSchedule++;
						parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_PARKING, this.clone()));
						break;
					}
					else{
						Bag candidates = parkingModel.getNearGeomCircleSlot(currentPoint.getPoint(), prevPoint, PICKING_DISTANCE, 100);
						if (candidates.numObjs > 0){
							// If it is close enough to occupy the spot
							chosenSlot = parkingModel.chooseGeomSlot(candidates, currentPoint.getPoint(), PICKING_DISTANCE);
							if (chosenSlot != null){	// A free slot was found
								alreadyChosen = false;
								parkingModel.changeSlotStatus(chosenSlot, SlotStatusStates.OCCUPIED);
								stop();
								parkingAgentState = ParkingAgentStates.Parked;
								Timing departureTimeLap = goals.get(currentSchedule).getDepartureTimeLap();
								Timing wakeUpFromParking = stepTime.getAddedTime(departureTimeLap);
								state.schedule.scheduleOnce(Timing.toBaseTime(wakeUpFromParking), this);
								currentPoint = new PathPoint(-1, chosenSlot.getPoint());
								parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
								pathToDestination = null;
								currentSchedule++;
								parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_PARKING, this.clone()));
								break;
							}
						}
						// Some else got there first
						alreadyChosen = false;
						end = goals.get(currentSchedule).getDestination().getPoint();
						currentPoint = new PathPoint(-1, currentPoint);
						pathToDestination = routeCalulator.getPath(currentPoint.getPoint(), end);
						if (pathToDestination == null){	// Communication error
							stop();
							parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
							parkingModel.notifySimpleError(ParkingAgent.ERROR_GETTING_ROUTE);
							parkingAgentState = ParkingAgentStates.Leaving;
							break;
						}
						parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
						break;
					}
				}
			}
			else{
				double distanceToEnd = pathToDestination.calculateDistance(currentPoint.getPoint(), end);
				if ((distanceToEnd <= maxWalkingDistance)){
					// Look for nearby available parking spots
					Bag candidates = parkingModel.getNearGeomCircleSlot(currentPoint.getPoint(), prevPoint, VISIBILITY_SEARCH_DISTANCE, 100);
					if (candidates.numObjs > 0){
						// If it is close enough to occupy the spot
						chosenSlot = parkingModel.chooseGeomSlot(candidates, currentPoint.getPoint(), VISIBILITY_SEARCH_DISTANCE);
						if (chosenSlot != null){	// A free slot was found
							double distanceToSlot = pathToDestination.calculateDistance(currentPoint.getPoint(), chosenSlot.getPoint());
							if (distanceToSlot <= PICKING_DISTANCE){
								alreadyChosen = false;
								parkingModel.changeSlotStatus(chosenSlot, SlotStatusStates.OCCUPIED);
								stop();
								parkingAgentState = ParkingAgentStates.Parked;
								Timing departureTimeLap = goals.get(currentSchedule).getDepartureTimeLap();
								Timing wakeUpFromParking = stepTime.getAddedTime(departureTimeLap);
								state.schedule.scheduleOnce(Timing.toBaseTime(wakeUpFromParking), this);
								currentPoint = new PathPoint(-1, chosenSlot.getPoint());
								parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
								pathToDestination = null;
								currentSchedule++;
								parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_PARKING, this.clone()));
								break;
							}
							else{
								alreadyChosen = true;
								end = chosenSlot.getPoint();
								currentPoint = new PathPoint(-1, pathToDestination.getNextPointInPath(currentPoint));
								pathToDestination = routeCalulator.getPath(currentPoint.getPoint(), end);
								if (pathToDestination == null){	// Communication error
									stop();
									parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
									parkingModel.notifySimpleError(ParkingAgent.ERROR_GETTING_ROUTE);
									parkingAgentState = ParkingAgentStates.Leaving;
									break;
								}
								parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
								break;
							}
						}	// else it keeps moving
					}
				}
			}
			if ((pathToDestination.isLast(currentPoint))){	// End of the route and no parking was found
				if ((currentBuildingEntraces.size() == 0)&&(buildingsForSeach.size() > 0)){
					currentBuilding = buildingsForSeach.remove(0);
					currentBuildingEntraces = parkingModel.destinationMap.getDestinationsFor(currentBuilding);
				}
				if (currentBuildingEntraces.size() > 0){
					BuildingEntranceDestination destP = currentBuildingEntraces.remove(0);
					end = destP.getPoint();
					currentPoint = new PathPoint(-1,
							new Point(currentPoint.getPoint().getX(), currentPoint.getPoint().getY()));
					pathToDestination = routeCalulator.getPath(currentPoint.getPoint(), end);
					if (pathToDestination == null){	// Communication error
						stop();
						parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
						parkingModel.notifySimpleError(ParkingAgent.ERROR_GETTING_ROUTE);
						parkingAgentState = ParkingAgentStates.Leaving;
						break;
					}
					maxWalkingDistance = 1000000;
				}
				else{
					stop();
					parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
					parkingModel.notifySimpleError(ParkingAgent.NO_PARKING_FOUND);
					break;
				}
			}
			parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
			break;
		// ---------------------------------------------------------------------------
		case Parked:
			parkingModel.changeSlotStatus(chosenSlot, SlotStatusStates.AVAILABLE);
			if (currentSchedule >= goals.size()){
				parkingAgentState = ParkingAgentStates.Leaving;
				pathToDestination = null;
			}
			else{
				parkingAgentState = ParkingAgentStates.Searching;
			}
			Timing nextStepTime = stepTime.getAddedTime(Timing.getBasicUnit());
			Stoppable stoppable = state.schedule.scheduleRepeating(Timing.toBaseTime(nextStepTime), this);
			enableStop(stoppable);
			parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
			break;
		// ---------------------------------------------------------------------------
		case Leaving:
			if (pathToDestination == null){
				currentDestDescription = entrancePoint.getDescription();
				pathToDestination = routeCalulator.getPath(currentPoint.getPoint(), entrancePoint.getPoint());
				if (pathToDestination == null){	// Communication error
					stop();
					parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
					parkingModel.notifySimpleError(ParkingAgent.ERROR_GETTING_ROUTE);
					parkingAgentState = ParkingAgentStates.Leaving;
					break;
				}
				currentPoint = new PathPoint(-1, currentPoint.getPoint());
			}
			else{
				currentPoint = pathToDestination.calculatePointForDistance(currentPoint, getSpeed()); // Assuming speed in meters / time unit
				if (!(pathToDestination.isLast(currentPoint))){
					parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
				}
				else{
					stop();
					parkingModel.notifyStaticticsChange(new StatisticNotification(DISTANCE_EXPLORER_STATISTICS_KEY, this.getAcummulatedDistance()));
					parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
				}
			}
			break;
		// ---------------------------------------------------------------------------
		default:
			Assert.fail("Problems");
			break;
		}
	}

	@Override
	public Point getPosition() {
		return currentPoint.getPoint();
	}
}
