package simulation.consumers.console;

import java.util.Observable;
import java.util.Observer;

import junit.framework.Assert;
import simulation.consumers.console.StatisticsConsoleUpdater.UpdaterValues;
import simulation.model.wrapping.ModelNotification;
import simulation.model.wrapping.ModelNotificationType;

public class ConsoleModelGlobalUpdater implements Observer{
	private StatisticsConsoleUpdater statisticsConsoleUpdater;
	private UpdaterValues cumulUpdaterValues = null;
	
	public ConsoleModelGlobalUpdater(StatisticsConsoleUpdater statisticsConsoleUpdater){
		this.statisticsConsoleUpdater = statisticsConsoleUpdater;
		this.cumulUpdaterValues = new UpdaterValues();
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
			
			UpdaterValues values = statisticsConsoleUpdater.getUpdaterValues();

			System.out.println("Tricked Amount\t\t" + values.getTrickedStats().getN());
			System.out.println("Guided Distance Mean\t\t" + values.getGuidedStats().getMean());
			System.out.println("Explorer Distance Mean\t\t" + values.getExplorerStats().getMean());
			
			cumulUpdaterValues.addAll(values);
			statisticsConsoleUpdater.reset();			
			break;
			
		case FINISHED:
			int runsAmount = (int)((ModelNotification) arg).getValue();
			System.out.println("FINISHED AFTER " + runsAmount + " ITERATIONS");
			
			System.out.println("Avg Tricked Amount\t\t" + cumulUpdaterValues.getTrickedStats().getN()/runsAmount);
			System.out.println("Final Guided Distance Mean\t\t" + cumulUpdaterValues.getGuidedStats().getMean());
			System.out.println("Final Explorer Distance Mean\t\t" + cumulUpdaterValues.getExplorerStats().getMean());
			
			System.exit(0); //TODO Look for a better solution
			break;
			
		default: //
			Assert.fail("ConsoleModelGlobalUpdater");
			break;
		}
	}
}
