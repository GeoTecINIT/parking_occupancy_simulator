package simulation.data.gisdata.routes;

import static simulation.common.globals.SimulGlobalConstants.PROPERTIES_FILE_CAR_ROUTE_DIR;
import static simulation.common.globals.SimulGlobalConstants.PROPERTIES_FILE_CONN_TIMEOUT;
import simulation.data.configs.common.PropertiesLoader;

public final class CarRouteCalulator extends RouteCalulator{
	private static volatile CarRouteCalulator instance = null;
	
	private CarRouteCalulator() {
    	super();
    }
 
    public static CarRouteCalulator getInstance() {
        if (instance == null) {
            synchronized (CarRouteCalulator.class) {
                if (instance == null) {
                    instance = new CarRouteCalulator();
                }
            }
        }
        return instance;
    }
    
    @Override
    protected void readParams(){
    	PropertiesLoader pl = PropertiesLoader.getInstance();
    	target = pl.getProperty(PROPERTIES_FILE_CAR_ROUTE_DIR);
    	timeOutInterval = pl.getPropertyAsInt(PROPERTIES_FILE_CONN_TIMEOUT);
    	pathRoute = "solve?";
    }
}
