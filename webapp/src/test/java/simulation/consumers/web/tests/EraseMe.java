package simulation.consumers.web.tests;

import simulation.common.globals.SimulationState;
import simulation.consumers.web.MasonModelWebSController;

public class EraseMe {

	public static void main(String[] args) {
		MasonModelWebSController controller = MasonModelWebSController.getInstance();
		if (controller.getState() != SimulationState.RUNNING){
			controller.run();
		}
	}
}
