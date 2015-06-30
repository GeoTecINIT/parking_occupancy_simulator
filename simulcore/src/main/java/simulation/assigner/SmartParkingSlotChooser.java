package simulation.assigner;

import sim.field.geo.GeomVectorField;
import simulation.common.globals.SlotData;
import simulation.common.smartparkingws.SmartParkingWServices;
import simulation.model.support.BuildingEntranceDestination;

import com.esri.core.geometry.Point;

public class SmartParkingSlotChooser extends SlotChooser{

	@Override
	public SlotData chooseSlot(BuildingEntranceDestination destination, GeomVectorField soltsField, Point point,
			double initialDistance, double finalDistance, double stepDistance) {
		SmartParkingWServices spws = SmartParkingWServices.getInstance();
		//SlotData chosen = spws.assignSlotCoord(point.getX(), point.getY()); 
		SlotData chosen = spws.assignSlotEntrance(destination.getDoor());
		return chosen;
	}
}
