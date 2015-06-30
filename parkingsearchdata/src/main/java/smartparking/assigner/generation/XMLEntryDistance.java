package smartparking.assigner.generation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="EntryDistance")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLEntryDistance implements Comparable<XMLEntryDistance> {
	@XmlElement(name="slId")
	private Integer slotDataId;
	@XmlElement(name="d")
	private Double distance;
	
	public XMLEntryDistance(){}
	
	public XMLEntryDistance(Integer slotDataId, Double distance) {
		super();
		this.slotDataId = slotDataId;
		this.distance = distance;
	}
	
	public Integer getSlotData() {
		return slotDataId;
	}
	
	public void setSlotData(Integer slotDataId) {
		this.slotDataId = slotDataId;
	}
	
	public Double getDistance() {
		return distance;
	}
	
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(XMLEntryDistance arg0) {
		return (int)(this.distance - arg0.distance);
	}
}
