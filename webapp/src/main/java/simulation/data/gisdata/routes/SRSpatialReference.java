package simulation.data.gisdata.routes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SRSpatialReference {
	private int wkid;
	private int latestWkid;
	
	public SRSpatialReference() {}
	
	public SRSpatialReference(int wkid, int latestWkid) {
		super();
		this.wkid = wkid;
		this.latestWkid = latestWkid;
	}

	public int getWkid() {
		return wkid;
	}

	public int getLatestWkid() {
		return latestWkid;
	}
}
