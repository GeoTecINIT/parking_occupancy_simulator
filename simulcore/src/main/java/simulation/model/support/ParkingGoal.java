package simulation.model.support;

public class ParkingGoal implements Comparable<ParkingGoal>{
	private int order;
	private BuildingEntranceDestination destination;
	private Timing departureTimeLap;
	
	public ParkingGoal(int order, BuildingEntranceDestination destination, Timing departureTimeLap) {
		this.order = order;
		this.destination = destination;
		this.departureTimeLap = departureTimeLap;
	}
	
	public int getOrder() {
		return order;
	}

	public Timing getDepartureTimeLap() {
		return departureTimeLap;
	}

	public BuildingEntranceDestination getDestination() {
		return destination;
	}

	@Override
	public int compareTo(ParkingGoal o) {
		return (order - o.order);
	}
}
