package simulation.model.core;

import static simulation.common.globals.SimulGlobalConstants.*;

import org.junit.Assert;

import sim.engine.SimState;
import sim.engine.Stoppable;
import simulation.common.globals.SlotCurrentState;
import simulation.common.globals.SlotStatusStates;
import simulation.data.gisdata.routes.CarRouteCalulator;
import simulation.data.gisdata.routes.RouteCalulator;
import simulation.model.support.ParkingAgentStates;
import simulation.model.support.PathPoint;
import simulation.model.support.SimulRoute;
import simulation.model.support.Timing;
import simulation.model.wrapping.AgentChangeNotification;
import simulation.model.wrapping.ParkingActions;
import simulation.model.wrapping.StatisticNotification;

import com.esri.core.geometry.Point;

public class GuidedParkingAgent extends ParkingAgent {
	private static final long serialVersionUID = 2L;

	protected PathPoint currentPoint = null;
	protected RouteCalulator routeCalulator = null;
	protected SimulRoute pathToDestination = null;
	
	private SlotCurrentState selectedSlot = null;
	
	public GuidedParkingAgent(AgentCreationProfileData agentCreationProfileData, double agentSpeed){
		super(agentCreationProfileData, agentSpeed);
		routeCalulator = CarRouteCalulator.getInstance();
		currentPoint = new PathPoint(-1, entrancePoint.getPoint());
		description = GUIDED_AGENT_DESCRIPTION;
	}

	public void doStep(SimState state) {
		Timing stepTime = Timing.fromBaseTime(state.schedule.getTime());
		currentTime = stepTime.clone();
		
		ParkingModel parkingModel = (ParkingModel)state;
        
        switch (parkingAgentState) {
		case Starting:
			parkingAgentState = ParkingAgentStates.Searching;
			parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_ADDING, this.clone()));
			// Continue to next case
		case Tricked:
		case Searching:
			Point end;
			if (pathToDestination == null){
				// ------------ FIND A FREE SPOT CLOSE TO THE REQUIRED DESTINATION AND A PATH TO THAT SPOT
				end = goals.get(currentSchedule).getDestination().getPoint();
				selectedSlot = parkingModel.chooseAndTakeSlot(goals.get(currentSchedule).getDestination(), end, GUIDED_ALGORITHM_STARTING_DISTANCE, GUIDED_ALGORITHM_FINAL_DISTANCE, GUIDED_ALGORITHM_STEP_DISTANCE);
				if (selectedSlot == null){
					// ------------ NO PARKING PLACE FOUND -> LEAVING SIMULATION
					stop();
					parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
					System.out.println("UPPPPPPSSSS No parking found!!!!!");
					parkingAgentState = ParkingAgentStates.Leaving;
					break;
					// ----------------------------------------------------------
				}
				else{
					// ------------ A PARKING PLACE WAS FOUND -> GET A PATH TO IT
					currentDestDescription = goals.get(currentSchedule).getDestination().getDescription();	// Description
					double x = selectedSlot.getSlot().getCoordinates().getLongitude();
					double y = selectedSlot.getSlot().getCoordinates().getLatitude();
					end = new Point(x, y);
					pathToDestination = routeCalulator.getPath(currentPoint.getPoint(), end);
					if (pathToDestination == null){	// Communication error
						stop();
						parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
						System.out.println("Error getting route!!!!!");
						parkingAgentState = ParkingAgentStates.Leaving;
						break;
					}
					acummulatedDistance += pathToDestination.getShape_Length();
					// ----------------------------------------------------------
				}
			}
			else{
				// ------------ IT IS ALREADY MOVING ALONG A PATH TO THE SELECTED PARKING SPOT
				double x = selectedSlot.getSlot().getCoordinates().getLongitude();
				double y = selectedSlot.getSlot().getCoordinates().getLatitude();
				end = new Point(x, y);
				PathPoint oldPoint = currentPoint;
				currentPoint = pathToDestination.calculatePointForDistance(currentPoint, getSpeed()); // Assuming speed in meters / time unit
				if ((pathToDestination.calculateDistance(currentPoint.getPoint(), end) > 10)
						&&(!(oldPoint.getPoint().equals(currentPoint.getPoint())))
						&&(!(pathToDestination.isLast(currentPoint)))){
					// ------------ IF IT IS NOT AT THE END OF THE PATH, IT KEEPS MOVING
					parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
				}
				else{
					// ------------ IF IT IS AT THE END OF THE PATH, IT TRIES TO PARK
					if (selectedSlot.getStatus() == SlotStatusStates.REQUESTED.getValue()){
						// ------------ IF IT IS STILL FREE, IT PARKS
						stop();
						parkingAgentState = ParkingAgentStates.Parked;
						Timing departureTimeLap = goals.get(currentSchedule).getDepartureTimeLap();
						Timing wakeUpFromParking = stepTime.getAddedTime(departureTimeLap);
						state.schedule.scheduleOnce(Timing.toBaseTime(wakeUpFromParking), this);
						currentPoint = new PathPoint(-1, end);
						parkingModel.changeSlotStatus(selectedSlot, SlotStatusStates.OCCUPIED);
						parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
						pathToDestination = null;
						currentSchedule++;
						parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_PARKING, this.clone()));
					}
					else{
						// ------------ AN AGENT OF ANOTHER KIND ALREADY PARKED AT THE SELECTED SPOT
						parkingModel.notifyStaticticsChange(new StatisticNotification(TRIKED_STATISTICS_KEY, 1));
						parkingAgentState = ParkingAgentStates.Tricked;
						parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_MOVING, this.clone()));
						currentPoint = new PathPoint(-1,
								new Point(currentPoint.getPoint().getX(), currentPoint.getPoint().getY()));
						pathToDestination = null;
						// ----------------------------------------------------------
					}
				}
			}
			break;
		case Parked:
			parkingModel.changeSlotStatus(selectedSlot, SlotStatusStates.AVAILABLE);
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
		case Leaving:
			if (pathToDestination == null){
				currentDestDescription = entrancePoint.getDescription();
				pathToDestination = routeCalulator.getPath(currentPoint.getPoint(), entrancePoint.getPoint());
				if (pathToDestination == null){	// Communication error
					stop();
					parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
					System.out.println("Error getting route!!!!!");
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
					parkingModel.notifyAgentChange(new AgentChangeNotification(ParkingActions.AGENT_LEAVING, this.clone()));
					parkingModel.notifyStaticticsChange(new StatisticNotification(DISTANCE_GUIDED_STATISTICS_KEY, this.getAcummulatedDistance()));
				}
			}
			break;
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
