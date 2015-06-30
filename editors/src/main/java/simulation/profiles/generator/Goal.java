package simulation.profiles.generator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import simulation.data.configs.profiles.TimeDefinition;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Goal {
	@XmlElement(name="DepartureTimeLap")
	private TimeDefinition departureTimeLap;
	
	public Goal(){}
	
	public Goal(TimeDefinition departureTimeLap) {
		super();
		this.departureTimeLap = departureTimeLap;
	}
	
	/**
	 * @return the departureTimeLap
	 */
	public TimeDefinition getDepartureTimeLap() {
		return departureTimeLap;
	}

	/**
	 * @param departureTimeLap the departureTimeLap to set
	 */
	public void setDepartureTimeLap(TimeDefinition departureTimeLap) {
		this.departureTimeLap = departureTimeLap;
	}
}
