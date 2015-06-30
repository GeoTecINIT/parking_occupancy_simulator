package smartparking.assigner.generation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="EntranceSlotsDistances")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLEntranceSlotsDistances {
	@XmlElement(name="entrId")
	private Integer buildingEntranceId;
	@XmlElement(name="ED")
	private List<XMLEntryDistance> entryDistances;
	
	public XMLEntranceSlotsDistances(){}

	public XMLEntranceSlotsDistances(Integer buildingEntranceId,
			List<XMLEntryDistance> entryDistances) {
		super();
		this.buildingEntranceId = buildingEntranceId;
		this.entryDistances = entryDistances;
	}

	public Integer getBuildingEntrance() {
		return buildingEntranceId;
	}

	public void setBuildingEntrance(Integer buildingEntranceId) {
		this.buildingEntranceId = buildingEntranceId;
	}

	public List<XMLEntryDistance> getEntryDistances() {
		return entryDistances;
	}

	public void setEntryDistances(List<XMLEntryDistance> entryDistances) {
		this.entryDistances = entryDistances;
	}
}
