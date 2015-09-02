package simulation.consumers.console;

import static simulation.common.globals.SimulGlobalConstants.DISTANCE_EXPLORER_STATISTICS_KEY;
import static simulation.common.globals.SimulGlobalConstants.DISTANCE_GUIDED_STATISTICS_KEY;
import static simulation.common.globals.SimulGlobalConstants.TRIKED_STATISTICS_KEY;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import simulation.model.wrapping.StatisticNotification;
import simulation.model.wrapping.StatisticsChangeUpdater;

public class StatisticsConsoleUpdater extends StatisticsChangeUpdater {
	
	public static class UpdaterValues{
		private DescriptiveStatistics trickedStats = null;
		private DescriptiveStatistics guidedStats = null;
		private DescriptiveStatistics explorerStats = null;
		
		public UpdaterValues(){
			trickedStats = new DescriptiveStatistics();
			guidedStats = new DescriptiveStatistics();
			explorerStats = new DescriptiveStatistics();
		}
		
		public UpdaterValues(UpdaterValues source){
			trickedStats = new DescriptiveStatistics(source.getTrickedStats());
			guidedStats = new DescriptiveStatistics(source.getGuidedStats());
			explorerStats = new DescriptiveStatistics(source.getExplorerStats());
		}
				
		public DescriptiveStatistics getTrickedStats() {
			return trickedStats;
		}
		
		public DescriptiveStatistics getGuidedStats() {
			return guidedStats;
		}
		
		public DescriptiveStatistics getExplorerStats() {
			return explorerStats;
		}
		
		public void addAll(UpdaterValues values){
			for (int index = 0; index < values.trickedStats.getN(); index++) {
				trickedStats.addValue(values.trickedStats.getElement(index));
			}
			for (int index = 0; index < values.guidedStats.getN(); index++) {
				guidedStats.addValue(values.guidedStats.getElement(index));
			}
			for (int index = 0; index < values.explorerStats.getN(); index++) {
				explorerStats.addValue(values.explorerStats.getElement(index));
			}
		}
		
		public void reset(){
			trickedStats.clear();
			guidedStats.clear();
			explorerStats.clear();
		}
	}
	
	private UpdaterValues values;
	
	public StatisticsConsoleUpdater() {
		super();
		values = new UpdaterValues();
		reset();
	}
	
	@Override
	protected synchronized void doUpdate(StatisticNotification arg) {
		switch (arg.getKey()) {
		case DISTANCE_GUIDED_STATISTICS_KEY:
			values.getGuidedStats().addValue(arg.getValue());
			break;
		case DISTANCE_EXPLORER_STATISTICS_KEY:
			values.getExplorerStats().addValue(arg.getValue());
			break;
		case TRIKED_STATISTICS_KEY:
			values.getTrickedStats().addValue(1.0);
			break;
		default:
			break;
		}
	}
	
	public synchronized UpdaterValues getUpdaterValues() {
		return new UpdaterValues(values);
	}
	
	/**
	 * Clear all values stored for statistics keeping.
	 */
	public synchronized void reset(){
		values.reset();
	}
}
