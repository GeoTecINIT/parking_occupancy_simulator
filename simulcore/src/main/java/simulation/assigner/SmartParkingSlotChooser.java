package simulation.assigner;

import sim.field.geo.GeomVectorField;
import simulation.common.globals.SlotData;
import simulation.common.smartparkingws.SmartParkingWServices;

import com.esri.core.geometry.Point;

public class SmartParkingSlotChooser extends SlotChooser{

	@Override
	public SlotData chooseSlot(GeomVectorField soltsField, Point point,
			double initialDistance, double finalDistance, double stepDistance) {
		SmartParkingWServices spws = SmartParkingWServices.getInstance();
		SlotData chosen = spws.chooseSlot(point.getX(), point.getY());
		return chosen;
	}
}
