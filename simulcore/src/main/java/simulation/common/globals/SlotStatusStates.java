package simulation.common.globals;

import java.util.HashMap;
import java.util.Map;

public enum SlotStatusStates {
	OCCUPIED(1),
	AVAILABLE(2),
	REQUESTED(3),
	DISABLED(4);
	
	private int value;
	
	private SlotStatusStates(int value) {
	   this.value = value;
	}
	
	public int getValue() {
	   return value;
	}
	
	private static Map<Integer, SlotStatusStates> map = new HashMap<Integer, SlotStatusStates>();

    static {
        for (SlotStatusStates slotStatusStates : SlotStatusStates.values()) {
            map.put(slotStatusStates.getValue(), slotStatusStates);
        }
    }
	
	public static SlotStatusStates valueOf(int value) {
        return map.get(value);
    }
}
