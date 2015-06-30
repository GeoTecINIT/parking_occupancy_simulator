package simulation.model.wrapping;

import java.util.concurrent.Callable;

public interface CancelableRunnable extends Callable<Integer>{
	public void setCanceledFlag();
}
