package smartparking.assigner.generation.screquests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Feature {
	private Attributes attributes;
	private SRPoint geometry;
	
	public Feature(){}
	
	public Feature(SRPoint geometry, Attributes attributes) {
		super();
		this.attributes = attributes;
		this.geometry = geometry;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public SRPoint getGeometry() {
		return geometry;
	}

	public void setGeometry(SRPoint geometry) {
		this.geometry = geometry;
	}
}
