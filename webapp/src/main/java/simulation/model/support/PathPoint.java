package simulation.model.support;

import com.esri.core.geometry.Point;

public class PathPoint{
	private int index;
	private Point point;
	
	public PathPoint(int index, Point point) {
		super();
		this.index = index;
		this.point = point;
	}
	
	public PathPoint(int index, PathPoint pathPoint) {
		super();
		this.index = index;
		this.point = new Point(pathPoint.getPoint().getX(), pathPoint.getPoint().getY());
	}

	public int getIndex() {
		return index;
	}
	
	public Point getPoint() {
		return point;
	}
}
