package simulation.data.configs.profiles;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class AgentProfile {
	@XmlElement(name="Type")
	private AgentProfileTypes type;
	@XmlElement(name="Amount")
	private int amount;
	@XmlElement(name="Repetition")
	private WeekRepetitionType repetition;
	@XmlElement(name="MaxWalkingDistance")
	private DoubleRandomizer maxWalkingDistance;
	@XmlElement(name="StartTime")
	private TimeDefinition startTime;
	@XmlElement(name="Goals")
	private List<GoalDefinition> goals;
	
	public AgentProfile(){}
	
	public AgentProfile(AgentProfileTypes type, int amount, WeekRepetitionType repetition, TimeDefinition startTime, List<GoalDefinition> goals) {
		super();
		this.type = type;
		this.amount = amount;
		this.repetition = repetition;
		this.startTime = startTime;
		this.goals = goals;
	}

	public AgentProfileTypes getType() {
		return type;
	}

	public int getAmount() {
		return amount;
	}
	
	public WeekRepetitionType getRepetition(){
		return repetition;
	}
	
	public DoubleRandomizer getMaxWalkingDistance() {
		return maxWalkingDistance;
	}

	public TimeDefinition getStartTime(){
		return startTime;
	}

	public List<GoalDefinition> getGoals() {
		return goals;
	}
}
