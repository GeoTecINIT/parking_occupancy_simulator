package simulation.common.smartparkingws;

import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;

import simulation.common.globals.HistoryLogger;
import simulation.common.globals.HistoryLoggerEntry;
import simulation.common.globals.SlotCurrentState;
import simulation.common.globals.SlotData;
import simulation.data.configs.common.PropertiesLoader;

import static simulation.common.globals.SimulGlobalConstants.*;

public class SmartParkingWServices {
	private final static Logger logger = Logger.getLogger(SmartParkingWServices.class);
	
	private String target;
	private String pathChangeStatus;
	private String pathGetSlotData;
	private String pathChooseSlot;
	private String slotParam;
	private String parkingParam;
	private String statusParam;
	private String longitudeParam;
	private String latitudeParam;
	private int timeOutInterval;
	
	SmartParkingSlotDataGetter slotDataGetter;
	SmartParkingSlotStatusChanger slotStatusChanger;
	SmartParkingSlotChooser slotChooser;
	
	private static volatile SmartParkingWServices instance = null;
	
    private SmartParkingWServices() {
    	readParams();
    	slotDataGetter = new SmartParkingSlotDataGetter(target, pathGetSlotData, timeOutInterval);
    	slotStatusChanger = new SmartParkingSlotStatusChanger(target, pathChangeStatus, timeOutInterval, slotParam, parkingParam, statusParam);
    	slotChooser = new SmartParkingSlotChooser(target, pathChooseSlot, timeOutInterval, longitudeParam, latitudeParam);
    }
 
    public static SmartParkingWServices getInstance() {
        if (instance == null) {
            synchronized (SmartParkingWServices.class) {
                if (instance == null) {
                    instance = new SmartParkingWServices();
                }
            }
        }
        return instance;
    }
    
    private void readParams(){
    	PropertiesLoader pl = PropertiesLoader.getInstance();
    	target = pl.getProperty(PROPERTIES_FILE_SLOT_MANAGER);
    	pathChangeStatus = pl.getProperty(PROPERTIES_FILE_PATH_CHANGE_STATUS);
    	pathGetSlotData = pl.getProperty(PROPERTIES_FILE_PATH_GET_SLOT_DATA);
    	pathChooseSlot = pl.getProperty(PROPERTIES_FILE_PATH_CHOOSE_SLOT);
    	slotParam = pl.getProperty(PROPERTIES_FILE_SLOT_PARAM);
    	parkingParam = pl.getProperty(PROPERTIES_FILE_PARKING_PARAM);
    	statusParam = pl.getProperty(PROPERTIES_FILE_STATUS_PARAM);
    	longitudeParam = pl.getProperty(PROPERTIES_FILE_LONGITUDE_PARAM);
    	latitudeParam = pl.getProperty(PROPERTIES_FILE_LATITUDE_PARAM);
    	timeOutInterval = pl.getPropertyAsInt(PROPERTIES_FILE_CONN_TIMEOUT);
    }
	
	public List<SlotData> getSlotsData() throws Exception{
		List<SlotData> data = slotDataGetter.getSlotsData();
		return data;
	}
	
	public boolean changeSlotStatus(SlotCurrentState slotStatus){
		try{
			ResponseChangeStatus resp = slotStatusChanger.changeSlotStatus(slotStatus);
			if ((resp.responses.size() == 0) || (resp.responses.get(0).equals("0"))){
				logger.error("Problems sending information!!! Error returned");
				logger.debug("Problems" + slotStatus.getSlot().getParking() + ", " + slotStatus.getSlot().getSlot() + resp.responses.get(0));
				return false;
			}
			String text = "PA:" + slotStatus.getSlot().getParking() + ", SL:" + slotStatus.getSlot().getSlot() + ", ST:" + slotStatus.getStatus() + ", RE:" + resp.responses.get(0); 
			logger.debug(text);
			HistoryLogger.getInstance().add(new HistoryLoggerEntry(text, (new Date()).getTime()));
			return true;	
		}
		catch(Exception e){
			logger.error("Problems sending information!!! Exception: " + e.getMessage());
			logger.debug("Problems" + slotStatus.getSlot().getParking() + ", " + slotStatus.getSlot().getSlot());
			return false;
		}
	}
	
	public SlotData chooseSlot(double longitude, double latitude){
		try{
			ResponseChooseSlot resp = slotChooser.chooseSlot(longitude, latitude);
			if (resp == null){
				logger.error("Problems sending information!!! Error returned");
			}
			return resp.getResponse();	
		}
		catch(Exception e){
			logger.error("Problems sending information!!! Exception: " + e.getMessage());
			return null;
		}
	}
}
