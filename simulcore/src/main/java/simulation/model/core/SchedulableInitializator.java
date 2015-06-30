package simulation.model.core;

import java.util.List;

import sim.engine.SimState;
import sim.engine.Steppable;
import simulation.common.globals.SlotCurrentState;
import simulation.model.wrapping.ModelNotification;
import simulation.model.wrapping.ModelNotificationType;

public class SchedulableInitializator implements Steppable{
	private static final long serialVersionUID = 1L;
	
	List<SlotCurrentState> slots = null;

	public SchedulableInitializator(List<SlotCurrentState> slots){
		this.slots = slots;
	}
	
	@Override
	public void step(SimState state) {
		ParkingModel parkingModel = (ParkingModel)state;
		parkingModel.notifyGlobal(new ModelNotification(ModelNotificationType.INIT, slots));
	}
}