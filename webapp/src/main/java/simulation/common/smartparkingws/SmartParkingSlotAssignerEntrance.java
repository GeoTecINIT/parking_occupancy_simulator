package simulation.common.smartparkingws;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import simulation.common.globals.WebRequester;

public class SmartParkingSlotAssignerEntrance extends WebRequester<ResponseAssignSlotEntrance> {
	private String target;
	private String pathChooseSlot;
	private String doorParam;
	private int timeOutInterval;
	
	public SmartParkingSlotAssignerEntrance(String target, String pathChooseSlot, int timeOutInterval, String doorParam){
		super(ResponseAssignSlotEntrance.class);
		this.target = target;
		this.pathChooseSlot = pathChooseSlot;
		this.timeOutInterval = timeOutInterval;
		this.doorParam = doorParam;
	}
	
	public ResponseAssignSlotEntrance assignSlotCoord(int door) throws Exception{
		// Get the data from the web service
		setUrl(target + pathChooseSlot);
		setRequestMediaType(MediaType.APPLICATION_XML_TYPE);
		setTimeOut(timeOutInterval);
		
		Map<String, String> params = new HashMap<>();
		params.put(doorParam, ((Integer)door).toString());
		setParams(params);
		
		ResponseAssignSlotEntrance resp = makeRequest();
		return resp;
	}
}
