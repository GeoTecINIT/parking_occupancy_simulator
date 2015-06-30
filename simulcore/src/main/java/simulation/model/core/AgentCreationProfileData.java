package simulation.model.core;

import java.util.List;

import simulation.data.configs.profiles.AgentProfileTypes;
import simulation.model.support.EntrancePoint;
import simulation.model.support.ParkingGoal;
import simulation.model.support.Timing;

public class AgentCreationProfileData {
	private AgentProfileTypes type;
	private double maxWalkingDistance;
	private EntrancePoint entryPoint;
	private Timing startingTime;
	private List<ParkingGoal> goals;
	
	public AgentCreationProfileData(AgentProfileTypes type, double maxWalkingDistance,
			EntrancePoint entryPoint, Timing startingTime, List<ParkingGoal> goals) {
		super();
		this.type = type;
		this.maxWalkingDistance = maxWalkingDistance;
		this.entryPoint = entryPoint;
		this.startingTime = startingTime;
		this.goals = goals;
	}

	public AgentProfileTypes getType() {
		return type;
	}
	
	public double getMaxWalkingDistance() {
		return maxWalkingDistance;
	}
	
	public EntrancePoint getEntryPoint() {
		return entryPoint;
	}
	
	public Timing getStartingTime() {
		return startingTime;
	}
	
	public List<ParkingGoal> getGoals() {
		return goals;
	}
}
