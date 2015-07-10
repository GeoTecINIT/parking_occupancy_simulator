package simulation.consumers.console;

import simulation.consumers.common.ConsoleAgentChangeUpdater;
import simulation.consumers.common.WebServiceUpdater;
import simulation.data.configs.common.PropertiesLoader;
import simulation.model.wrapping.MasonModelController;
import simulation.model.wrapping.ModelRunner;

import static simulation.consumers.console.ConsoleGlobalConstants.*;

public class MasonModelConsoleController extends MasonModelController{
	private StatisticsConsoleUpdater supdater = null;
	private ConsoleModelGlobalUpdater cgupdater = null;
	private static volatile MasonModelConsoleController instance = null;
	
	private MasonModelConsoleController(){
		super();
		supdater = new StatisticsConsoleUpdater();
		cgupdater = new ConsoleModelGlobalUpdater(supdater);
	}
	
	public static MasonModelConsoleController getInstance() {
        if (instance == null) {
            synchronized (MasonModelConsoleController.class) {
                if (instance == null) {
                    instance = new MasonModelConsoleController();
                }
            }
        }
        return instance;
    }
	
	@Override
	protected void createRunner() {
		runner = new ModelRunner(parkingModel);
	}
	
	@Override
	protected void setupUpdaters(){
        runner.addStatisticsUpdater(supdater);
		parkingModel.addGlobalUpdater(cgupdater);
		
		PropertiesLoader pl = PropertiesLoader.getInstance();
		boolean useConsole = pl.getPropertyAsBoolean(USE_CONSOLE_UPDATER_FIELD);
		boolean useWebServ = pl.getPropertyAsBoolean(USE_SMART_PARKING_UPDATER_FIELD);

		if (useConsole){
			runner.addAgentsUpdater(new ConsoleAgentChangeUpdater());
		}
		if (useWebServ){
			runner.addSlotsUpdater(new WebServiceUpdater());
		}
	}
	
	@Override
	protected boolean isSlotInitNeeded() {
		return false;
	}

	@Override
	protected boolean isSetSlotsFreeOnInit() {
		return true; // TODO: Cambiar a true 
	}
}
