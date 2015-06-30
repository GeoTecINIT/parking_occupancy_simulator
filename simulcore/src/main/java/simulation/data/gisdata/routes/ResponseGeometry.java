package simulation.data.gisdata.routes;

import java.util.ArrayList;
import java.util.List;

import com.esri.core.geometry.Point;

public class ResponseGeometry {
	private double [][][] paths;
	
	public ResponseGeometry(){}
	
	public ResponseGeometry(double[][][] paths) {
		super();
		this.paths = paths;
	}

	public double[][][] getPaths() {
		return paths;
	}
	
	public static List<Point> getAsPointCollection(ResponseGeometry responseGeometry){
		List<Point> vertexes = new ArrayList<Point>();
		for(int i = 0; i < responseGeometry.paths[0].length; i++){
			vertexes.add(new Point(responseGeometry.paths[0][i][0], responseGeometry.paths[0][i][1]));
		}
		return vertexes;
	}
}
