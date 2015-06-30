package simulation.profiles.generator;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Master")
@XmlAccessorType(XmlAccessType.FIELD)
public class Master {
	@XmlElement(name="MasterProfiles")
	private List<MasterProfile> masterProfiles;
	
	public Master(){}
	
	public Master(List<MasterProfile> masterProfiles) {
		super();
		this.masterProfiles = masterProfiles;
	}
	
	/**
	 * @return the masterProfiles
	 */
	public List<MasterProfile> getMasterProfiles() {
		return masterProfiles;
	}
	
	/**
	 * @param masterProfiles the masterProfiles to set
	 */
	public void setMasterProfiles(List<MasterProfile> masterProfiles) {
		this.masterProfiles = masterProfiles;
	}
}
