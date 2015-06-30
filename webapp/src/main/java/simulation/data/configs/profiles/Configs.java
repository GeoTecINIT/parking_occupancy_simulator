package simulation.data.configs.profiles;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ConfigsXML")
@XmlAccessorType(XmlAccessType.FIELD)
public class Configs {
	@XmlElement(name="Profiles")
	private List<AgentProfile> profiles;
	
	public Configs(){}
	
	public Configs(List<AgentProfile> profiles){
		this.profiles = profiles;
	}
	
	public List<AgentProfile> getProfiles(){
		return profiles;
	}
}
