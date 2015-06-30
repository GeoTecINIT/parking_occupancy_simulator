package simulation.consumers.masongui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import simulation.common.globals.SlotCurrentState;
import simulation.model.wrapping.ModelNotification;

public class SlotsInitiatior implements Observer{
	SlotsVisualController slotsVisualController = null;

	public SlotsInitiatior(SlotsVisualController slotsVisualController) {
		super();
		this.slotsVisualController = slotsVisualController;
	}

	@Override
	@SuppressWarnings(value = {"incomplete-switch" })
	public void update(Observable o, Object arg) {
		ModelNotification notification = (ModelNotification) arg;
		switch (notification.getType()) {
		case INIT:
			@SuppressWarnings("unchecked")
			List<SlotCurrentState> slots = (List<SlotCurrentState>)notification.getValue();
			for (SlotCurrentState slotCurrentState : slots) {
				slotsVisualController.doUpdate(slotCurrentState);
			}
			break;
		}
	}
}
