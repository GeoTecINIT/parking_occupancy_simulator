package simulation.common.smartparkingws;

import java.util.List;

import javax.xml.bind.annotation.*;

import simulation.common.globals.SlotData;

@XmlRootElement(name="getSlotDataResponse", namespace="http://manageslots.smartparking.smartways.geotec.uji.es")
@XmlAccessorType(XmlAccessType.FIELD)

public class SlotDataXML {
	@XmlElement(name="return", namespace="http://manageslots.smartparking.smartways.geotec.uji.es")
	private List<SlotData> slots;
	
	public SlotDataXML() {
		// TODO Auto-generated constructor stub
	}
	
	public List<SlotData> getSlotObjects() {
		return slots;
	}
}
