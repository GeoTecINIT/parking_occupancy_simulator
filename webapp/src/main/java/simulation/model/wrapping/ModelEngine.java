package simulation.model.wrapping;

import java.util.Observer;

public interface ModelEngine extends CancelableRunnable{

	public abstract void addSlotChangeUpdater(Observer updater);

	public abstract void addAgentChangeUpdater(Observer updater);

	public abstract void addStaticticsChangeUpdater(Observer updater);

	public abstract void notifySlotChange(Object arg);

	public abstract void notifyAgentChange(Object arg);

	public abstract void notifyStaticticsChange(Object arg);

}