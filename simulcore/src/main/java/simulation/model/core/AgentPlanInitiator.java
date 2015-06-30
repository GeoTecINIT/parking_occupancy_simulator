package simulation.model.core;

import sim.engine.SimState;
import sim.engine.Steppable;

public class AgentPlanInitiator implements Steppable{
	private static final long serialVersionUID = 1L;
	
	AgentCreationProfileData agentCreationData = null;

	public AgentPlanInitiator(AgentCreationProfileData agentCreationData){
		this.agentCreationData = agentCreationData;
	}
	
	@Override
	public void step(SimState state) {
		ParkingModel parkingModel = (ParkingModel)state;
		parkingModel.addAgent(agentCreationData);
	}
	
	public AgentCreationProfileData getAgentCreationData() {
		return agentCreationData;
	}
}
