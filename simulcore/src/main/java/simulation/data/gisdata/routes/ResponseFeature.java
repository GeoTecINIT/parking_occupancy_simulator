package simulation.data.gisdata.routes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseFeature {
	Attributes attributes;
	ResponseGeometry geometry;
	
	public ResponseFeature() {}
	
	public ResponseFeature(Attributes attributes, ResponseGeometry geometry) {
		super();
		this.attributes = attributes;
		this.geometry = geometry;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public ResponseGeometry getGeometry() {
		return geometry;
	}
}
