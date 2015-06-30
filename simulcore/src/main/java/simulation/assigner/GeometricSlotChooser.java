package simulation.assigner;

import sim.field.geo.GeomVectorField;
import sim.util.Bag;
import sim.util.geo.MasonGeometry;
import simulation.common.globals.SlotCurrentState;
import simulation.common.globals.SlotData;
import simulation.common.globals.SlotStatusStates;
import simulation.model.support.BuildingEntranceDestination;

import com.esri.core.geometry.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class GeometricSlotChooser extends SlotChooser {
	
	@Override
	public SlotData chooseSlot(BuildingEntranceDestination destination, GeomVectorField soltsField, Point point,
			double initialDistance, double finalDistance, double stepDistance) {
		SlotCurrentState chosen = null;
		boolean found = false;
		Geometry center = fact.createPoint(new Coordinate(point.getX(), point.getY()));
		Geometry currBuffer, prevBuffer = null;
		for(double distance = initialDistance; (distance <= finalDistance) && (found == false); distance+= stepDistance){
			Geometry areaToExplore;
			currBuffer = center.buffer(distance);
			if (prevBuffer == null){
				areaToExplore = currBuffer;
			}
			else{
				areaToExplore = currBuffer.difference(prevBuffer);
			}
			
			Bag candidates = soltsField.getCoveredObjects(new MasonGeometry(areaToExplore));
			double minDistance = Double.MAX_VALUE;
			for (Object object : candidates) {
				MasonGeometry location = (MasonGeometry) object;
				SlotCurrentState slot = (SlotCurrentState)(location.getUserData());
				if (slot.getStatus() == SlotStatusStates.AVAILABLE.getValue()){
					found = true;
					double pointDistance = center.distance(location.geometry);
					if (minDistance >= pointDistance){
						minDistance = pointDistance;
						chosen = slot;
					}
				}
			}
			prevBuffer = currBuffer;
		}
		return chosen.getSlot();
	}
}
