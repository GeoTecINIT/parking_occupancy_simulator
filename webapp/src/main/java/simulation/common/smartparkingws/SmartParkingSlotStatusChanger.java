package simulation.common.smartparkingws;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;


import simulation.common.globals.SlotCurrentState;
import simulation.common.globals.WebRequester;

public class SmartParkingSlotStatusChanger extends WebRequester<ResponseChangeStatus> {
	private String target;
	private String pathChangeStatus;
	private String slotParam;
	private String parkingParam;
	private String statusParam;
	private int timeOutInterval;
	
	public SmartParkingSlotStatusChanger(String target, String pathChangeStatus, int timeOutInterval, String slotParam, String parkingParam, String statusParam){
		super(ResponseChangeStatus.class);
		this.target = target;
		this.pathChangeStatus = pathChangeStatus;
		this.timeOutInterval = timeOutInterval;
		this.slotParam = slotParam;
		this.parkingParam = parkingParam;
		this.statusParam = statusParam;
	}
	
	public ResponseChangeStatus changeSlotStatus(SlotCurrentState slotStatus) throws Exception{
		// Get the data from the web service
		setUrl(target + pathChangeStatus);
		setRequestMediaType(MediaType.APPLICATION_XML_TYPE);
		setTimeOut(timeOutInterval);
		
		Map<String, String> params = new HashMap<>();
		params.put(parkingParam, slotStatus.getSlot().getParking().toString());
		params.put(slotParam, slotStatus.getSlot().getSlot().toString());
		params.put(statusParam, slotStatus.getStatus().toString());
		setParams(params);
		
		ResponseChangeStatus resp = makeRequest();
		return resp;	
	}
}
