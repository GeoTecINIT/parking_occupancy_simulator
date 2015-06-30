package simulation.consumers.console;

import java.io.IOException;

public class MasonTestConsole {

	public static void main(String[] args) throws IOException {
		MasonModelConsoleController controller = MasonModelConsoleController.getInstance();
		controller.run();
		System.in.read();
		System.exit(0);
	}
}
