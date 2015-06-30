package simulation.data.gisdata.routes;

import java.io.IOException;
import java.net.URLEncoder;

import org.codehaus.jackson.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class RouteRequest {
	private String f = "json";
	private boolean returnDirections = false;
	private boolean returnRoutes = true;
	private boolean returnStops = false;
	private boolean returnBarriers = false;
	private boolean returnPolygonBarriers = false;
	private boolean returnPolylineBarriers = false;
	private String outSR = "102100";
	private String outputLines = "esriNAOutputLineTrueShape";
	Stops stops = null;
	
	public RouteRequest(){}
	
	public RouteRequest(Stops stops){
		this.stops = stops;
	}

	public RouteRequest(String f, boolean returnDirections,
			boolean returnRoutes, boolean returnStops, boolean returnBarriers,
			boolean returnPolygonBarriers, boolean returnPolylineBarriers, String outSR,
			String outputLines, Stops stops) {
		super();
		this.f = f;
		this.returnDirections = returnDirections;
		this.returnRoutes = returnRoutes;
		this.returnStops = returnStops;
		this.returnBarriers = returnBarriers;
		this.returnPolygonBarriers = returnPolygonBarriers;
		this.returnPolylineBarriers = returnPolylineBarriers;
		this.outSR = outSR;
		this.outputLines = outputLines;
		this.stops = stops;
	}

	public String getF() {
		return f;
	}

	public boolean isReturnDirections() {
		return returnDirections;
	}

	public boolean isReturnRoutes() {
		return returnRoutes;
	}

	public boolean isReturnStops() {
		return returnStops;
	}

	public boolean isReturnBarriers() {
		return returnBarriers;
	}

	public boolean isReturnPolygonBarriers() {
		return returnPolygonBarriers;
	}

	public boolean isReturnPolylineBarriers() {
		return returnPolylineBarriers;
	}

	public String getOutSR() {
		return outSR;
	}

	public String getOutputLines() {
		return outputLines;
	}

	public Stops getStops() {
		return stops;
	}
	
	public String urlEncode() throws JsonGenerationException, JsonMappingException, IOException{
		StringBuilder sb = new StringBuilder();
		sb.append("f=" + f);
		sb.append("&returnDirections=" + returnDirections);
		sb.append("&returnRoutes=" + returnRoutes);
		sb.append("&returnStops=" + returnStops);
		sb.append("&returnBarriers=" + returnBarriers);
		sb.append("&returnPolygonBarriers=" + returnPolygonBarriers);
		sb.append("&returnPolylineBarriers=" + returnPolylineBarriers);
		sb.append("&outSR=" + outSR);
		sb.append("&outputLines=" + outputLines);

		ObjectWriter ow = new ObjectMapper().writer();
		String stopsJson = ow.writeValueAsString(stops);
		sb.append("&stops=" + URLEncoder.encode(stopsJson, "UTF-8"));
		
		return sb.toString();
	}
}
