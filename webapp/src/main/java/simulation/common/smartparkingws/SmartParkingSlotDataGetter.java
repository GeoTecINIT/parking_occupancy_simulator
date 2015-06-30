package simulation.common.smartparkingws;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.MediaType;

import simulation.common.globals.SlotData;
import simulation.common.globals.WebRequester;

public class SmartParkingSlotDataGetter extends WebRequester<SlotDataXML> {
	private String target;
	private String pathGetSlotData;
	private int timeOutInterval;
	
	public SmartParkingSlotDataGetter(String target, String pathGetSlotData, int timeOutInterval){
		super(SlotDataXML.class);
		this.target = target;
		this.pathGetSlotData = pathGetSlotData;
		this.timeOutInterval = timeOutInterval;
	}
	
	public List<SlotData> getSlotsData() throws Exception{
		// Get the data from the web service
		setUrl(target + pathGetSlotData);
		setRequestMediaType(MediaType.APPLICATION_XML_TYPE);
		setTimeOut(timeOutInterval);
		setParams(new HashMap<String, String>());
		
		SlotDataXML data = makeRequest();
		return data.getSlotObjects();
	}
}
