package simulation.profiles.generator;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Master")
@XmlAccessorType(XmlAccessType.FIELD)
public class Master {
	@XmlElement(name="ByCarProportion")
	private double byCarProportion;
	@XmlElement(name="MasterProfiles")
	private List<MasterProfile> masterProfiles;
	
	public Master(){}
	
	public Master(double byCarProportion, List<MasterProfile> masterProfiles) {
		super();
		this.byCarProportion = byCarProportion;
		this.masterProfiles = masterProfiles;
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
