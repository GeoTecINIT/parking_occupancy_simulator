package simulation.model.core;


public class ExplorerParkingAgentFactory implements ParkingAgentFactory{
	private static volatile ExplorerParkingAgentFactory instance = null;
	
	private ExplorerParkingAgentFactory(){}
	
	public static ExplorerParkingAgentFactory getInstance() {
        if (instance == null) {
            synchronized (ExplorerParkingAgentFactory.class) {
                if (instance == null) {
                    instance = new ExplorerParkingAgentFactory();
                }
            }
        }
        return instance;
    }
	
	@Override
	public ParkingAgent createParkingAgent(AgentCreationProfileData agentCreationProfileData, double agentSpeed){
		return new ExplorerParkingAgent(agentCreationProfileData, agentSpeed);
	}
}
