package simulation.model.core;

import sim.engine.Schedule;

public class ExtendedSchedule extends Schedule{
	private static final long serialVersionUID = 4003826348168002226L;

	public double getMinTime(){
		Key key = (Key)(queue.getMinKey());
		return (key != null)? key.getTime():0;
	}
}
