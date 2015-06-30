package simulation.data.gisdata.routes;

import java.util.List;

import com.esri.core.geometry.Point;

import simulation.model.support.SimulRoute;

public class RouteResponse {
	private List<String> messages;
	private Routes routes;
	
	public RouteResponse() {}
	
	public RouteResponse(List<String> messages, Routes routes) {
		super();
		this.messages = messages;
		this.routes = routes;
	}

	public List<String> getMessages() {
		return messages;
	}

	public Routes getRoutes() {
		return routes;
	}
	
	public void setRoutes(Routes routes){
		this.routes = routes;
	}
	
	public static SimulRoute buildRoute (RouteResponse routeResponse){
		List<Point> path = ResponseGeometry.getAsPointCollection(routeResponse.getRoutes().getFeatures().get(0).getGeometry());
		double total_WeightedTime = routeResponse.getRoutes().getFeatures().get(0).getAttributes().getTotal_WeightedTime(); 
		double shape_Length = routeResponse.getRoutes().getFeatures().get(0).getAttributes().getShape_Length();
		SimulRoute simulRoute = new SimulRoute(total_WeightedTime, shape_Length, path);
		return simulRoute;
	}
}
