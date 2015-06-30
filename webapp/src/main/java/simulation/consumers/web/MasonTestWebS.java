package simulation.consumers.web;

import java.io.IOException;

import simulation.common.globals.SimulationState;

public class MasonTestWebS {

	public static void main(String[] args) throws IOException {
		MasonModelWebSController controller = MasonModelWebSController.getInstance();
		if (controller.getState() != SimulationState.RUNNING){
			controller.run();
		}
		System.in.read();
	}

}
