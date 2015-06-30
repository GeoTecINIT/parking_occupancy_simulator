package simulation.model.support;

import javax.xml.bind.annotation.XmlAttribute;

public class Destination {
	@XmlAttribute(name="description")
	private String description;
	@XmlAttribute(name="description")
	private int building;
	@XmlAttribute(name="description")
	private int door;
	
	public Destination(){}
	
	public Destination(String description, int building, int door) {
		super();
		this.description = description;
		this.building = building;
		this.door = door;
	}

	public String getDescription() {
		return description;
	}

	public int getBuilding() {
		return building;
	}

	public int getDoor() {
		return door;
	}
}
