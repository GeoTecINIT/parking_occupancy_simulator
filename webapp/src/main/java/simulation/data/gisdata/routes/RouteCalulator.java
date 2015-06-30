package simulation.data.gisdata.routes;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import simulation.common.globals.WebRequester;
import simulation.model.support.SimulRoute;

import com.esri.core.geometry.Point;

public abstract class RouteCalulator extends WebRequester<RouteResponse>{
	protected String target;
	protected String pathRoute;
	protected int timeOutInterval;
	
	protected RouteCalulator() {
		super(RouteResponse.class);
		readParams();
    }
    
    protected abstract void readParams();
    
	@Override
	protected void preProcessResponse(Response response){
		// Arcgis server does not return the json content-type, so we need to override the headers of the response
		response.getHeaders().clear();
	    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
	}
	
	public SimulRoute getPath(Point start, Point end){
		String responseString="";
		try{
			SRSpatialReference spatialReference = new SRSpatialReference(102100, 3857);
			SRPoint point1 = new SRPoint(start.getX(), start.getY(), spatialReference);
			SRPoint point2 = new SRPoint(end.getX(), end.getY(), spatialReference);
			
			List<Feature> features = new ArrayList<Feature>();
			features.add(new Feature(point1));
			features.add(new Feature(point2));
			
			Stops stops = new Stops(features);
			RouteRequest routeRequest = new RouteRequest(stops);
			String json = routeRequest.urlEncode();
			
			setTimeOut(timeOutInterval);
			setUrl(target + pathRoute + json);
			setRequestMediaType(MediaType.APPLICATION_JSON_TYPE);
			setParams(new HashMap<String, String>());
			
			RouteResponse routeResponse = makeRequest();
			SimulRoute simulRoute = RouteResponse.buildRoute(routeResponse);
			
			return simulRoute;
		}
		catch(Exception e){
			System.out.println("Problems getting route information "+ responseString +" !!! Exception: " + e.getMessage());
			return null;
		}
	}
}
