package simulation.data.gisdata.routes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.esri.core.geometry.Point;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SRPoint {
	private double x;
	private double y;
	private double z;
	private SRSpatialReference spatialReference;
	
	public SRPoint(){}
	
	public SRPoint(double x, double y, SRSpatialReference spatialReference) {
		this(x, y, 0, spatialReference);
	}
	
	public SRPoint(double x, double y, double z, SRSpatialReference spatialReference) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.spatialReference = spatialReference;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public SRSpatialReference getSpatialReference() {
		return spatialReference;
	}
	
	public static Point getAsEsriPoint(SRPoint point){
		return new Point(point.getX(), point.getY());
	}
	
	public static SRPoint fromEsriPoint(Point point, SRSpatialReference spatialReference){
		return new SRPoint(point.getX(), point.getY(), spatialReference);
	}
}
