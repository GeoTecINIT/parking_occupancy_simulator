package simulation.common.globals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Slot {
	@XmlElement(namespace="http://smartparking.smartways.geotec.uji.es/xsd")
	private Integer parking;
	
	@XmlElement(namespace="http://smartparking.smartways.geotec.uji.es/xsd")
	private Integer slot;
	
	public Slot() {
		// TODO Auto-generated constructor stub
	}

	public Slot(Integer parking, Integer slot) {
		super();
		this.parking = parking;
		this.slot = slot;
	}
	
	public Integer getParking() {
		return parking;
	}

	public void setParking(Integer parking) {
		this.parking = parking;
	}

	public Integer getSlot() {
		return slot;
	}

	public void setSlot(Integer slot) {
		this.slot = slot;
	}
	
	@Override
	public String toString() {
		return super.toString() + " parking: " + getParking() + ", slot: " + getSlot();
	}
}
