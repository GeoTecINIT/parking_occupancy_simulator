package simulation.consumers.web;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({SlotClientData.class, SlotClientState.class})
public class SlotListDetails<T> {
	@XmlElement
	private long time;
	@XmlElement
	private List<T> slots;
	
	public SlotListDetails(){}
	
	public SlotListDetails(long time, List<T> slots) {
		super();
		this.time = time;
		this.slots = slots;
	}

	public long getTime() {
		return time;
	}

	public List<T> getSlots() {
		return slots;
	}
}
