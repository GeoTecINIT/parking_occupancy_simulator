package simulation.consumers.web;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SlotClientState{
	@XmlElement
	private int id;
	@XmlElement
	private int state;
	@XmlElement
	private long time;
	
	public SlotClientState(){}
	
	public SlotClientState(int id, int state, long time) {
		super();
		this.id = id;
		this.state = state;
		this.time = time;
	}
	
	public int getId() {
		return id;
	}

	public int getState() {
		return state;
	}

	public long getTime() {
		return time;
	}
}