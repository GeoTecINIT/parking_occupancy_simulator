package simulation.consumers.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import simulation.model.wrapping.*;
import junit.framework.Assert;

public class ConsoleModelGlobalUpdater implements Observer{
	private StatisticsConsoleUpdater statisticsConsoleUpdater;  
	
	private int trickedCount = 0;
	private int runsWithGuided = 0;
	private int runsWithExplorer = 0;
	
	private List<Double> guidedDistances;
	private List<Double> explorerDistances;
	
	public ConsoleModelGlobalUpdater(StatisticsConsoleUpdater statisticsConsoleUpdater){
		this.statisticsConsoleUpdater = statisticsConsoleUpdater;
		this.guidedDistances = new ArrayList<Double>();
		this.explorerDistances = new ArrayList<Double>();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		ModelNotificationType notification = ((ModelNotification) arg).getType();
		switch (notification) {
		case STEP:
			// Currently, do nothing
			break;
			
		case ITERATION:
			int iterNumber = (int)((ModelNotification) arg).getValue();
			System.out.println("ITERATION " + iterNumber);

			trickedCount += statisticsConsoleUpdater.getTrickedCount();
			System.out.println("Tricked Amount\t" + statisticsConsoleUpdater.getTrickedCount());
			
			if (statisticsConsoleUpdater.getGuidedDistanceAverage() != 0) {
				System.out.println("Guided Distance Mean\t" + statisticsConsoleUpdater.getGuidedDistanceAverage());
				System.out.println("Guided Distance Variance\t" + statisticsConsoleUpdater.getGuidedDistanceVariance());
				runsWithGuided++; 
			}
			else{
				System.out.println("No guided Agents In This Run");
			}
			if (statisticsConsoleUpdater.getExplorerDistanceAverage() != 0) {
				System.out.println("Explorer Distance Mean\t" + statisticsConsoleUpdater.getExplorerDistanceAverage());
				System.out.println("Explorer Distance Variance\t" + statisticsConsoleUpdater.getExplorerDistanceVariance());
				runsWithExplorer++;
			}
			else{
				System.out.println("No Explorer Agents In This Run");
			}
			
			guidedDistances.addAll(statisticsConsoleUpdater.getGuidedDistances());
			explorerDistances.addAll(statisticsConsoleUpdater.getExplorerDistances());
			statisticsConsoleUpdater.reset();			
			break;
			
		case FINISHED:
			int runsAmount = (int)((ModelNotification) arg).getValue();
			System.out.println("FINISHED AFTER ITERATIONS " + runsAmount);
			
			System.out.println("Avg Tricked Amount\t" + trickedCount/runsAmount);
			if (runsWithGuided != 0){
				System.out.println("Final Guided Distance Mean\t" + StatisticsConsoleUpdater.mean(guidedDistances));
				System.out.println("Final Guided Distance Variance\t" + StatisticsConsoleUpdater.variance(guidedDistances));
			}
			else{
				System.out.println("No Runs With Guided Agents");
			}
			if (runsWithExplorer != 0){
				System.out.println("Final Explorer Distance Mean\t" + StatisticsConsoleUpdater.mean(explorerDistances));
				System.out.println("Final Explorer Distance Variance\t" + StatisticsConsoleUpdater.variance(explorerDistances));
			}
			else{
				System.out.println("No Runs With Explorer Agents");
			}
			System.exit(0); //TODO Look for a better solution
			break;
			
		default: //
			Assert.fail("ConsoleModelGlobalUpdater");
			break;
		}
	}
}
