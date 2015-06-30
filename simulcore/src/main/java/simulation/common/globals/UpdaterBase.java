package simulation.common.globals;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import simulation.model.wrapping.CancelableRunnable;

public abstract class UpdaterBase<T> implements Observer, CancelableRunnable{
	final static Logger logger = Logger.getLogger(UpdaterBase.class);
	private BlockingQueue<T> queue = new LinkedBlockingQueue<T>();
	private boolean cancelledFlag = false;
	
	@Override
	public synchronized void update(Observable o, Object arg){
		try{
			@SuppressWarnings("unchecked")
			T value = (T)arg;
			queue.put(value);
		}
		catch(Exception e){
			logger.error("Problems!!! Exception: " + e.getMessage());
		}
	}

	@Override
	public Integer call() throws Exception {
		while(!cancelledFlag){
			doSynchUpdate(queue.take());
		}
		return 0;
	}
	
	private synchronized void doSynchUpdate(T arg){
		doUpdate(arg);
	}
	
	protected abstract void doUpdate(T arg);
	
	@Override
	public void setCanceledFlag(){
		cancelledFlag = true;
	}
}
