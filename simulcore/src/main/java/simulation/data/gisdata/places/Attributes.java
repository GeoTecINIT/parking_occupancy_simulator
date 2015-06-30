package simulation.data.gisdata.places;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Attributes {
	@XmlElement(name="OBJECTID")
	private int objectID;
	@XmlElement(name="Z_Coordinate")
	private int zCoordinate;
	@XmlElement(name="Floor")
	private int floor;
	@XmlElement(name="BuildingID")
	private String buildingID;
	@XmlElement(name="Type")
	private int type;
	
	public Attributes() {}
	
	public Attributes(int objectID, int zCoordinate, int floor,
			String buildingID, int type) {
		super();
		this.objectID = objectID;
		this.zCoordinate = zCoordinate;
		this.floor = floor;
		this.buildingID = buildingID;
		this.type = type;
	}

	public int getObjectID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}

	public int getZCoordinate() {
		return zCoordinate;
	}

	public void setZCoordinate(int zCoordinate) {
		this.zCoordinate = zCoordinate;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(String buildingID) {
		this.buildingID = buildingID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
