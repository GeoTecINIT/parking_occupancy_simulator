package simulation.model.wrapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ModelRunner {
	private ModelEngine engine = null;
	private List<CancelableRunnable> updaters = null;
	
	private ExecutorService pool = null;
	private Future<Integer> engineFuture = null;
	private List<Future<Integer>> updatersFutures = null;
	
	public ModelRunner(ModelEngine engine){
		this.engine = engine;
		updaters = new ArrayList<CancelableRunnable>();
		updatersFutures = new ArrayList<Future<Integer>>();
	}
	
	public void addSlotsUpdater(SlotCurrentStateUpdater updater){
		engine.addSlotChangeUpdater(updater);
		updaters.add(updater);
	}
	
	public void addAgentsUpdater(AgentsChangeUpdater updater){
		engine.addAgentChangeUpdater(updater);
		updaters.add(updater);
	}
	
	public void addStatisticsUpdater(StatisticsChangeUpdater updater){
		engine.addStaticticsChangeUpdater(updater);
		updaters.add(updater);
	}
	
	public void run(){
		pool = Executors.newFixedThreadPool(updaters.size() + 1);
		for (CancelableRunnable updater : updaters) {
			updatersFutures.add(pool.submit(updater));
		}
		engineFuture = pool.submit(engine);
	}
	
	public void cancel(){
		engineFuture.cancel(true);
		for (Future<Integer> future : updatersFutures) {
			future.cancel(true);
		}
		for (CancelableRunnable updater : updaters) {
			updater.setCanceledFlag();
		}
		engine.setCanceledFlag();
	}
}
