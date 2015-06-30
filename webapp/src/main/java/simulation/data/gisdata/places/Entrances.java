package simulation.data.gisdata.places;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="")
@XmlAccessorType(XmlAccessType.FIELD)
public class Entrances {
	@XmlElement(name="displayFieldName")
	private String displayFieldName;
	@XmlElement(name="fieldAliases")
	private FieldAliases fieldAliases;
	@XmlElement(name="geometryType")
	private String geometryType;
	@XmlElement(name="spatialReference")
	private SRSpatialReference spatialReference;
	@XmlElement(name="fields")
	private List<Field> fields;
	@XmlElement(name="features")
	private List<Feature> features;
	
	public Entrances() {}

	public Entrances(String displayFieldName, FieldAliases fieldAliases,
			String geometryType, SRSpatialReference spatialReference,
			List<Field> fields, List<Feature> features) {
		super();
		this.displayFieldName = displayFieldName;
		this.fieldAliases = fieldAliases;
		this.geometryType = geometryType;
		this.spatialReference = spatialReference;
		this.fields = fields;
		this.features = features;
	}

	public String getDisplayFieldName() {
		return displayFieldName;
	}

	public void setDisplayFieldName(String displayFieldName) {
		this.displayFieldName = displayFieldName;
	}

	public FieldAliases getFieldAliases() {
		return fieldAliases;
	}

	public void setFieldAliases(FieldAliases fieldAliases) {
		this.fieldAliases = fieldAliases;
	}

	public String getGeometryType() {
		return geometryType;
	}

	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}

	public SRSpatialReference getSpatialReference() {
		return spatialReference;
	}

	public void setSpatialReference(SRSpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
}
