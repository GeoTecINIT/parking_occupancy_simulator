package smartparking.assigner.generation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="entranceSlotsDistancesCollection")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLEntranceSlotsDistancesCollection {
	@XmlElement(name="esdists")
	private List<XMLEntranceSlotsDistances> entranceSlotsDistances;
	
	public XMLEntranceSlotsDistancesCollection(){}

	public XMLEntranceSlotsDistancesCollection(
			List<XMLEntranceSlotsDistances> entranceSlotsDistances) {
		super();
		this.entranceSlotsDistances = entranceSlotsDistances;
	}

	public List<XMLEntranceSlotsDistances> getEntranceSlotsDistances() {
		return entranceSlotsDistances;
	}

	public void setEntranceSlotsDistances(
			List<XMLEntranceSlotsDistances> entranceSlotsDistances) {
		this.entranceSlotsDistances = entranceSlotsDistances;
	}
}
