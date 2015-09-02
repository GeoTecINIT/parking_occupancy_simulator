package simulation.consumers.console;

import java.util.ArrayList;
import java.util.List;

import simulation.model.wrapping.StatisticNotification;
import simulation.model.wrapping.StatisticsChangeUpdater;
import static simulation.common.globals.SimulGlobalConstants.*;

public class StatisticsConsoleUpdater extends StatisticsChangeUpdater {
	
	// Static Utility Methods
	// -----------------------------------------------------
	/**
	 * Calculates the arithmetic mean for the given list of values.
	 */
	public static double mean(List<Double> values){
		if (values.size() == 0) return 0;
		double sum = 0;
		for (Double value : values) {
			sum += value;
		}
		return (sum / values.size());
	}
	
	public static double variance(List<Double> values){
		if (values.size() == 0) return 0;
		double meanValue = mean(values);
		double sum = 0;
		for (Double value : values) {
			sum += ((value - meanValue) * (value - meanValue));
		}
		if (values.size() == 1) return sum;
		return (sum / (values.size() - 1));
	}
	
	// -----------------------------------------------------
	
	private int trickedCount;
	private List<Double> guidedDistances;
	private List<Double> explorerDistances;
	
	public StatisticsConsoleUpdater() {
		super();
		guidedDistances = new ArrayList<Double>();
		explorerDistances = new ArrayList<Double>();
		reset();
	}
	
	@Override
	protected synchronized void doUpdate(StatisticNotification arg) {
		switch (arg.getKey()) {
		case DISTANCE_GUIDED_STATISTICS_KEY:
			guidedDistances.add(arg.getValue());
			break;
		case DISTANCE_EXPLORER_STATISTICS_KEY:
			explorerDistances.add(arg.getValue());
			break;
		case TRIKED_STATISTICS_KEY:
			trickedCount++;
			break;
		default:
			break;
		}
	}
	
	public synchronized List<Double> getGuidedDistances() {
		return new ArrayList<Double>(guidedDistances);
	}

	public synchronized List<Double> getExplorerDistances() {
		return new ArrayList<Double>(explorerDistances);
	}

	public synchronized int getTrickedCount(){
		return trickedCount;
	}
	
	public synchronized double getGuidedDistanceAverage(){
		return mean(guidedDistances);
	}
	
	public synchronized double getExplorerDistanceAverage(){
		return mean(explorerDistances);
	}
	
	public synchronized double getGuidedDistanceVariance(){
		return variance(guidedDistances);
	}
	
	public synchronized double getExplorerDistanceVariance(){
		return variance(explorerDistances);
	}
	
	/**
	 * Clear all values stored for statistics keeping.
	 */
	public synchronized void reset(){
		trickedCount = 0;
		guidedDistances.clear();
		explorerDistances.clear();
	}
}
