package simulation.data.gisdata.routes;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Routes {
	private FieldAliases fieldAliases;
	private String geometryType;
	private SRSpatialReference spatialReference;
	private List<ResponseFeature> features;
	
	public Routes() {}
	
	public Routes(FieldAliases fieldAliases, String geometryType,
			SRSpatialReference spatialReference, List<ResponseFeature> features) {
		super();
		this.fieldAliases = fieldAliases;
		this.geometryType = geometryType;
		this.spatialReference = spatialReference;
		this.features = features;
	}

	public FieldAliases getFieldAliases() {
		return fieldAliases;
	}

	public String getGeometryType() {
		return geometryType;
	}

	public SRSpatialReference getSpatialReference() {
		return spatialReference;
	}

	public List<ResponseFeature> getFeatures() {
		return features;
	}
}
