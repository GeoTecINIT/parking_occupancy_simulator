package simulation.model.core;

public class ParkingAgentFactoryProducer {
	public static ParkingAgentFactory getParkingAgentFactory(AgentBehaviorTypes agentBehaviorTypes){
		switch (agentBehaviorTypes) {
		case GUIDED:
			return GuidedParkingAgentFactory.getInstance();
		default:
			return ExplorerParkingAgentFactory.getInstance();
		}
	}
}
