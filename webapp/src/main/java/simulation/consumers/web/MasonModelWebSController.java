package simulation.consumers.web;

import static simulation.consumers.web.WebGlobalConstants.*;

import simulation.consumers.common.ConsoleAgentChangeUpdater;
import simulation.consumers.common.WebServiceUpdater;
import simulation.data.configs.common.PropertiesLoader;
import simulation.model.wrapping.MasonModelController;

public class MasonModelWebSController extends MasonModelController{
	private MapUpdater mapUpdater = null;
	private CO2Updater cO2Updater = null;
	
	private boolean reloadOnRun = false;

	private static volatile MasonModelWebSController instance = null;
	
	private MasonModelWebSController(){
		super();
		
		PropertiesLoader pl = PropertiesLoader.getInstance();
		reloadOnRun = pl.getPropertyAsBoolean(PROPERTIES_FILE_RELOAD_ON_RUN);
	}
	
	public static MasonModelWebSController getInstance() {
        if (instance == null) {
            synchronized (MasonModelWebSController.class) {
                if (instance == null) {
                    instance = new MasonModelWebSController();
                }
            }
        }
        return instance;
    }
	
	public SlotListDetails <SlotClientData> getMapData(){
		return mapUpdater.getSlotsData();
	}
	
	public SlotListDetails<SlotClientState> getMapUpdates(long requestedTime){
		return mapUpdater.getUpdates(requestedTime);
	}
	
	public ActivityIndicators getCO2Indicators() {
		return cO2Updater.getCO2Indicators();
	}

	@Override
	protected void setupUpdaters() {

		mapUpdater = new MapUpdater(slots);
		runner.addSlotsUpdater(mapUpdater);
		
		PropertiesLoader pl = PropertiesLoader.getInstance();
		boolean useConsole = pl.getPropertyAsBoolean(USE_CONSOLE_UPDATER_FIELD);
		boolean useWebServ = pl.getPropertyAsBoolean(USE_SMART_PARKING_UPDATER_FIELD);

		if (useConsole){
			runner.addAgentsUpdater(new ConsoleAgentChangeUpdater());
		}
		if (useWebServ){
			runner.addSlotsUpdater(new WebServiceUpdater());
		}
		
		cO2Updater = CO2Updater.getInstance();
		parkingModel.addGlobalUpdater(cO2Updater.getGlobalCO2Updater());
		runner.addAgentsUpdater(cO2Updater.getAgentCO2Updater());
	}
	
	@Override
	protected void onRun() {
		if (reloadOnRun){
			requestDataUpdate(false);
		}
	}
	
	@Override
	protected boolean isSlotInitNeeded() {
		return false;	
	}

	@Override
	protected boolean isSetSlotsFreeOnInit() {
		return false;
	}
}
