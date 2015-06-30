package simulation.model.wrapping;

import simulation.model.support.ParkingAgentDescriptor;

public class AgentChangeNotification {
	private ParkingActions action;
	private ParkingAgentDescriptor agentData;
	
	public AgentChangeNotification(ParkingActions action,
			ParkingAgentDescriptor agentData) {
		super();
		this.action = action;
		this.agentData = agentData;
	}

	public ParkingActions getAction() {
		return action;
	}

	public ParkingAgentDescriptor getAgentData() {
		return agentData;
	}
}
