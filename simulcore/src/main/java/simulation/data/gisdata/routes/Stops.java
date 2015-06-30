package simulation.data.gisdata.routes;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Stops {
	private String type = "features";
	private boolean doNotLocateOnRestrictedElements = true;
	private List<Feature> features = null;
	private boolean hasZ = false;

	public Stops(){
	}
	
	public Stops(List<Feature> features){
		this.features = features;
	}

	public Stops(String type, boolean doNotLocateOnRestrictedElements, List<Feature> features) {
		super();
		this.type = type;
		this.features = features;
		this.doNotLocateOnRestrictedElements = doNotLocateOnRestrictedElements;
	}

	public String getType() {
		return type;
	}

	public boolean isDoNotLocateOnRestrictedElements() {
		return doNotLocateOnRestrictedElements;
	}

	public List<Feature> getFeatures() {
		return features;
	}
	
	public boolean isHasZ() {
		return hasZ;
	}
}
