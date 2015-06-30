package simulation.data.gisdata.routes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Attributes {
	@XmlElement(name="ObjectID")
	private int ObjectID;
	@XmlElement(name="Name")
	private String Name;
	@XmlElement(name="FirstStopID")
	private int FirstStopID;
	@XmlElement(name="LastStopID")
	private int LastStopID;
	@XmlElement(name="StopCount")
	private int StopCount;
	@XmlElement(name="Total_WeightedTime")
	private double Total_WeightedTime;
	@XmlElement(name="Shape_Length")
	private double Shape_Length;
	@XmlElement(name="Total_Length")
	private String Total_Length;
	@XmlElement(name="Total_Time")
	private String Total_Time;
	
	public Attributes() {}
	
	public Attributes(int objectID, String name, int firstStopID,
			int lastStopID, int stopCount, double total_WeightedTime,
			double shape_Length) {
		super();
		ObjectID = objectID;
		Name = name;
		FirstStopID = firstStopID;
		LastStopID = lastStopID;
		StopCount = stopCount;
		Total_WeightedTime = total_WeightedTime;
		Shape_Length = shape_Length;
	}

	public int getObjectID() {
		return ObjectID;
	}

	public String getName() {
		return Name;
	}

	public int getFirstStopID() {
		return FirstStopID;
	}

	public int getLastStopID() {
		return LastStopID;
	}

	public int getStopCount() {
		return StopCount;
	}

	public double getTotal_WeightedTime() {
		return Total_WeightedTime;
	}

	public double getShape_Length() {
		return Shape_Length;
	}
}
