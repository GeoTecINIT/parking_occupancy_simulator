package simulation.consumers.masongui;

import static simulation.consumers.masongui.GUIGlobalConstants.*;

import java.util.HashMap;

import sim.field.geo.GeomVectorField;
import sim.portrayal.geo.GeomVectorFieldPortrayal;
import sim.util.geo.MasonGeometry;
import simulation.common.globals.SlotCurrentState;
import simulation.common.globals.SlotStatusStates;
import simulation.model.wrapping.SlotCurrentStateUpdater;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;

public class SlotsVisualController extends SlotCurrentStateUpdater implements PortrayalElement{
	public class DoubleKey{
		private int parking;
		private int slot;
		
		public DoubleKey(int parking, int slot) {
			super();
			this.parking = parking;
			this.slot = slot;
		}
		
		public int getParking() {
			return parking;
		}
		
		public int getSlot() {
			return slot;
		}
		
		@Override
	    public int hashCode() {
	        return this.parking ^ this.slot;
	    }
		
		@Override
		public boolean equals(Object obj) {
			DoubleKey other = (DoubleKey)obj;
			return ((other.parking == parking) && (other.slot == slot));
		}
	}
	
	private final String ATTRIBUTE_STATE = "STATE";
	private GeomVectorField slotsField;
	private GeomVectorFieldPortrayal slotsPortrayal;
	private HashMap<DoubleKey, MasonGeometry> locations;
	private GeometryFactory fact = new GeometryFactory();
	
	public SlotsVisualController(int width, int height) {
		super();
		locations = new HashMap<DoubleKey, MasonGeometry>();
		slotsField = new GeomVectorField(width, height);
		slotsPortrayal = new GeomVectorFieldPortrayal();
	}
	
	@Override
	public void setFieldMBR (Envelope MBR) {
		slotsField.setMBR(MBR);
	}
	
	@Override
	public Envelope getFieldMBR () {
		return slotsField.getMBR();
	}
	
	@Override
	public GeomVectorFieldPortrayal getPortrayal() {
		return slotsPortrayal;
	}
	
	@Override
	public void setup(){
		slotsField.clear();
		locations.clear();
        slotsPortrayal.setField(slotsField);
        slotsPortrayal.setPortrayalForAll(new SlotOvalPortrayal2D(SLOT_SYMBOL_SCALE, ATTRIBUTE_STATE));
	}

	@Override
	protected void doUpdate(SlotCurrentState slot) {
		MasonGeometry location;
		DoubleKey key = new DoubleKey(slot.getSlot().getParking(), slot.getSlot().getSlot());
		location = locations.get(key);
		if (location == null){
			double x = slot.getSlot().getCoordinates().getLongitude();
			double y = slot.getSlot().getCoordinates().getLatitude();
			location = new MasonGeometry(fact.createPoint(new Coordinate(x, y)));
			locations.put(key, location);
			slotsField.addGeometry(location);
		}
		location.addAttribute(ATTRIBUTE_STATE, SlotStatusStates.valueOf(slot.getStatus()).toString());
	}
}
