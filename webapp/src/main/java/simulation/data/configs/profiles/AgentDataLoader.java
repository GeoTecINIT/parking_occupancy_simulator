package simulation.data.configs.profiles;

import java.util.ArrayList;
import java.util.List;

import simulation.common.globals.RandomGenerator;
import simulation.data.gisdata.places.EntrancesGetter;
import simulation.model.core.AgentCreationProfileData;
import simulation.model.core.AgentPlanInitiator;
import simulation.model.core.DestinationMap;
import simulation.model.support.BuildingEntranceDestination;
import simulation.model.support.EntrancePoint;
import simulation.model.support.ParkingGoal;
import simulation.model.support.Timing;

public class AgentDataLoader {
	private DestinationMap destinationMap = null;
	List<EntrancePoint> entrances;
	
	public AgentDataLoader(EntrancesGetter entriesGetter,	DestinationMap destinationMap) {
		super();
		this.entrances = entriesGetter.getEntries();
		this.destinationMap = destinationMap;
	}

	public List<AgentPlanInitiator> defineAgentsFromProfile (AgentProfile profile, double carSpeed) {
		List<AgentPlanInitiator> agentsData = new ArrayList<AgentPlanInitiator>();
		List<Integer> repetitions = WeekRepetitionType.getDaysFor(profile.getRepetition());
		for(int i = 0; i < profile.getAmount(); i++){
			for (Integer day : repetitions){
				Timing weekDayTime = new Timing(day, 0, 0, 0);
				List<ParkingGoal> resultinGoals = new ArrayList<ParkingGoal>();
				for (GoalDefinition goalDef : profile.getGoals()) {
					Timing randomizedParkedTime = getRandomizedTime(goalDef.getDepartureTimeLap());
					BuildingEntranceDestination destinationPoint = getDestination(goalDef.getDestination());
					resultinGoals.add(new ParkingGoal(goalDef.getOrder(), destinationPoint, randomizedParkedTime));
				}
				Timing randomizedStartTime = getRandomizedTime(profile.getStartTime());
				Timing resultingStartTime = weekDayTime.getAddedTime(randomizedStartTime);
				AgentPlanInitiator agentPlanInitiator = new AgentPlanInitiator(new AgentCreationProfileData(profile.getType(), profile.getMaxWalkingDistance().getRandom(), getRandomEntrance(), resultingStartTime, resultinGoals));
				agentsData.add(agentPlanInitiator);
			}
		}
		return agentsData;
	}
	
	private Timing getRandomizedTime(TimeDefinition timeDefinition){
		Timing time = timeDefinition.getTime();
		TimeRandomizer rndz = timeDefinition.getTimeRandomizer();
		Timing randomizedTime = time.getAddedTime(rndz.getRandom());
		return randomizedTime;
	}
	
	private BuildingEntranceDestination getDestination (DestinationDef destinationDef){
		BuildingEntranceDestination chosen;
		// If door is specified, get that one
		chosen = destinationMap.getDestinationFor(destinationDef.getBuilding(), destinationDef.getDoor());
		if (chosen != BuildingEntranceDestination.NULL){
			return chosen;
		}
		// If building is specified, get one random entrance from that building
		chosen = destinationMap.getRandomDestinationFor(destinationDef.getBuilding());
		if (chosen != BuildingEntranceDestination.NULL){
			return chosen;
		}
		// else, get anyone randomly
		chosen = destinationMap.getAnyRandomDestination();
		return chosen;
	}
	
	private EntrancePoint getRandomEntrance (){
		int amount = entrances.size();
		int chosen = RandomGenerator.getInstance().nextInt(amount);
		return entrances.get(chosen);
	}
}
