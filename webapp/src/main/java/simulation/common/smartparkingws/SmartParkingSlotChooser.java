package simulation.common.smartparkingws;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import simulation.common.globals.WebRequester;

public class SmartParkingSlotChooser extends WebRequester<ResponseChooseSlot> {
	private String target;
	private String pathChooseSlot;
	private String longitudeParam;
	private String latitudeParam;
	private int timeOutInterval;
	
	public SmartParkingSlotChooser(String target, String pathChooseSlot, int timeOutInterval, String longitudeParam, String latitudeParam){
		super(ResponseChooseSlot.class);
		this.target = target;
		this.pathChooseSlot = pathChooseSlot;
		this.timeOutInterval = timeOutInterval;
		this.longitudeParam = longitudeParam;
		this.latitudeParam = latitudeParam;
	}
	
	public ResponseChooseSlot chooseSlot(double longitude, double latitude) throws Exception{
		// Get the data from the web service
		setUrl(target + pathChooseSlot);
		setRequestMediaType(MediaType.APPLICATION_XML_TYPE);
		setTimeOut(timeOutInterval);
		
		Map<String, String> params = new HashMap<>();
		params.put(longitudeParam, ((Double)longitude).toString());
		params.put(latitudeParam, ((Double)latitude).toString());
		setParams(params);
		
		ResponseChooseSlot resp = makeRequest();
		return resp;
	}
}
