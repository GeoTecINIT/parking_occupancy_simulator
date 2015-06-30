package simulation.profiles.generator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import simulation.data.configs.profiles.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Schedule implements Comparable<Schedule>{
	@XmlElement(name="StartTime")
	private TimeDefinition startTime;
	@XmlElement(name="FacultyGoal")
	private Goal facultyGoal;
	@XmlElement(name="LibraryGoal")
	private Goal libraryGoal;
	@XmlElement(name="SportGoal")
	private Goal sportGoal;
	
	public Schedule(){}

	public Schedule(TimeDefinition startTime, Goal facultyGoal,
			Goal libraryGoal, Goal sportGoal) {
		super();
		this.startTime = startTime;
		this.facultyGoal = facultyGoal;
		this.libraryGoal = libraryGoal;
		this.sportGoal = sportGoal;
	}

	/**
	 * @return the startTime
	 */
	public TimeDefinition getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(TimeDefinition startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the facultyGoal
	 */
	public Goal getFacultyGoal() {
		return facultyGoal;
	}

	/**
	 * @param facultyGoal the facultyGoal to set
	 */
	public void setFacultyGoal(Goal facultyGoal) {
		this.facultyGoal = facultyGoal;
	}

	/**
	 * @return the libraryGoal
	 */
	public Goal getLibraryGoal() {
		return libraryGoal;
	}

	/**
	 * @param libraryGoal the libraryGoal to set
	 */
	public void setLibraryGoal(Goal libraryGoal) {
		this.libraryGoal = libraryGoal;
	}

	/**
	 * @return the sportGoal
	 */
	public Goal getSportGoal() {
		return sportGoal;
	}

	/**
	 * @param sportGoal the sportGoal to set
	 */
	public void setSportGoal(Goal sportGoal) {
		this.sportGoal = sportGoal;
	}

	@Override
	public int compareTo(Schedule arg) {
		int thisHave = 0, argHave = 0;
		if ((this.getSportGoal() != null) || (this.getLibraryGoal() != null)){
			thisHave = 1;
		}
		if ((arg.getSportGoal() != null) || (arg.getLibraryGoal() != null)){
			argHave = 1;
		}
		return (argHave - thisHave);
	}
}
