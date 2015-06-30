package simulation.consumers.common;

import simulation.model.support.ParkingAgentDescriptor;
import simulation.model.wrapping.AgentChangeNotification;
import simulation.model.wrapping.AgentsChangeUpdater;

public class ConsoleAgentChangeUpdater extends AgentsChangeUpdater{
	@Override
	protected void doUpdate(AgentChangeNotification unit) {
		ParkingAgentDescriptor agentData = unit.getAgentData();
		
		switch (unit.getAction()) {
		case AGENT_ADDING:
			break;
		case AGENT_MOVING:
			System.out.println(agentData.getType() + "" + agentData.getId() + " moving to " + agentData.getCurrentDestination() + " at " + agentData.getCurrentTime());
			break;
		case AGENT_PARKING:
			System.out.println(agentData.getType() + "" + agentData.getId() + " parking at " + agentData.getCurrentTime() + " on " + agentData.getPosition());
			break;
		case AGENT_LEAVING:
			System.out.println(agentData.getType() + "" + agentData.getId() + " leaving at " + agentData.getCurrentTime());
			break;
		default:
			break;
		}
	}
}
