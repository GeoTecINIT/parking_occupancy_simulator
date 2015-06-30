package simulation.data.gisdata.routes;


import static simulation.common.globals.SimulGlobalConstants.PROPERTIES_FILE_CONN_TIMEOUT;
import static simulation.common.globals.SimulGlobalConstants.PROPERTIES_FILE_PEDESTRIAN_DIR;
import simulation.data.configs.common.PropertiesLoader;

public final class PedestrianRouteCalulator extends RouteCalulator{
	private static volatile PedestrianRouteCalulator instance = null;
	
	private PedestrianRouteCalulator() {
    	super();
    }
 
    public static PedestrianRouteCalulator getInstance() {
        if (instance == null) {
            synchronized (PedestrianRouteCalulator.class) {
                if (instance == null) {
                    instance = new PedestrianRouteCalulator();
                }
            }
        }
        return instance;
    }
    
    @Override
    protected void readParams(){
    	PropertiesLoader pl = PropertiesLoader.getInstance();
    	target = pl.getProperty(PROPERTIES_FILE_PEDESTRIAN_DIR);
    	timeOutInterval = pl.getPropertyAsInt(PROPERTIES_FILE_CONN_TIMEOUT);
    	pathRoute = "solve?";
    }
}
