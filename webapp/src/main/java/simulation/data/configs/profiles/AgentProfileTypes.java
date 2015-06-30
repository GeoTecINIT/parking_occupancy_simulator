package simulation.data.configs.profiles;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum AgentProfileTypes {
	@XmlEnumValue ("Estudiante") ESTUDIANTE(1),
	@XmlEnumValue ("PDI") PDI(2),
	@XmlEnumValue ("PAS") PAS(3);
	
	private int value;
	private AgentProfileTypes(int value) {
	   this.value = value;
	}
	public int getValue() {
	   return value;
	}
}
