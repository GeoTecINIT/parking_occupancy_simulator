package simulation.common.globals;

public class BuildingEntrance {
	public static BuildingEntrance NULL = new BuildingEntrance();

	private Integer doorId;
	private String description;
	private BuildingEntranceCoordinate coordinates;
	private String scBuildingId;
	
	public BuildingEntrance() {
		super();
	}

	public BuildingEntrance(Integer doorId, String scBuildingId, String description, BuildingEntranceCoordinate coordinates) {
		super();
		this.doorId = doorId;
		this.scBuildingId = scBuildingId;
		this.coordinates = coordinates;
	}
	
	public BuildingEntrance(Integer doorId, String scBuildingId, String description, Double longitude, Double latitude) {
		super();
		this.doorId = doorId;
		this.scBuildingId = scBuildingId;
		this.description = description;
		this.coordinates = new BuildingEntranceCoordinate(longitude, latitude);
	}

	public Integer getDoorId() {
		return doorId;
	}

	public void setDoorId(Integer doorId) {
		this.doorId = doorId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BuildingEntranceCoordinate getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(BuildingEntranceCoordinate coordinates) {
		this.coordinates = coordinates;
	}

	public String getScBuildingId() {
		return scBuildingId;
	}

	public void setScBuildingId(String scBuildingId) {
		this.scBuildingId = scBuildingId;
	}
}
