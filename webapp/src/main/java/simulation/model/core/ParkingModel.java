package simulation.model.core;

import static simulation.common.globals.SimulGlobalConstants.SIMULATION_CAR_SPEED;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;

import org.junit.Assert;

import sim.engine.Schedule;
import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.geo.GeomVectorField;
import sim.util.Bag;
import sim.util.geo.MasonGeometry;
import simulation.assigner.*;
import simulation.common.globals.RandomGenerator;
import simulation.common.globals.SlotCurrentState;
import simulation.common.globals.SlotData;
import simulation.common.globals.SlotStatusStates;
import simulation.data.configs.profiles.AgentDataLoader;
import simulation.data.configs.profiles.AgentProfile;
import simulation.data.configs.profiles.AgentsConfigsLoader;
import simulation.data.configs.profiles.Configs;
import simulation.data.gisdata.places.DestinationsGetter;
import simulation.data.gisdata.places.EntrancesGetter;
import simulation.model.support.BuildingEntranceDestination;
import simulation.model.support.Timing;
import simulation.model.wrapping.HandyObservable;
import simulation.model.wrapping.ModelEngine;
import simulation.model.wrapping.ModelNotification;
import simulation.model.wrapping.ModelNotificationType;

import com.esri.core.geometry.Point;
import com.vividsolutions.jts.geom.*;

public class ParkingModel extends SimState implements ModelEngine{
    private static final long serialVersionUID = 1;

    private AgentsConfigsLoader configsLoader;
	private List<SlotCurrentState> slots;
	private GeomVectorField soltsField;
	private HashMap<Integer, SlotCurrentState> quickSlotFind = new HashMap<>();
	private GeometryFactory fact = new GeometryFactory();
	private SlotChooser assigner;
	private List<AgentPlanInitiator> agentsInitiators = new ArrayList<AgentPlanInitiator>();	// Necesario almacenarlo para planificaciones futuras
	
	private boolean isSlotInitNeeded;
	private boolean setSlotsFreeOnInit;
	private boolean useRealTime;
	private Timing zeroTime;
	
	public DestinationMap destinationMap;
	
	// -----------------------------------
	
	public ParkingModel(long seed, AgentsConfigsLoader configsLoader, List<SlotCurrentState> slots, double timeInterval, double guidedAgentProportion,
			boolean slotInitNeeded, boolean setSlotsFreeOnInit, int runsAmount, boolean useRealTime){
		super(seed, new ExtendedSchedule());
        
        this.configsLoader = configsLoader;
        this.slots = slots.subList(0, slots.size());
        for (SlotCurrentState slotCurrentState : slots) {
        	quickSlotFind.put(slotCurrentState.getSlot().getObjectId(), slotCurrentState);
		}
        this.timeInterval = (timeInterval > 0) ? timeInterval: 0;
        this.guidedAgentProportion = guidedAgentProportion;
        this.isSlotInitNeeded = slotInitNeeded;
        this.setSlotsFreeOnInit = setSlotsFreeOnInit;
        this.runsAmount = runsAmount; 
        this.useRealTime = useRealTime;
        
        this.assigner = SlotChooserInstancer.getSlotChooserInstance();
        
        createNotificators();
		
		// Make the slots space ready for searches
		soltsField = new GeomVectorField();
		for (SlotCurrentState slotCurrentState : slots) {
			double x = slotCurrentState.getSlot().getCoordinates().getLongitude();
			double y = slotCurrentState.getSlot().getCoordinates().getLatitude();
			MasonGeometry location = new MasonGeometry(fact.createPoint(new Coordinate(x, y)));
			location.setUserData(slotCurrentState);
			soltsField.addGeometry(location);
		}
	}

    public void init() {      
        try{
        	this.agentsInitiators.clear();
	        Configs configs = configsLoader.readConfigs();
	        
	        List<AgentProfile> profiles = configs.getProfiles();
	        destinationMap = new DestinationMap(DestinationsGetter.getInstance().getEntries());
	        
	        AgentDataLoader agentLoader = new AgentDataLoader(EntrancesGetter.getInstance(), destinationMap);
	        for (AgentProfile agentProfileXML : profiles) {
	        	List<AgentPlanInitiator> profileInitiators = agentLoader.defineAgentsFromProfile(agentProfileXML, SIMULATION_CAR_SPEED);
            	this.agentsInitiators.addAll(profileInitiators);
	        }
	        scheduleInitiators();
	        
	        if (setSlotsFreeOnInit){
		        // Set all spot to free state
		        for (SlotCurrentState slotCurrentState : slots) {
		        	slotCurrentState.setStatus(SlotStatusStates.AVAILABLE.getValue());
				}
	        }
	        if (isSlotInitNeeded){
	        	// Notify existence of Parking Places
	        	schedule.scheduleOnce(Schedule.EPOCH, new SchedulableInitializator(slots));
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        	Assert.fail("Problems");
        }
    }
    
    private void scheduleInitiators(){
    	for (AgentPlanInitiator agentPlanInitiator : agentsInitiators) {
    		Timing startigTime = agentPlanInitiator.getAgentCreationData().getStartingTime();
    		if (startigTime.compareTo(zeroTime) > 0){
	            double baseTime = Timing.toBaseTime(startigTime);
	            schedule.scheduleOnce(baseTime, agentPlanInitiator);
    		}
		}
    }

	public void addAgent(AgentCreationProfileData agentCreationData) {
		double baseTime = Timing.toBaseTime(agentCreationData.getStartingTime().getAddedTime(Timing.getBasicUnit()));
		ParkingAgentFactory factory = chooseAgentFactory();
		ParkingAgent agent = factory.createParkingAgent(agentCreationData, SIMULATION_CAR_SPEED);
        Stoppable stoppable = schedule.scheduleRepeating(baseTime, agent);
        agent.enableStop(stoppable);
	}
	
	private ParkingAgentFactory chooseAgentFactory(){
		double random = RandomGenerator.getInstance().nextDouble(true, false);
		if (random < guidedAgentProportion){
			return GuidedParkingAgentFactory.getInstance();
		}
		return ExplorerParkingAgentFactory.getInstance();
	}
    
    public void run() throws InterruptedException{
    	int i = 0;
    	double prevSimulTime = 0;
    	long prevRealTime = (new Date()).getTime();
    	
    	do{
	    	do{
	    		double simulTime = ((ExtendedSchedule)(schedule)).getMinTime();
	    		long realTime = (new Date()).getTime();
	    		
	    		globalNotificator.notifyObservers(new ModelNotification(ModelNotificationType.STEP, prevSimulTime));

	    		if (useRealTime == true){
		    		double simulTimeDiff = simulTime - prevSimulTime;
		    		long realTimeDiff = realTime - prevRealTime;
		    		
		    		long expectedTime = (long)(simulTimeDiff * getTimeInterval());
		    		long sleepTime = (expectedTime - realTimeDiff);
		    		
		    		// Solo esperar si ha pasado menos tiempo del necesario
		    		if ((prevSimulTime!=0)&&(sleepTime > 0)){
		    			Thread.sleep(sleepTime);
		    		}
	    		}
	    		else{
	    			Thread.sleep((int)(getTimeInterval()));
	    		}

	    		prevSimulTime = simulTime;
	    		prevRealTime = realTime;
	    		
	    		if (!schedule.step(this)) { break; }
	    		if (cancelled) { break; }
	    	}while(true);

	    	if (cancelled) { break; }
	    	
	    	globalNotificator.notifyObservers(new ModelNotification(ModelNotificationType.ITERATION, (i + 1)));
	    	
	    	if (++i >= runsAmount) { break; }
	    	
	    	schedule.reset();
    		scheduleInitiators();
	    	
	    	if (setSlotsFreeOnInit){	// Set all spot to free state
		        for (SlotCurrentState slotCurrentState : slots) {
		        	slotCurrentState.setStatus(SlotStatusStates.AVAILABLE.getValue());
				}
	        }
	    	
    	}while(true);
    	finish();
    	if (!cancelled){
    		globalNotificator.notifyObservers(new ModelNotification(ModelNotificationType.FINISHED, runsAmount));
    	}
    }
    
    public void start(){
        super.start();
        if (useRealTime == true){
	    	Calendar calendar = Calendar.getInstance();
	    	Timing now = new Timing(calendar.get(Calendar.DAY_OF_WEEK) - 1, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
	    	zeroTime = now;
        }
        else{
        	zeroTime = new Timing(0, 0, 0, 0);
        }
    	init();
    }

	@Override
	public Integer call() throws Exception {
        start();
		run();
		return 0;
	}
	
	// -----------------------------------
	// PARKING SEARCH UTILITIES 
	
	public synchronized SlotCurrentState chooseAndTakeSlot(BuildingEntranceDestination destination, Point point, double initialDistance, double finalDistance, double stepDistance){
		SlotData chosen = chooseSlot(destination, point, initialDistance, finalDistance, stepDistance);
		SlotCurrentState toChange = null;
		if (chosen != null){
			toChange = quickSlotFind.get(chosen.getObjectId());
			changeSlotStatus(toChange, SlotStatusStates.REQUESTED);
		}
		return toChange;
	}
	
	public synchronized SlotData chooseSlot(BuildingEntranceDestination destination, Point point, double initialDistance, double finalDistance, double stepDistance){
		return assigner.chooseSlot(destination, soltsField, point, initialDistance, finalDistance, stepDistance);
//		return assigner.chooseSlot(slots, point.getX(), point.getY(), initialDistance, finalDistance, stepDistance);
	}
	
	public synchronized Bag getNearGeomCircleSlot(Point currPoint, Point prevPoint, double distance, double width){
		Geometry center = fact.createPoint(new Coordinate(currPoint.getX(), currPoint.getY()));
		Geometry areaToExplore = center.buffer(distance);	
		Bag candidates = soltsField.getCoveredObjects(new MasonGeometry(areaToExplore));
		return candidates;
	}
	
	public synchronized Bag getNearGeomSquareSlot(Point currPoint, Point prevPoint, double distance, double width){
		double x1 = prevPoint.getX();
		double y1 = prevPoint.getY();
		double x2 = currPoint.getX();
		double y2 = currPoint.getY();
		
		double vx = x2 - x1;
		double vy = y2 - y1;
		double len = Math.sqrt(vx*vx + vy*vy);
		double ux = - vy / len;
		double uy = vx / len;
		double rx = vx / len;
		double ry = vy / len;
		
		// Described in counter-clockwise order
		double p1x = x1;
		double p1y = y1;
		double p2x = x1 - width*ux;
		double p2y = y1 - width*uy;
		double p3x = x2 + distance*rx;
		double p3y = y2 + distance*ry;
		double p4x = p3x - width*ux;
		double p4y = p3y - width*uy;
		
		Coordinate [] squateCoords = new Coordinate[]{
				new Coordinate(p1x, p1y),
				new Coordinate(p2x, p2y),
				new Coordinate(p3x, p3y),
				new Coordinate(p4x, p4y),
				new Coordinate(p1x, p1y)
		};
		
		Geometry areaToExplore = fact.createPolygon(squateCoords);		
		Bag candidates = soltsField.getCoveredObjects(new MasonGeometry(areaToExplore));
		return candidates;
	}
	
	public synchronized SlotCurrentState chooseGeomSlot(Bag candidates, Point destPoint, double aceptableDistance){
		SlotCurrentState chosen = null;
		Geometry dest = fact.createPoint(new Coordinate(destPoint.getX(), destPoint.getY()));
		double minDistance = Double.MAX_VALUE;
		for (Object object : candidates) {
			MasonGeometry location = (MasonGeometry) object;
			SlotCurrentState slot = (SlotCurrentState)(location.getUserData());
			if (slot.getStatus() != SlotStatusStates.OCCUPIED.getValue()){
				double pointDistance = dest.distance(location.geometry);
				if (pointDistance <= aceptableDistance){
					if (minDistance >= pointDistance){
						minDistance = pointDistance;
						chosen = slot;
					}
				}
			}
		}
		return chosen;
	}
	
	public void changeSlotStatus(SlotCurrentState slot, SlotStatusStates state){
		slot.setStatus(state.getValue());
		notifySlotChange(slot);
	}
	
	// -----------------------------------
	// SIMULATION GLOBAL PARAMETERS 
	
	private double timeInterval = 1;
	private int runsAmount = 1;
	private int currentRun = 0;
	private double guidedAgentProportion = 0.5;
	
	public synchronized double getGuidedAgentProportion() {
		return guidedAgentProportion;
	}

	public synchronized void setGuidedAgentProportion(double guidedAgentProportion) {
		this.guidedAgentProportion = guidedAgentProportion;
	}

	public synchronized double getTimeInterval() {
		return timeInterval;
	}

	public synchronized void setTimeInterval(double timeInterval) {
		this.timeInterval = timeInterval;
	}
	
	public int getRunsAmount() {
		return runsAmount;
	}
	
	public int getCurrentRun() {
		return currentRun;
	}
	
	public double getCarSpeed() {
		return SIMULATION_CAR_SPEED * 3.6;	//	KM/H
	}
	
	public Timing getCurrentTime() {
		return Timing.fromBaseTime(schedule.getTime());	//	KM/H
	}
	
	// --------------------------------------------
	// THREAD CANCELLATION

	private boolean cancelled = false;

	@Override
	public void setCanceledFlag() {
		cancelled  = true;
	}
	
	// -----------------------------------
	// Utilities 
	
	public void notifySimpleError(String msg){
		System.err.println(msg);
	}

	// ============================================================
	// NOTIFICATIONS (TO UPDATERS)
	
	private HandyObservable slotChangeNotificator;
	private HandyObservable agentChangeNotificator;
	private HandyObservable statisticsNotificator;
	
	private HandyObservable globalNotificator;
	
	private void createNotificators(){
		slotChangeNotificator = new HandyObservable();
		agentChangeNotificator = new HandyObservable();
		statisticsNotificator = new HandyObservable();
		
		globalNotificator = new HandyObservable();
	}
	
	@Override
	public void addSlotChangeUpdater(Observer updater) {
		slotChangeNotificator.addObserver(updater);
	}
	
	@Override
	public void addAgentChangeUpdater(Observer updater) {
		agentChangeNotificator.addObserver(updater);
	}

	@Override
	public void addStaticticsChangeUpdater(Observer updater) {
		statisticsNotificator.addObserver(updater);
	}
	
	@Override
	public void notifySlotChange(Object arg) {
		slotChangeNotificator.notifyObservers(arg);
	}
	
	@Override
	public void notifyAgentChange(Object arg) {
		agentChangeNotificator.notifyObservers(arg);
	}

	@Override
	public void notifyStaticticsChange(Object arg) {
		statisticsNotificator.notifyObservers(arg);
	}
	
	//---

	public void addGlobalUpdater(Observer updater) {
		globalNotificator.addObserver(updater);
	}
	
	public void notifyGlobal(Object arg) {
		globalNotificator.notifyObservers(arg);
	}
	
	// ============================================================
}
