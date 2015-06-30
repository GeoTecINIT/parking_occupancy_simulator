package simulation.model.support;

import com.esri.core.geometry.Point;

public class BuildingEntrance {
	public static BuildingEntranceDestination NULL = new BuildingEntranceDestination();
	
	private int id;
	private int building;
	private int door;
	private String description;
	private Point point;
	
	public BuildingEntrance() {
		super();
	}

	public BuildingEntrance(int id, int building, int door, String description, Point point) {
		super();
		this.id = id;
		this.building = building;
		this.door = door;
		this.point = point;
	}
	
	public BuildingEntrance(int id, int building, int door, String description, double x, double y) {
		super();
		this.id = id;
		this.building = building;
		this.door = door;
		this.description = description;
		this.point = new Point(x, y);
	}

	public int getId() {
		return id;
	}

	public int getBuilding() {
		return building;
	}

	public Point getPoint() {
		return point;
	}

	public String getDescription() {
		return description;
	}

	public int getDoor() {
		return door;
	}
}
