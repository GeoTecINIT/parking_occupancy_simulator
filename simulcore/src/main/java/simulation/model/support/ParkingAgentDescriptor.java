package simulation.model.support;

import simulation.data.configs.profiles.AgentProfileTypes;

import com.esri.core.geometry.Point;

public interface ParkingAgentDescriptor {

	public abstract AgentProfileTypes getType();
	
	public abstract String getDescription();

	public abstract long getId();

	public abstract ParkingAgentStates getParkingAgentState();
	
	public abstract Point getPosition();
	
	public abstract double getSpeed();
	
	public abstract double getMaxWalkingDistance();
	
	public abstract double getAcummulatedDistance();
	
	public abstract Timing getCurrentTime();
	
	public abstract String getCurrentDestination();

	public ParkingAgentDescriptor clone();
}