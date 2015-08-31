package simulation.consumers.web;

import static simulation.common.globals.SimulGlobalConstants.*;

import java.util.Observable;
import java.util.Observer;

import junit.framework.Assert;
import simulation.model.support.ParkingAgentDescriptor;
import simulation.model.wrapping.AgentChangeNotification;
import simulation.model.wrapping.AgentsChangeUpdater;
import simulation.model.wrapping.ModelNotification;
import simulation.model.wrapping.ModelNotificationType;

public class CO2Updater{
	private int guidedSteps = 0;
	private int explorerSteps = 0;
	private int accumulatedGuidedSteps = 0;
	private int accumulatedExplorerSteps = 0;

	private synchronized int getGuidedSteps() {
		return guidedSteps;
	}

	private synchronized void setGuidedSteps(int guidedSteps) {
		this.guidedSteps = guidedSteps;
	}

	private synchronized int getExplorerSteps() {
		return explorerSteps;
	}
	
	private synchronized void setExplorerSteps(int explorerSteps) {
		this.explorerSteps = explorerSteps;
	}
	
	private synchronized int getAccumulatedGuidedSteps() {
		return accumulatedGuidedSteps;
	}
	
	private synchronized void setAccumulatedGuidedSteps(int accumulatedGuidedSteps) {
		this.accumulatedGuidedSteps = accumulatedGuidedSteps;
	}

	private synchronized int getAccumulatedExplorerSteps() {
		return accumulatedExplorerSteps;
	}

	private synchronized void setAccumulatedExplorerSteps(int accumulatedExplorerSteps) {
		this.accumulatedExplorerSteps = accumulatedExplorerSteps;
	}
	
	private static double cO2GramsPerStep(){
		return 0.15 * SIMULATION_CAR_SPEED;
	}
	
	public CO2Indicators getCO2Indicators() {
		CO2Indicators indicators = new CO2Indicators(
				(int)(cO2GramsPerStep() * getGuidedSteps()),
				(int)(cO2GramsPerStep() * getExplorerSteps()),
				(int)(cO2GramsPerStep() * getAccumulatedGuidedSteps()),
				(int)(cO2GramsPerStep() * getAccumulatedExplorerSteps())
		);
		return indicators;
	}
	
	private synchronized void updateAccumulates(){
		System.out.println(getAccumulatedGuidedSteps() + ", " + getGuidedSteps());
		setAccumulatedGuidedSteps(getAccumulatedGuidedSteps() + getGuidedSteps());
		setAccumulatedExplorerSteps(getAccumulatedExplorerSteps() + getExplorerSteps());
		setGuidedSteps(0);
		setExplorerSteps(0);
	}
	
	public class AgentCO2Updater extends AgentsChangeUpdater {

		@Override
		protected void doUpdate(AgentChangeNotification unit) {
			ParkingAgentDescriptor agentData = unit.getAgentData();
			switch (unit.getAction()) {
			case AGENT_MOVING:
				if (agentData.getDescription().equals(EXPLORER_AGENT_DESCRIPTION)){
					setExplorerSteps(getExplorerSteps() + 1);;
				}
				else{
					setGuidedSteps(getGuidedSteps() + 1);
				}
				break;
			case AGENT_ADDING:
				break;
			case AGENT_LEAVING:
				break;
			case AGENT_PARKING:
				break;
			case AGENT_TRICKED:
				break;
			default:
				System.err.println("ERRRRRRROOOOOORRRRR");
				break;
			}
		}
	}
	
	public class GlobalCO2Updater implements Observer{
		
		@Override
		public void update(Observable o, Object arg) {
			ModelNotificationType notification = ((ModelNotification) arg).getType();
			switch (notification) {
			case STEP:
				updateAccumulates();
				break;
			case ITERATION:
				break;
			case INIT:
				break;
			case FINISHED:
				break;
			default: //
				Assert.fail("GlobalCO2Updater");
				break;
			}
		}
	}
	
	// SINGLETON implementation
	
	private AgentCO2Updater agentCO2Updater = new AgentCO2Updater();
	private GlobalCO2Updater globalCO2Updater = new GlobalCO2Updater();

	private static volatile CO2Updater instance = null;
	
	private CO2Updater(){}
	 
    public static CO2Updater getInstance() {
        if (instance == null) {
            synchronized (CO2Updater.class) {
                if (instance == null) {
                    instance = new CO2Updater();
                }
            }
        }
        return instance;
    }
    	
	public AgentCO2Updater getAgentCO2Updater() {
		return agentCO2Updater;
	}

	public GlobalCO2Updater getGlobalCO2Updater() {
		return globalCO2Updater;
	}
}
