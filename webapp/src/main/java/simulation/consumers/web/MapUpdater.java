package simulation.consumers.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import simulation.common.globals.SlotCoordinates;
import simulation.common.globals.SlotCurrentState;
import simulation.model.wrapping.SlotCurrentStateUpdater;

public class MapUpdater extends SlotCurrentStateUpdater {
	final static Logger logger = Logger.getLogger(MapUpdater.class);
	
	private Map<SlotCurrentState, Integer> mapSlotServer = null;
	private Map<Integer, SlotClientState> mapSlotClient = null;
	
	public MapUpdater(List<SlotCurrentState> slots) {
		mapSlotServer = new HashMap<SlotCurrentState, Integer>();
		mapSlotClient = new HashMap<Integer, SlotClientState>();
		for (SlotCurrentState slotCurrentState : slots) {
			int id = slotCurrentState.getSlot().getObjectId();
			mapSlotServer.put(slotCurrentState, id);
			mapSlotClient.put(id, new SlotClientState(id, slotCurrentState.getStatus(), (new Date()).getTime()));
		}
	}
	
	public SlotListDetails <SlotClientData> getSlotsData(){
		List<SlotClientData> slots = new ArrayList<SlotClientData>();
		for (Map.Entry<SlotCurrentState, Integer> entry : mapSlotServer.entrySet()) {
			SlotCoordinates coordinates = entry.getKey().getSlot().getCoordinates();
			slots.add(new SlotClientData(entry.getValue(), coordinates.getLongitude(), coordinates.getLatitude()));
		}
		return new SlotListDetails <SlotClientData>((new Date()).getTime(), slots);
	}
	
	@Override
	protected synchronized void doUpdate(SlotCurrentState arg) {
		Integer id = mapSlotServer.get(arg);
		mapSlotClient.put(id, new SlotClientState(id, arg.getStatus(), (new Date()).getTime()));
	}
	
	public synchronized SlotListDetails<SlotClientState> getUpdates(long requestedTime){
		List<SlotClientState> slots = new ArrayList<SlotClientState>();
		long maxTime = 0;
		for (Map.Entry<Integer, SlotClientState> entry : mapSlotClient.entrySet()) {
			if (entry.getValue().getTime() > requestedTime){
				slots.add(entry.getValue());
				if (maxTime < entry.getValue().getTime()){
					maxTime = entry.getValue().getTime();
				}
			}
		}
		return new SlotListDetails<SlotClientState> (maxTime, slots);
	}

}
