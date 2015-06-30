package simulation.common.globals;

public final class SimulGlobalConstants {
	private SimulGlobalConstants(){}
	
	// ----------------------------------------------------------------------
	// BASICS
	
	public static final String MAIN_PROPERTY_FILE_RESOURCE_PATH = "main.properties";
	public static final String IN_RESOURCES = "inresources";
	public static final String CONFIGS_FOLDER = "configfolder";
	public static final String PROPERTIES_FILE_RESOURCE_NAME = "app.properties";
	public static final String AGENTS_DEFINITION_RESOURCE_NAME = "agents.xml";
	public static final String LOG4J_FILE_RESOURCE_NAME = "log4j.properties";
	

	public static final String PROPERTIES_FILE_STEP_TIME = "defmoment";
	public static final String PROPERTIES_FILE_AGENT_BEHAVIOR = "guidedagentprop";
	public static final String PROPERTIES_FILE_REPETITIONS = "repetitions";
	public static final String PROPERTIES_FILE_USE_REAL_TIME = "userealtime";
	public static final double SIMULATION_CAR_SPEED = 8;	//  About 30 km/h
	
	// ----------------------------------------------------------------------
	// AGENTS PARAMETERS
	
	public static final String DISTANCE_EXPLORER_STATISTICS_KEY = "DISTANCE_EXPLORER";
	public static final String DISTANCE_GUIDED_STATISTICS_KEY = "DISTANCE_GUIDED";
	public static final String TRIKED_STATISTICS_KEY = "TRIKED";
	
	public static final double GUIDED_ALGORITHM_STARTING_DISTANCE = 20.0;
	public static final double GUIDED_ALGORITHM_FINAL_DISTANCE = 1020.0;
	public static final double GUIDED_ALGORITHM_STEP_DISTANCE = 100.0;
	
	// ----------------------------------------------------------------------
	// COMMUNICATION
	
	public static final String PROPERTIES_FILE_CAR_ROUTE_DIR = "carroutedir";
	public static final String PROPERTIES_FILE_PEDESTRIAN_DIR = "pedestrianroutedir";
	public static final String PROPERTIES_FILE_CONN_TIMEOUT = "timeoutinterval";
	
	public static final String PROPERTIES_FILE_SLOT_MANAGER = "target";
	public static final String PROPERTIES_FILE_PATH_CHANGE_STATUS = "pathchangestatus";
	public static final String PROPERTIES_FILE_PATH_GET_SLOT_DATA = "pathpetslotdata";
	public static final String PROPERTIES_FILE_PATH_CHOOSE_SLOT = "pathchooseslot";
	public static final String PROPERTIES_FILE_PATH_ASSIGN_SLOT = "pathassignslotentrance";
	public static final String PROPERTIES_FILE_SLOT_PARAM = "slotparam";
	public static final String PROPERTIES_FILE_PARKING_PARAM = "parkingparam";
	public static final String PROPERTIES_FILE_STATUS_PARAM = "statusparam";
	public static final String PROPERTIES_FILE_LONGITUDE_PARAM = "longitudeparam";
	public static final String PROPERTIES_FILE_LATITUDE_PARAM = "latitudeparam";
	public static final String PROPERTIES_FILE_DOOR_PARAM = "doorparam";
	
	public static final String USE_REMOTE_ASSIGNER = "useremotechooser";
	
	// ----------------------------------------------------------------------
	// MASON GUI
	
	public static final String EXPLORER_AGENT_DESCRIPTION = "EXPLORER";
	public static final String GUIDED_AGENT_DESCRIPTION = "GUIDED";
	public static final boolean PREFER_DESCRIPTION = true;
	
	// ----------------------------------------------------------------------
}
