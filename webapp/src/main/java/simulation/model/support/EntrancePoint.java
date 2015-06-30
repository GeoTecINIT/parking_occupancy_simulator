package simulation.model.support;

import com.esri.core.geometry.Point;

public class EntrancePoint {
	private int id;
	private String description;
	private Point point;
	
	public EntrancePoint() {}

	public EntrancePoint(int id, String description, Point point) {
		super();
		this.id = id;
		this.point = point;
	}
	
	public EntrancePoint(int id, String description, double x, double y) {
		super();
		this.id = id;
		this.description = description;
		this.point = new Point(x, y);
	}

	public int getId() {
		return id;
	}

	public Point getPoint() {
		return point;
	}

	public String getDescription() {
		return description;
	}
}
