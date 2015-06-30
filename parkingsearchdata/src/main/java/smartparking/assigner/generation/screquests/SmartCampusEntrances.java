package smartparking.assigner.generation.screquests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import simulation.common.globals.BuildingEntrance;
import simulation.common.globals.BuildingEntranceCoordinate;
import simulation.common.globals.WebRequester;

public class SmartCampusEntrances extends WebRequester<Entrances> {
	
	private SmartCampusEntrances() {
		super(Entrances.class);
		
		Map<String, String> params = new HashMap<>();
		params.put("where", "1=1");
		params.put("outFields", "*");	
		params.put("f", "json");
		setParams(params);
		setTimeOut(10000);
		setRequestMediaType(MediaType.APPLICATION_JSON_TYPE);
		setUrl("http://smartcampus.sg.uji.es:6080/arcgis/rest/services/SmartCampus/BuildingDoors/MapServer/0/query?");
	}
	
	@Override
	protected void preProcessResponse(Response response){
		// Arcgis server does not return the json content-type, so we need to override the headers of the response
		response.getHeaders().clear();
	    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
	}
	
	public List<BuildingEntrance> getBuildingEntrances() throws Exception{
		List<Feature> features = makeRequest().getFeatures();
		List<BuildingEntrance> buildingEntrances = new ArrayList<>();
		
		for (Feature feature : features) {
			Attributes attr = feature.getAttributes();
			BuildingEntranceCoordinate coordinate = new BuildingEntranceCoordinate(feature.getGeometry().getX(), feature.getGeometry().getY());
			buildingEntrances.add(new BuildingEntrance(attr.getObjectID(), attr.getBuildingID(), "", coordinate));
		}
		return buildingEntrances;
	}
	
	private static volatile SmartCampusEntrances instance = null;
	
	public static SmartCampusEntrances getInstance() {
        if (instance == null) {
            synchronized (SmartCampusEntrances.class) {
                if (instance == null) {
                    instance = new SmartCampusEntrances();
                }
            }
        }
        return instance;
 	}
}
