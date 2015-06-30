package simulation.model.core;

import java.util.HashMap;
import java.util.Map;

public enum AgentBehaviorTypes {
	GUIDED(0),
	EXPLORER(1);
	
	private int value;
	
	private AgentBehaviorTypes(int value) {
	   this.value = value;
	}
	
	public int getValue() {
	   return value;
	}
	
	private static Map<Integer, AgentBehaviorTypes> map = new HashMap<Integer, AgentBehaviorTypes>();

    static {
        for (AgentBehaviorTypes agentBehaviorTypes : AgentBehaviorTypes.values()) {
            map.put(agentBehaviorTypes.getValue(), agentBehaviorTypes);
        }
    }
	
	public static AgentBehaviorTypes valueOf(int value) {
        return map.get(value);
    }
}
