package simulation.assigner;

import sim.field.geo.GeomVectorField;
import sim.util.Bag;
import sim.util.geo.MasonGeometry;
import simulation.common.globals.SlotCurrentState;
import simulation.common.globals.SlotData;
import simulation.common.globals.SlotStatusStates;
import simulation.data.gisdata.routes.PedestrianRouteCalulator;
import simulation.data.gisdata.routes.RouteCalulator;
import simulation.model.support.BuildingEntranceDestination;
import simulation.model.support.SimulRoute;

import com.esri.core.geometry.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class GeographicalSlotChooser extends SlotChooser {
	private RouteCalulator routeCalulator = PedestrianRouteCalulator.getInstance();
	
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
					Point slotPoint = new Point(location.geometry.getCoordinate().x, location.geometry.getCoordinate().y);
					SimulRoute pathToDestination = routeCalulator.getPath(point, slotPoint);
					double pointDistance = pathToDestination.getShape_Length();
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
