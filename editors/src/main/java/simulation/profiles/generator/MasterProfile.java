package simulation.profiles.generator;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import simulation.data.configs.profiles.AgentProfileTypes;
import simulation.data.configs.profiles.DoubleRandomizer;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MasterProfile {
	@XmlElement(name="ByCarProportion")
	private double byCarProportion;
	@XmlElement(name="Name")
	private AgentProfileTypes name;
	@XmlElement(name="Schedules")
	private List<Schedule> schedules;
	@XmlElement(name="Faculties")
	private List<Faculty> faculties;
	@XmlElement(name="Library")
	private Library library;
	@XmlElement(name="Sports")
	private List<Sport> sports;
	
	public MasterProfile(){}
	
	public MasterProfile(double byCarProportion, AgentProfileTypes name, DoubleRandomizer maxWalkingDistance, List<Schedule> schedules,
			List<Faculty> faculties, Library library, List<Sport> sports) {
		super();
		this.byCarProportion = byCarProportion;
		this.name = name;
		this.schedules = schedules;
		this.faculties = faculties;
		this.library = library;
		this.sports = sports;
	}
	
	/**
	 * @return the byCarProportion
	 */
	public double getByCarProportion() {
		return byCarProportion;
	}
	
	/**
	 * @param byCarProportion the byCarProportion to set
	 */
	public void setByCarProportion(double byCarProportion) {
		this.byCarProportion = byCarProportion;
	}

	/**
	 * @return the name
	 */
	public AgentProfileTypes getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(AgentProfileTypes name) {
		this.name = name;
	}

	/**
	 * @return the schedules
	 */
	public List<Schedule> getSchedules() {
		return schedules;
	}

	/**
	 * @param schedules the schedules to set
	 */
	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	/**
	 * @return the faculties
	 */
	public List<Faculty> getFaculties() {
		return faculties;
	}

	/**
	 * @param faculties the faculties to set
	 */
	public void setFaculties(List<Faculty> faculties) {
		this.faculties = faculties;
	}

	/**
	 * @return the library
	 */
	public Library getLibrary() {
		return library;
	}

	/**
	 * @param library the library to set
	 */
	public void setLibrary(Library library) {
		this.library = library;
	}

	/**
	 * @return the sports
	 */
	public List<Sport> getSports() {
		return sports;
	}

	/**
	 * @param sports the sports to set
	 */
	public void setSports(List<Sport> sports) {
		this.sports = sports;
	}
	
	public int getTotalPeopleAmount(){
		int peopleAmount = 0;
		for (Faculty faculty : getFaculties()) {
			peopleAmount += faculty.getAmount(); 
		}
		return peopleAmount;
	}
	
	public int getCantScheduledWithSport(){
		int cantScheduledWithSport = 0;
		for (Schedule schedule : getSchedules()) {
			if (schedule.getSportGoal() != null){
				cantScheduledWithSport++;
			}
		}
		cantScheduledWithSport *= getSports().size();
		return cantScheduledWithSport;
	}
	
	public int getCantScheduledWithLibrary(){
		int cantScheduledWithLibrary = 0;
		for (Schedule schedule : getSchedules()) {
			if (schedule.getLibraryGoal() != null){
				cantScheduledWithLibrary++;
			}
		}
		return cantScheduledWithLibrary;
	}
	
	public int getCantWithoutOthers(){
		int cantWithoutOthers = 0;
		for (Schedule schedule : getSchedules()) {
			if ((schedule.getSportGoal() == null) && (schedule.getLibraryGoal() == null)){
				cantWithoutOthers++;
			}
		}
		return cantWithoutOthers;
	}
}
