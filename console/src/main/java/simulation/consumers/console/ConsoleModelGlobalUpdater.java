package simulation.consumers.console;

import java.util.Observable;
import java.util.Observer;

import simulation.model.wrapping.*;
import junit.framework.Assert;

public class ConsoleModelGlobalUpdater implements Observer{
	private StatisticsConsoleUpdater statisticsConsoleUpdater;  
	
	private int trickedCount = 0;
	private double sumAvgDistanceGuided = 0;
	private double sumAvgDistanceExplorer = 0;
	private int runsWithGuided = 0;
	private int runsWithExplorer = 0;
	
	public ConsoleModelGlobalUpdater(StatisticsConsoleUpdater statisticsConsoleUpdater){
		this.statisticsConsoleUpdater = statisticsConsoleUpdater;
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
			sumAvgDistanceGuided += statisticsConsoleUpdater.getGuidedAverageDistance();
			sumAvgDistanceExplorer += statisticsConsoleUpdater.getExplorerAverageDistance();
			
			System.out.println("Tricked Amount\t" + statisticsConsoleUpdater.getTrickedCount());
			
			if (statisticsConsoleUpdater.getGuidedAverageDistance() != 0) {
				System.out.println("Guided Mean Distance\t" + statisticsConsoleUpdater.getGuidedAverageDistance());
				runsWithGuided++; 
			}
			else{
				System.out.println("No guided Agents In This Run");
			}
			if (statisticsConsoleUpdater.getExplorerAverageDistance() != 0) {
				System.out.println("Explorer Mean Distance\t" + statisticsConsoleUpdater.getExplorerAverageDistance());
				runsWithExplorer++;
			}
			else{
				System.out.println("No Explorer Agents In This Run");
			}
			statisticsConsoleUpdater.reset();			
			break;
			
		case FINISHED:
			int runsAmount = (int)((ModelNotification) arg).getValue();
			System.out.println("FINISHED AFTER ITERATIONS " + runsAmount);
			
			System.out.println("Avg Tricked Amount\t" + trickedCount/runsAmount);
			if (runsWithGuided != 0){
				System.out.println("Avg Guided Mean Distance\t" + sumAvgDistanceGuided/runsWithGuided);
			}
			else{
				System.out.println("No Runs With Guided Agents");
			}
			if (runsWithExplorer != 0){
				System.out.println("Avg Explorer Mean Distance\t" + sumAvgDistanceExplorer/runsWithExplorer);
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
