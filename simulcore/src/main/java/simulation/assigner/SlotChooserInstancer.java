package simulation.assigner;

import static simulation.common.globals.SimulGlobalConstants.*;
import simulation.data.configs.common.PropertiesLoader;

public class SlotChooserInstancer {
	private static volatile SlotChooser instance = null;
	
	private SlotChooserInstancer(){}
	
	public static SlotChooser getSlotChooserInstance(){
		if (instance == null) {
            synchronized (SlotChooserInstancer.class) {
                if (instance == null) {
                	PropertiesLoader pl = PropertiesLoader.getInstance();
                	boolean remote = pl.getPropertyAsBoolean(USE_REMOTE_ASSIGNER);
                	if (remote == true){
                		instance = new SmartParkingSlotChooser();
                	}
                	else{
                		instance = new GeometricSlotChooser();
                	}
                }
            }
        }
        return instance;
	}
	
}
