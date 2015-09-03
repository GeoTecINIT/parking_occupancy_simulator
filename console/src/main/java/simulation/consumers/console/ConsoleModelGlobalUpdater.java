package simulation.consumers.console;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;

import junit.framework.Assert;
import simulation.consumers.console.StatisticsConsoleUpdater.UpdaterValues;
import simulation.model.wrapping.ModelNotification;
import simulation.model.wrapping.ModelNotificationType;
import static simulation.consumers.console.ConsoleGlobalConstants.*;

public class ConsoleModelGlobalUpdater implements Observer{
	private StatisticsConsoleUpdater statisticsConsoleUpdater;
	private UpdaterValues cumulUpdaterValues = null;
	private PrintWriter guidedWriter = null;
	private PrintWriter explorerWriter = null;
	
	public ConsoleModelGlobalUpdater(StatisticsConsoleUpdater statisticsConsoleUpdater){
		this.statisticsConsoleUpdater = statisticsConsoleUpdater;
		this.cumulUpdaterValues = new UpdaterValues();
		try {
			this.guidedWriter = new PrintWriter(GUIDED_DISTANCES_FILE_NAME, "UTF-8");
			this.explorerWriter = new PrintWriter(EXPLORER_DISTANCES_FILE_NAME, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.err.println("Cannot write to resulting distances files.");
			this.guidedWriter = null;
			this.explorerWriter = null;
		}
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

			System.out.println("Tricked Amount\t\t\t\t" + values.getTrickedStats().getN());
			System.out.println("Guided Distance Mean\t\t" + String.format("%8.03f", values.getGuidedStats().getMean()) + "\t\tfor " + values.getGuidedStats().getN() + " agents");
			System.out.println("Explorer Distance Mean\t\t" + String.format("%8.03f", values.getExplorerStats().getMean()) + "\t\tfor " + values.getExplorerStats().getN() + " agents");
			System.out.println("--------------------------------------------");
			
			cumulUpdaterValues.addAll(values);
			statisticsConsoleUpdater.reset();			
			break;
			
		case FINISHED:
			int runsAmount = (int)((ModelNotification) arg).getValue();
			System.out.println("FINISHED AFTER " + runsAmount + " ITERATIONS");
			
			System.out.println("Avg Tricked Amount\t\t\t\t\t" + cumulUpdaterValues.getTrickedStats().getN()/runsAmount);
			System.out.println("Final Guided Distance Mean\t\t\t" + String.format("%8.03f", cumulUpdaterValues.getGuidedStats().getMean()));
			System.out.println("Final Explorer Distance Mean\t\t" + String.format("%8.03f", cumulUpdaterValues.getExplorerStats().getMean()));
			

			// Write all distance values to their files
			if ((guidedWriter != null) && (explorerWriter != null)){
				for(int index = 0; index < cumulUpdaterValues.getGuidedStats().getN(); index++){
					guidedWriter.println(cumulUpdaterValues.getGuidedStats().getElement(index));
				}
				for(int index = 0; index < cumulUpdaterValues.getExplorerStats().getN(); index++){
					explorerWriter.println(cumulUpdaterValues.getExplorerStats().getElement(index));
				}
				guidedWriter.close();
				explorerWriter.close();
			}
			
			System.exit(0); //TODO Look for a better solution
			break;
			
		default: //
			Assert.fail("ConsoleModelGlobalUpdater");
			break;
		}
	}
}
