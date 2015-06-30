package simulation.model.core;

import java.util.Collections;
import java.util.List;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import simulation.model.support.BasicParkingAgent;
import simulation.model.support.EntrancePoint;
import simulation.model.support.ParkingGoal;
import simulation.model.support.Timing;

public abstract class ParkingAgent extends BasicParkingAgent implements Steppable{
	protected static final long serialVersionUID = 1L;
	
	protected Timing startingTime;
	protected List<ParkingGoal> goals;
	protected int currentSchedule;
	protected EntrancePoint entrancePoint;
	private Stoppable stoppable;

	public void enableStop(Stoppable stoppable){
		this.stoppable = stoppable;
	}
	
	public void stop(){
		stoppable.stop();
	}
	
	public Timing getStartingTime(){
		return startingTime;
	}
	
	/*
	 * It is assumed the agentSpeed is already in meter / time units
	 */
	public ParkingAgent(AgentCreationProfileData agentCreationProfileData, double agentSpeed){
		super(agentCreationProfileData.getType(), agentSpeed, agentCreationProfileData.getMaxWalkingDistance());
		this.goals = agentCreationProfileData.getGoals();
		Collections.sort(this.goals);
		currentSchedule = 0;
		this.entrancePoint = agentCreationProfileData.getEntryPoint();
		this.startingTime = agentCreationProfileData.getStartingTime();
	}
	
	public abstract void doStep(SimState state);

	public void step(SimState state) {
		doStep(state);
	}
}
