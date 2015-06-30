package simulation.model.wrapping;

import static simulation.common.globals.SimulGlobalConstants.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import simulation.common.globals.SimulationState;
import simulation.common.globals.SlotCurrentState;
import simulation.common.globals.SlotData;
import simulation.common.smartparkingws.SmartParkingWServices;
import simulation.data.configs.common.MainPropertiesLoader;
import simulation.data.configs.common.PropertiesLoader;
import simulation.data.configs.common.SlotsDataLoader;
import simulation.data.configs.profiles.AgentsConfigsLoader;
import simulation.data.configs.profiles.AgentsConfigsLoaderXML;
import simulation.model.core.ParkingModel;
import simulation.model.support.Timing;

public abstract class MasonModelController implements Observer {
	private final static Logger logger = Logger.getLogger(SmartParkingWServices.class);
	
	protected ModelRunner runner = null;
	protected List<SlotCurrentState> slots;
	protected ParkingModel parkingModel = null;
	private AgentsConfigsLoader configsLoader = null;
	
	// Parameters
	private double stepTime = 0;
	private double guidedAgentProportion = 1;
	private int repetitions = 1;
	private boolean useRealTime = false;
	
	// State
	private SimulationState simulState = SimulationState.NOT_STARTED;
	private int currIteration = 0;
	private Timing simulTime = Timing.ZERO;
	private boolean loadBasicParams = true;
	private boolean requireData = true;

	public MasonModelController(){
		slots = new ArrayList<SlotCurrentState>();
		readData();
	}
	
	protected void readData(){
		try{
			MainPropertiesLoader mainProps = MainPropertiesLoader.getInstance();
			InputStream in = mainProps.getInputStreamForFileInConfigFolder(LOG4J_FILE_RESOURCE_NAME);
			PropertyConfigurator.configure(in);
			
			PropertiesLoader pl = PropertiesLoader.getInstance();
			if (loadBasicParams){
				stepTime = pl.getPropertyAsDouble(PROPERTIES_FILE_STEP_TIME);
				guidedAgentProportion = pl.getPropertyAsDouble(PROPERTIES_FILE_AGENT_BEHAVIOR);
				loadBasicParams = false;
			}
			repetitions = pl.getPropertyAsInt(PROPERTIES_FILE_REPETITIONS);
			useRealTime = pl.getPropertyAsBoolean(PROPERTIES_FILE_USE_REAL_TIME);
			
	    	SlotsDataLoader manager = new SlotsDataLoader();
			List<SlotData> slotsData = manager.getSlotsData();
			slots = SlotCurrentState.createSlotsStates(slotsData);
			
			configsLoader = new AgentsConfigsLoaderXML();
			
			simulState = SimulationState.NOT_STARTED;
			requireData = false;
		}
		catch(Exception e){
			logger.error("Problems retrieving information!!! Exception: " + e.getMessage());
			simulState = SimulationState.ERROR;
			requireData = true;
		}
	}
	
	public synchronized void run(){
		if (requireData == true){
			readData();
		}
		
		if (simulState != SimulationState.ERROR){
			parkingModel = new ParkingModel(System.currentTimeMillis(), configsLoader, slots, stepTime, guidedAgentProportion,
					isSlotInitNeeded(), isSetSlotsFreeOnInit(), repetitions, useRealTime);
			
			createRunner();		// Can be redefined in child classes
			setupUpdaters();	// Defined in child classes
			parkingModel.addGlobalUpdater(this);	// Iteration and finished model notifications
			
			runner.run();
			simulState = SimulationState.RUNNING;
			onRun();
		}
	}
	
	public synchronized void cancel(){
		runner.cancel();
		simulState = SimulationState.CANCELED;
		simulTime = Timing.ZERO;
		onCancel();
	}
	
	protected abstract boolean isSlotInitNeeded();
	
	protected abstract boolean isSetSlotsFreeOnInit();
	
	protected abstract void setupUpdaters();
	
	protected void createRunner(){
		runner = new ModelRunner(parkingModel);
	}
	
	protected void onRun() {}
	
	protected void onCancel() {}
	
	@Override
	public synchronized void update(Observable o, Object arg){
		ModelNotification notification = (ModelNotification) arg;
		switch (notification.getType()) {
		case INIT:
			// TODO think if something is needed
			break;
		case ITERATION:
			currIteration = (int)(notification.getValue());
			break;
		case FINISHED:
			simulState = SimulationState.FINISHED;
			break;
		case STEP:
			simulTime = Timing.fromBaseTime((double)(notification.getValue()));
			break;
		default: //
			Assert.fail("SimulationState");
			break;
		}
	}
	
	public synchronized int getCurrRepetition() {
		return currIteration;
	}
	
	public synchronized SimulationState getState(){
		return simulState;
	}

	public synchronized Timing getSimulTime() {
		return simulTime;
	}
	
	public synchronized void requestDataUpdate(boolean loadBasicParams){
		this.loadBasicParams = loadBasicParams;
		requireData = true;
	}
	
	// -------------------------------------------------	
	// PARAMETERS
	
	public synchronized void setSimulationParameters(double stepTime, double guidedAgentProportion){
		this.stepTime = stepTime;
		this.guidedAgentProportion = guidedAgentProportion;
		if (parkingModel != null){
			parkingModel.setTimeInterval(stepTime);
			parkingModel.setGuidedAgentProportion(guidedAgentProportion);
		}
	}
	
	public synchronized double getStepTime(){
		return stepTime;
	}
	
	public synchronized double getGuidedAgentProportion(){
		return guidedAgentProportion;
	}
	
	// -------------------------------------------------	
}
