package simulation.consumers.masongui;

import java.io.IOException;

public class MasonTestUI {

	public static void main(String[] args) throws IOException {
		MasonModelUIController controller = MasonModelUIController.getInstance();
		controller.run();
		System.in.read();
	}
}
