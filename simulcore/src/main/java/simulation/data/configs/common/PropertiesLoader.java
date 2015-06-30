package simulation.data.configs.common;

import static simulation.common.globals.SimulGlobalConstants.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
	private final Properties configProp = new Properties();
	private static volatile PropertiesLoader instance = null;
	
	private PropertiesLoader(){
		//Private constructor to restrict new instances
		try {
			MainPropertiesLoader mainProps = MainPropertiesLoader.getInstance();
			InputStream in = mainProps.getInputStreamForFileInConfigFolder(PROPERTIES_FILE_RESOURCE_NAME);
			configProp.load(in);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PropertiesLoader getInstance() {
        if (instance == null) {
            synchronized (PropertiesLoader.class) {
                if (instance == null) {
                    instance = new PropertiesLoader();
                }
            }
        }
        return instance;
    }
	
	public String getProperty(String key){
		return configProp.getProperty(key);
	}
	
	public int getPropertyAsInt(String key){
		return Integer.parseInt(configProp.getProperty(key));
	}
	
	public boolean getPropertyAsBoolean(String key){
		return Boolean.parseBoolean(configProp.getProperty(key));
	}
	
	public double getPropertyAsDouble(String key){
		return Double.parseDouble(configProp.getProperty(key));
	}
	
	public boolean containsKey(String key){
		return configProp.containsKey(key);
	}
}
