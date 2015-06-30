package simulation.common.globals;

public class BuildingEntranceCoordinate {
	private Double longitude;
	private Double latitude;
	
	public BuildingEntranceCoordinate(){
		super();
	}
	
	public BuildingEntranceCoordinate(Double longitude, Double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
}
