package simulation.common.globals;

public enum SimulationState {
	NOT_STARTED(1),
	RUNNING(2),
	CANCELED(3),
	FINISHED(4),
	ERROR(5);
	
	private int value;
	
	private SimulationState(int value) {
	   this.value = value;
	}
	
	public int getValue() {
	   return value;
	}
}