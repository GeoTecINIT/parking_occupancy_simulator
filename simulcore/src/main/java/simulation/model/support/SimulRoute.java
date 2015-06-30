package simulation.model.support;

import java.util.List;

import com.esri.core.geometry.Point;


public class SimulRoute {
	private double total_WeightedTime;
	private double shape_Length;
	private List<Point> path;
	
	public SimulRoute() {}

	public SimulRoute(double total_WeightedTime, double shape_Length,
			List<Point> path) {
		super();
		this.total_WeightedTime = total_WeightedTime;
		this.shape_Length = shape_Length;
		this.path = path;
	}

	public double getTotal_WeightedTime() {
		return total_WeightedTime;
	}

	public double getShape_Length() {
		return shape_Length;
	}

	public List<Point> getPath() {
		return path;
	}
	
	// PONER -1
	public PathPoint calculatePointForDistance(PathPoint startingPoint, double distance){
		if (path.size() == 0) { return null; }
		if (shape_Length <= distance) { return new PathPoint(path.size()-1, path.get(path.size()-1)); }
		if (startingPoint.getIndex() >= path.size() - 1) { return startingPoint; }
		if (startingPoint.getIndex() == -1) { return new PathPoint(0, path.get(0)); }
			
		Point currentPoint = startingPoint.getPoint();
		int nextPointPos = startingPoint.getIndex() + 1;
		Point nextPoint = path.get(nextPointPos);
		double accumulateDistance = 0;
		double distanceToNextPoint = calculateDistance(currentPoint, nextPoint);
		for (; nextPointPos < path.size() - 1; nextPointPos++){
			if (accumulateDistance + distanceToNextPoint >= distance){
				break;
			}
			accumulateDistance += distanceToNextPoint;
			currentPoint = nextPoint;
			nextPoint = path.get(nextPointPos + 1);
			distanceToNextPoint = calculateDistance(currentPoint, nextPoint);
		}
		if (accumulateDistance + distanceToNextPoint <= distance){
			return new PathPoint(nextPointPos, nextPoint);
		}
		double ratio = (distance - accumulateDistance) / distanceToNextPoint;
		double dx = (nextPoint.getX() - currentPoint.getX()) * ratio;
		double dy = (nextPoint.getY() - currentPoint.getY()) * ratio;
		
		return new PathPoint(nextPointPos - 1, new Point(currentPoint.getX() + dx, currentPoint.getY() + dy));
	}
	
	public double calculateDistance(Point p1, Point p2){
		double result = Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2)); 
		return result;
	}
	
	public boolean isLast(PathPoint point){
		return (path.size() == (point.getIndex()+1));
	}
	
	public Point getLast(){
		if (path == null) return null;
		if (path.size() == 0) return null;
		return path.get(path.size()-1);
	}
	
	public PathPoint getNextPointInPath(PathPoint currPoint){
		int currIndex = currPoint.getIndex();
		if (path.size() == 0) { return null; }
		if (currIndex >= path.size() - 1) { return currPoint; }
		return new PathPoint(currIndex + 1, path.get(currIndex + 1));
	}
}
