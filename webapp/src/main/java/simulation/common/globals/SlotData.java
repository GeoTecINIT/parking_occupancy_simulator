package simulation.common.globals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SlotData {
	@XmlElement(namespace="http://smartparking.smartways.geotec.uji.es/xsd")
	private SlotCoordinates coordinates = null;
	
	@XmlElement(namespace="http://smartparking.smartways.geotec.uji.es/xsd")
	private Integer objectId;
	
	@XmlElement(namespace="http://smartparking.smartways.geotec.uji.es/xsd")
	private Slot slot;
	
	@XmlElement(namespace="http://smartparking.smartways.geotec.uji.es/xsd")
	private Integer type;

	public SlotData() {
		// TODO Auto-generated constructor stub
	}

	public SlotData(SlotCoordinates coordinates, Integer objectId, Slot slot, Integer type) {
		super();
		this.slot = slot;
		this.type = type;
		this.coordinates = coordinates;
		this.objectId = objectId;
	}
	
	public Integer getParking() {
		return slot.getParking();
	}

	public void setParking(Integer parking) {
		this.slot.setParking(parking);
	}

	public Integer getSlot() {
		return slot.getSlot();
	}

	public void setSlot(Integer slot) {
		this.slot.setSlot(slot);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	
	public SlotCoordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(SlotCoordinates coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String toString() {
		return super.toString() + slot.toString() + ", type: " + type + ", objectId: " + objectId;
	}
}
