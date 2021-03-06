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
	private int guidedCount = 0;
	private int explorerCount = 0;
	private int guidedSteps = 0;
	private int explorerSteps = 0;
	private int accumulatedGuidedSteps = 0;
	private int accumulatedExplorerSteps = 0;

	public synchronized int getGuidedCount() {
		return guidedCount;
	}

	public synchronized void setGuidedCount(int guidedCount) {
		this.guidedCount = guidedCount;
	}

	public synchronized int getExplorerCount() {
		return explorerCount;
	}

	public synchronized void setExplorerCount(int explorerCount) {
		this.explorerCount = explorerCount;
	}

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
	
	public ActivityIndicators getCO2Indicators() {
		ActivityIndicators indicators = new ActivityIndicators(
				(int)(cO2GramsPerStep() * getGuidedSteps()),
				(int)(cO2GramsPerStep() * getExplorerSteps()),
				(int)(cO2GramsPerStep() * getAccumulatedGuidedSteps()),
				(int)(cO2GramsPerStep() * getAccumulatedExplorerSteps()),
				""
		);
		return indicators;
	}
	
	private synchronized void updateAccumulates(){
		setGuidedSteps(getGuidedCount());
		setExplorerSteps(getExplorerCount());
		setAccumulatedGuidedSteps(getAccumulatedGuidedSteps() + getGuidedSteps());
		setAccumulatedExplorerSteps(getAccumulatedExplorerSteps() + getExplorerSteps());
		setGuidedCount(0);
		setExplorerCount(0);
	}
	
	public class AgentCO2Updater extends AgentsChangeUpdater {

		@Override
		protected void doUpdate(AgentChangeNotification unit) {
			ParkingAgentDescriptor agentData = unit.getAgentData();
			switch (unit.getAction()) {
			case AGENT_MOVING:
				if (agentData.getDescription().equals(EXPLORER_AGENT_DESCRIPTION)){
					setExplorerCount(getExplorerCount() + 1);;
				}
				else{
					setGuidedCount(getGuidedCount() + 1);
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
				Assert.fail("AgentCO2Updater");
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
	
	private AgentCO2Updater agentCO2Updater = new AgentCO2Updater();
	private GlobalCO2Updater globalCO2Updater = new GlobalCO2Updater();
	
	public CO2Updater(){
		
	}
    	
	public AgentCO2Updater getAgentCO2Updater() {
		return agentCO2Updater;
	}

	public GlobalCO2Updater getGlobalCO2Updater() {
		return globalCO2Updater;
	}
}
