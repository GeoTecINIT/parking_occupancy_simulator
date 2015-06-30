package simulation.consumers.web;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SimulStateData {
	
	public static final SimulStateData NULL = new SimulStateData();
	
	@XmlElement
	private int state;
	@XmlElement
	private String time;

	public SimulStateData() {}
	
	public SimulStateData(int state, String time) {
		super();
		this.state = state;
		this.time = time;
	}
	
	public int getState() {
		return state;
	}
	
	public String getTime() {
		return time;
	}
}
