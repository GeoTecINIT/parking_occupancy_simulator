package simulation.model.core;


public interface ParkingAgentFactory {	
	public abstract ParkingAgent createParkingAgent(AgentCreationProfileData agentCreationProfileData, double agentSpeed);
}
