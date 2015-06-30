package simulation.model.wrapping;

import java.util.Observable;

public class HandyObservable extends Observable {
	@Override
	public void notifyObservers(Object arg) {
		super.setChanged();
		super.notifyObservers(arg);
	}
}
