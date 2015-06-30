package simulation.data.configs.common;

import java.util.List;

import simulation.common.globals.SlotData;
import simulation.common.smartparkingws.SmartParkingWServices;

public class SlotsDataLoader{
	private SmartParkingWServices smartParkingWS = null;
	
	public SlotsDataLoader() {
		smartParkingWS = SmartParkingWServices.getInstance();
	}

	public List<SlotData> getSlotsData() throws Exception {
		return smartParkingWS.getSlotsData();
	}
}
