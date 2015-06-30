package simulation.common.smartparkingws;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="setSlotCurrentStatusResponse", namespace="http://manageslots.smartparking.smartways.geotec.uji.es")
@XmlAccessorType(XmlAccessType.FIELD)

public class ResponseChangeStatus
{
	@XmlElement(name="return", namespace="http://manageslots.smartparking.smartways.geotec.uji.es")
	public List<String> responses;

	public ResponseChangeStatus()	{
	} // JAXB needs this
	
	public int response(){
		return Integer.parseInt(responses.get(0));
	}
}