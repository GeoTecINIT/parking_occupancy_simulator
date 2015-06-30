package simulation.common.smartparkingws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import simulation.common.globals.SlotData;

@XmlRootElement(name="chooseSlotResponse", namespace="http://manageslots.smartparking.smartways.geotec.uji.es")
@XmlAccessorType(XmlAccessType.FIELD)

public class ResponseChooseSlot
{
	@XmlElement(name="return", namespace="http://manageslots.smartparking.smartways.geotec.uji.es")
	private SlotData response;

	public ResponseChooseSlot()	{
	} // JAXB needs this

	public SlotData getResponse() {
		return response;
	}

	public void setResponse(SlotData response) {
		this.response = response;
	}
}