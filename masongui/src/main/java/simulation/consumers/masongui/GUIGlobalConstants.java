package simulation.consumers.masongui;

import java.awt.Color;

public final class GUIGlobalConstants {
	private GUIGlobalConstants(){}
	
	// ----------------------------------------------------------------------
	// CONTROLLER
	public static final String USE_CONSOLE_UPDATER_FIELD = "useconsoleupd";
	public static final String USE_SMART_PARKING_UPDATER_FIELD = "usesmartparkingupd";
	
	// ----------------------------------------------------------------------
	// MASON GUI
	
	public static final String ROADS_SHAPEFILE_RESOURCE_PATH = "roadshp/Poly.shp";
	public static final int WINDOWS_WIDTH = 700;
	public static final int WINDOWS_HEIGHT = 600;
	public static final double SLOT_SYMBOL_SCALE = 3.0;
	public static final double CAR_SYMBOL_SCALE = 5.0;
	public static final Color ROADS_COLOR = Color.CYAN;

	public static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;
	public static final Color STUDENT_COLOR = Color.BLUE;
	public static final Color PAS_COLOR = Color.BLUE;
	public static final Color PDI_COLOR = Color.BLUE;
	public static final Color PARKED_AGENT_COLOR = Color.BLACK;
	public static final Color LEAVING_AGENT_COLOR = Color.MAGENTA;
	public static final Color TRICKED_AGENT_COLOR = Color.RED;
	
	public static final Color AVAILABLE_SLOT_COLOR = Color.GREEN;
	public static final Color OCCUPIED_SLOT_COLOR = Color.RED;
	public static final Color DISABLED_SLOT_COLOR = Color.BLACK;
	public static final Color REQUESTED_SLOT_COLOR = Color.YELLOW;
	
	// ----------------------------------------------------------------------
}
