package simulation.common.globals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SlotCoordinates {
	@XmlElement(namespace="http://smartparking.smartways.geotec.uji.es/xsd")
	private double longitude;
	
	@XmlElement(namespace="http://smartparking.smartways.geotec.uji.es/xsd")
	private double latitude;
	
	public SlotCoordinates() {}
	
	public SlotCoordinates(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public SlotCoordinates clone(){
		return new SlotCoordinates(getLongitude(), getLatitude());
	}
}
