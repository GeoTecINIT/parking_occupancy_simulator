package simulation.assigner;

import java.util.List;

import sim.field.geo.GeomVectorField;
import sim.util.geo.MasonGeometry;
import simulation.common.globals.SlotCurrentState;
import simulation.common.globals.SlotData;

import com.esri.core.geometry.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * 
 * 
 */

public abstract class SlotChooser {
	protected GeometryFactory fact = new GeometryFactory();
	
	public abstract SlotData chooseSlot(GeomVectorField soltsField, Point point, double initialDistance, double finalDistance, double stepDistance);
	
	public SlotData chooseSlot(List<SlotCurrentState> slots, double longitude, double latitude, double initialDistance, double finalDistance, double stepDistance){
		Point point = new Point(longitude, latitude);
		GeomVectorField soltsField = new GeomVectorField();
		for (SlotCurrentState slotCurrentState : slots) {
			double x = slotCurrentState.getSlot().getCoordinates().getLongitude();
			double y = slotCurrentState.getSlot().getCoordinates().getLatitude();
			MasonGeometry location = new MasonGeometry(fact.createPoint(new Coordinate(x, y)));
			location.setUserData(slotCurrentState);
			soltsField.addGeometry(location);
		}
		return chooseSlot(soltsField, point, initialDistance, finalDistance, stepDistance);
	}
}
