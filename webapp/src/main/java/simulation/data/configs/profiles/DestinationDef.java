package simulation.data.configs.profiles;

import javax.xml.bind.annotation.XmlAttribute;

public class DestinationDef {
	@XmlAttribute(name="description")
	private String description;
	@XmlAttribute(name="building")
	private String building;
	@XmlAttribute(name="door")
	private int door;
	
	public DestinationDef(){}
	
	public DestinationDef(String description, String building, int door) {
		super();
		this.description = description;
		this.building = building;
		this.door = door;
	}

	public String getDescription() {
		return description;
	}

	public String getBuilding() {
		return building;
	}

	public int getDoor() {
		return door;
	}
}
