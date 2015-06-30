package simulation.model.core;


public class GuidedParkingAgentFactory implements ParkingAgentFactory{
	private static volatile GuidedParkingAgentFactory instance = null;
	
	private GuidedParkingAgentFactory(){}
	
	public static GuidedParkingAgentFactory getInstance() {
        if (instance == null) {
            synchronized (GuidedParkingAgentFactory.class) {
                if (instance == null) {
                    instance = new GuidedParkingAgentFactory();
                }
            }
        }
        return instance;
    }
	
	@Override
	public ParkingAgent createParkingAgent(AgentCreationProfileData agentCreationProfileData, double agentSpeed){
		return new GuidedParkingAgent(agentCreationProfileData, agentSpeed);
	}
}
