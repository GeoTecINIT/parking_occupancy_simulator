package simulation.data.configs.profiles;

import javax.xml.bind.annotation.XmlElement;

import simulation.model.support.Timing;

public class TimeDefinition {

	@XmlElement(name="Time")
	private Timing time;
	@XmlElement(name="TimeRandomizer")
	private TimeRandomizer timeRandomizer;
	
	public TimeDefinition(){}
	
	public TimeDefinition(Timing time, TimeRandomizer timeRandomizer,
			Timing parkedTime, TimeRandomizer parkedTimeRandomizer) {
		super();
		this.time = time;
		this.timeRandomizer = timeRandomizer;
	}

	public Timing getTime() {
		return time;
	}
	public TimeRandomizer getTimeRandomizer() {
		return timeRandomizer;
	}
}
