package simulation.data.configs.profiles;

import javax.xml.bind.annotation.XmlElement;

public class GoalDefinition {
	@XmlElement(name="Order")
	private int order;
	@XmlElement(name="Destination")
	private DestinationDef destination;
	@XmlElement(name="DepartureTimeLap")
	private TimeDefinition departureTimeLap;
	
	public GoalDefinition(){}
	
	public GoalDefinition(DestinationDef destination, TimeDefinition departureTimeLap) {
		this.destination = destination;
		this.departureTimeLap = departureTimeLap;
	}
	
	public GoalDefinition(int order, DestinationDef destination, TimeDefinition departureTimeLap) {
		this.order = order;
		this.destination = destination;
		this.departureTimeLap = departureTimeLap;
	}
	
	public int getOrder() {
		return order;
	}

	public TimeDefinition getDepartureTimeLap() {
		return departureTimeLap;
	}

	public DestinationDef getDestination() {
		return destination;
	}

}
