package smartparking.assigner.generation.screquests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FieldAliases {
	@XmlElement(name="OBJECTID")
	private String objectID;
	@XmlElement(name="Z_Coordinate")
	private String zCoordinate;
	@XmlElement(name="Floor")
	private String floor;
	@XmlElement(name="BuildingID")
	private String buildingID;
	@XmlElement(name="Type")
	private String type;
	
	public FieldAliases() {}

	public FieldAliases(String objectID, String zCoordinate, String floor,
			String buildingID, String type) {
		super();
		this.objectID = objectID;
		this.zCoordinate = zCoordinate;
		this.floor = floor;
		this.buildingID = buildingID;
		this.type = type;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	public String getzCoordinate() {
		return zCoordinate;
	}

	public void setzCoordinate(String zCoordinate) {
		this.zCoordinate = zCoordinate;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(String buildingID) {
		this.buildingID = buildingID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
