package simulation.data.gisdata.routes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Feature {
	private SRPoint geometry;
	
	public Feature(){}

	public Feature(SRPoint geometry) {
		super();
		this.geometry = geometry;
	}

	public SRPoint getGeometry() {
		return geometry;
	}
}
