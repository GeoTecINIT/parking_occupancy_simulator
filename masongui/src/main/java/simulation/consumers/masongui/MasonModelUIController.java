package simulation.consumers.masongui;

import simulation.consumers.common.ConsoleAgentChangeUpdater;
import simulation.consumers.common.WebServiceUpdater;
import simulation.model.wrapping.MasonModelController;
import simulation.model.wrapping.ModelRunner;
import simulation.data.configs.common.PropertiesLoader;
import static simulation.consumers.masongui.GUIGlobalConstants.*;

public class MasonModelUIController extends MasonModelController{
	private AgentsVisualController avupdater = null;
	private SlotsVisualController svupdater = null;

	private static volatile MasonModelUIController instance = null;
	
	private MasonModelUIController(){
		super();
		avupdater = new AgentsVisualController(WINDOWS_WIDTH, WINDOWS_HEIGHT);
		svupdater = new SlotsVisualController(WINDOWS_WIDTH, WINDOWS_HEIGHT);
	}
	
	public static MasonModelUIController getInstance() {
        if (instance == null) {
            synchronized (MasonModelUIController.class) {
                if (instance == null) {
                    instance = new MasonModelUIController();
                }
            }
        }
        return instance;
    }
	
	@Override
	protected void createRunner() {
		ParkingModelUI parkingModelUI = new ParkingModelUI(parkingModel, avupdater, svupdater);
		runner = new ModelRunner(parkingModelUI);
	}

	@Override
	protected void setupUpdaters() {
		runner.addAgentsUpdater(avupdater);
        runner.addSlotsUpdater(svupdater);
		
		PropertiesLoader pl = PropertiesLoader.getInstance();
		boolean useConsole = pl.getPropertyAsBoolean(USE_CONSOLE_UPDATER_FIELD);
		boolean useWebServ = pl.getPropertyAsBoolean(USE_SMART_PARKING_UPDATER_FIELD);

		if (useConsole){
			runner.addAgentsUpdater(new ConsoleAgentChangeUpdater());
		}
		if (useWebServ){
			runner.addSlotsUpdater(new WebServiceUpdater());
		}
		parkingModel.addGlobalUpdater(new SlotsInitiatior(svupdater));
	}
	
	@Override
	protected boolean isSlotInitNeeded() {
		return true;
	}

	@Override
	protected boolean isSetSlotsFreeOnInit() {
		return true;
	}
}
