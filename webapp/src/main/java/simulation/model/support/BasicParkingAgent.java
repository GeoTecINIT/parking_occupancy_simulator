package simulation.model.support;

import java.util.concurrent.atomic.AtomicLong;

import simulation.data.configs.profiles.AgentProfileTypes;

import com.esri.core.geometry.Point;

public class BasicParkingAgent implements ParkingAgentDescriptor {

	private long id;
	private static final AtomicLong sequence = new AtomicLong();	// Shared among agents for getting theirs IDs
	private AgentProfileTypes type;
	private double speed;
	private double maxWalkingDistance;
	protected double acummulatedDistance;
	protected ParkingAgentStates parkingAgentState;
	protected Timing currentTime;
	protected String currentDestDescription;
	protected Point position;
	protected String description;
	
	public BasicParkingAgent(AgentProfileTypes type, double speed, double maxWalkingDistance){
		this.type = type;
		this.speed = speed;
		this.maxWalkingDistance = maxWalkingDistance;
		this.acummulatedDistance = 0;
		this.id = sequence.incrementAndGet();
		this.parkingAgentState = ParkingAgentStates.Starting;
		this.description = "";
	}

	/* (non-Javadoc)
	 * @see parking.defs.ParkingAgentDescriptor#getName()
	 */
	@Override
	public AgentProfileTypes getType() {
		return type;
	}
	
	/* (non-Javadoc)
	 * @see parking.defs.ParkingAgentDescriptor#getId()
	 */
	@Override
	public long getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see parking.defs.ParkingAgentDescriptor#getParkingAgentState()
	 */
	@Override
	public ParkingAgentStates getParkingAgentState(){
		return parkingAgentState;
	}

	@Override
	public double getSpeed() {
		return speed;
	}
	
	@Override
	public double getMaxWalkingDistance() {
		return maxWalkingDistance;
	}

	@Override
	public double getAcummulatedDistance() {
		return acummulatedDistance;
	}
	
	@Override
	public Timing getCurrentTime() {
		return currentTime;
	}
	
	@Override
	public String getCurrentDestination() {
		return currentDestDescription;
	}
	
	@Override
	public Point getPosition() {
		return position;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public BasicParkingAgent clone(){
		BasicParkingAgent newOne = new BasicParkingAgent(getType(), getSpeed(), getMaxWalkingDistance());
		newOne.acummulatedDistance = getAcummulatedDistance();
		newOne.currentDestDescription = getCurrentDestination();
		newOne.currentTime = getCurrentTime();
		newOne.id = getId();
		newOne.parkingAgentState = getParkingAgentState();
		newOne.position = getPosition();
		newOne.description = getDescription();
		return newOne;
	}
}
