package simulation.data.gisdata.routes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FieldAliases {
	@XmlElement(name="ObjectID")
	private String ObjectID;
	@XmlElement(name="Name")
	private String Name;
	@XmlElement(name="FirstStopID")
	private String FirstStopID;
	@XmlElement(name="LastStopID")
	private String LastStopID;
	@XmlElement(name="StopCount")
	private String StopCount;
	@XmlElement(name="Total_WeightedTime")
	private String Total_WeightedTime;
	@XmlElement(name="Shape_Length")
	private String Shape_Length;
	@XmlElement(name="Total_Length")
	private String Total_Length;
	@XmlElement(name="Total_Time")
	private String Total_Time;
	
	public FieldAliases() {}

	public FieldAliases(String objectID, String name, String firstStopID,
			String lastStopID, String stopCount, String total_WeightedTime,
			String shape_Length) {
		super();
		this.ObjectID = objectID;
		this.Name = name;
		this.FirstStopID = firstStopID;
		this.LastStopID = lastStopID;
		this.StopCount = stopCount;
		this.Total_WeightedTime = total_WeightedTime;
		this.Shape_Length = shape_Length;
	}
	
	public String getObjectID() {
		return ObjectID;
	}
	
	public String getName() {
		return Name;
	}

	public String getFirstStopID() {
		return FirstStopID;
	}

	public String getLastStopID() {
		return LastStopID;
	}

	public String getStopCount() {
		return StopCount;
	}

	public String getTotal_WeightedTime() {
		return Total_WeightedTime;
	}

	public String getShape_Length() {
		return Shape_Length;
	}
}
