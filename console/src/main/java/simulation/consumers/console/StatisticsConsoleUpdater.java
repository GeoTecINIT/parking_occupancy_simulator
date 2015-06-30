package simulation.consumers.console;

import simulation.model.wrapping.StatisticNotification;
import simulation.model.wrapping.StatisticsChangeUpdater;
import static simulation.common.globals.SimulGlobalConstants.*;

public class StatisticsConsoleUpdater extends StatisticsChangeUpdater {
	private int trickedCount;
	private int guidedCount;
	private double sumDistanceGuided;
	private int explorerCount;
	private double sumDistanceExplorer;
	
	public StatisticsConsoleUpdater() {
		super();
		reset();
	}

	@Override
	protected synchronized void doUpdate(StatisticNotification arg) {
		switch (arg.getKey()) {
		case DISTANCE_EXPLORER_STATISTICS_KEY:
			explorerCount++;
			sumDistanceExplorer += arg.getValue();
			break;
		case DISTANCE_GUIDED_STATISTICS_KEY:
			guidedCount++;
			sumDistanceGuided += arg.getValue();
			break;
		case TRIKED_STATISTICS_KEY:
			trickedCount++;
			break;
		default:
			break;
		}
	}
	
	public int getTrickedCount(){
		return trickedCount;
	}
	
	public synchronized double getGuidedAverageDistance(){
		return (guidedCount != 0 ) ? sumDistanceGuided / guidedCount : 0;
	}
	
	public synchronized double getExplorerAverageDistance(){
		return (explorerCount != 0) ? sumDistanceExplorer / explorerCount : 0;
	}
	
	public synchronized void reset(){
		trickedCount = 0;
		guidedCount = 0;
		sumDistanceGuided = 0;
		explorerCount = 0;
		sumDistanceExplorer = 0;
	}
}
