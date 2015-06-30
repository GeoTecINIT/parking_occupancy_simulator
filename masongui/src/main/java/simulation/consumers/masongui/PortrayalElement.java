package simulation.consumers.masongui;

import sim.portrayal.geo.GeomVectorFieldPortrayal;
import com.vividsolutions.jts.geom.Envelope;

public interface PortrayalElement {
	
	public void setFieldMBR (Envelope MBR);
	
	public Envelope getFieldMBR ();
	
	public GeomVectorFieldPortrayal getPortrayal();

	public void setup();
}
