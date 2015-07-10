package simulation.profiles.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simulation.data.configs.profiles.AgentProfile;
import simulation.data.configs.profiles.Configs;
import simulation.data.configs.profiles.DestinationDef;
import simulation.data.configs.profiles.DoubleRandomizer;
import simulation.data.configs.profiles.GoalDefinition;
import simulation.data.configs.profiles.WeekRepetitionType;

/**
 * Hello world!
 *
 */
public class AgentProfilesCreator 
{
	public static List<AgentProfile> buildProfiles(Master master, double globalPercentage, WeekRepetitionType repetition, DoubleRandomizer maxWalkingDistance){
		
		List<AgentProfile> agentProfiles = new ArrayList<AgentProfile>();
    	
    	for (MasterProfile masterProfile : master.getMasterProfiles()) {
    		// Amounts for this profile
    		double totalPeopleAmount = masterProfile.getTotalPeopleAmount();
    		double cantScheduledWithSport = masterProfile.getCantScheduledWithSport();
    		double cantScheduledWithLibrary = masterProfile.getCantScheduledWithLibrary();
    		double cantWithout = masterProfile.getCantWithoutOthers();
    		double totalSchedules = cantWithout + cantScheduledWithLibrary + cantScheduledWithSport;
    		// -----------------------------------------------------
    		
			for (Faculty faculty : masterProfile.getFaculties()) {
				double byCarProportion = masterProfile.getByCarProportion() * globalPercentage;
				int amount = (int)(((double) faculty.getAmount() / totalSchedules) * byCarProportion);
				double facultyPeopleProportion = (double)(faculty.getAmount()) / totalPeopleAmount;
				int acumulated = 0;
				Collections.sort(masterProfile.getSchedules());
				for (Schedule schedule : masterProfile.getSchedules()) {
					// TODO Cambiar lo del identificador del edificio
					GoalDefinition facultyGoal = new GoalDefinition(1, new DestinationDef(faculty.getName(), faculty.getName(), 0), schedule.getFacultyGoal().getDepartureTimeLap());
					if (schedule.getSportGoal() != null){
						for (Sport sport : masterProfile.getSports()) {
							// -----------------------------------
							// Amounts
							int cantSport = (int)(((double)sport.getAmount() * facultyPeopleProportion / cantScheduledWithSport) * byCarProportion) + 1;
							if ((amount - cantSport) > 0) { acumulated += (amount - cantSport); }
							// -----------------------------------
							// Create the final profile
							List<GoalDefinition> goals = new ArrayList<>();
							GoalDefinition sportGoal = new GoalDefinition(2, new DestinationDef(sport.getName(), sport.getName(), 0), schedule.getSportGoal().getDepartureTimeLap());
							goals.add(facultyGoal);
							goals.add(sportGoal);
							AgentProfile agentProfile = new AgentProfile(masterProfile.getName(), cantSport, repetition, maxWalkingDistance, schedule.getStartTime(), goals);
							agentProfiles.add(agentProfile);
							// -----------------------------------
						}
					}
					else if (schedule.getLibraryGoal() != null){
						// -----------------------------------
						// Amounts
						int cantLibrary = (int)(((double)masterProfile.getLibrary().getAmount() * facultyPeopleProportion / cantScheduledWithLibrary) * byCarProportion) + 1;
						if ((amount - cantLibrary) > 0) { acumulated += (amount - cantLibrary); }
						// -----------------------------------
						// Create the final profile
						List<GoalDefinition> goals = new ArrayList<>();
						GoalDefinition libraryGoal = new GoalDefinition(2, new DestinationDef(masterProfile.getLibrary().getName(), masterProfile.getLibrary().getName(), 0), schedule.getLibraryGoal().getDepartureTimeLap());
						goals.add(facultyGoal);
						goals.add(libraryGoal);
						AgentProfile agentProfile = new AgentProfile(masterProfile.getName(), cantLibrary, repetition, maxWalkingDistance, schedule.getStartTime(), goals);
						agentProfiles.add(agentProfile);
						// -----------------------------------
					}
					else{
						// -----------------------------------
						// Amounts
						int realAmount = (int)(amount + acumulated / cantWithout);
						// -----------------------------------
						// Create the final profile
						List<GoalDefinition> goals = new ArrayList<>();
						goals.add(facultyGoal);
						AgentProfile agentProfile = new AgentProfile(masterProfile.getName(), realAmount, repetition, maxWalkingDistance, schedule.getStartTime(), goals);
						agentProfiles.add(agentProfile);
						// -----------------------------------
					}
    			}
			}
		}
    	return agentProfiles;
	}
	
    public static void main( String[] args ) throws Exception
    {
    	String masterXMLPath = "master.xml";
    	String agentXMLPath = "agents.xml";
    	double globalPercentage = 1;
    	
    	if (args.length > 2) {
    		masterXMLPath = args[0];
    		agentXMLPath = args[1];
    		globalPercentage = Double.parseDouble(args[2]);
    	}
    	
    	WeekRepetitionType repetition = WeekRepetitionType.SUNDAY;
    	DoubleRandomizer maxWalkingDistance = new DoubleRandomizer(100, 80);
    	
    	XMLPersistenceManager<Master> masterXMLManager = new XMLPersistenceManager<Master>(Master.class, masterXMLPath);
    	Master master = masterXMLManager.read();
    	
    	List<AgentProfile> agentProfiles = buildProfiles(master, globalPercentage, repetition, maxWalkingDistance);
    	
    	XMLPersistenceManager<Configs> configsXMLManager = new XMLPersistenceManager<Configs>(Configs.class, agentXMLPath);
    	configsXMLManager.setPretty();
    	Configs configs = new Configs(agentProfiles);
    	configsXMLManager.write(configs);
    	
    	System.out.println("Yes, we did it!");
    }
}
