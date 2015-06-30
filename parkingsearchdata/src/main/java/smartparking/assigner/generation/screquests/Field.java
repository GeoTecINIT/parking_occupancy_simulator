package smartparking.assigner.generation.screquests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Field {
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="type")
	private String type;
	@XmlElement(name="alias")
	private String alias;
	@XmlElement(name="length")
	private String length;
	
	public Field(){}

	public Field(String name, String type, String alias, String length) {
		super();
		this.name = name;
		this.type = type;
		this.alias = alias;
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
}
