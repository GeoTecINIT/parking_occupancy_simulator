package simulation.consumers.web;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SlotClientData{
	@XmlTransient
	public static final SlotClientData NULL = new SlotClientData();
	
	@XmlElement
	private int id;
	@XmlElement
	private double lon;
	@XmlElement
	private double lat;
	
	public SlotClientData(){}
	
	public SlotClientData(int id, double lon, double lat) {
		super();
		this.id = id;
		this.lon = lon;
		this.lat = lat;
	}
	
	public int getId() {
		return id;
	}
	
	public double getLon() {
		return lon;
	}
	
	public double getLat() {
		return lat;
	}
}