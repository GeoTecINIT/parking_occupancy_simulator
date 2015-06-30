package simulation.data.configs.common;

import static simulation.common.globals.SimulGlobalConstants.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MainPropertiesLoader {
	private final Properties mainConfigProp = new Properties();
	private static volatile MainPropertiesLoader instance = null;
	
	private MainPropertiesLoader(){
		//Private constructor to restrict new instances
		try {
			InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(MAIN_PROPERTY_FILE_RESOURCE_PATH);
			mainConfigProp.load(inStream);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MainPropertiesLoader getInstance() {
        if (instance == null) {
            synchronized (MainPropertiesLoader.class) {
                if (instance == null) {
                    instance = new MainPropertiesLoader();
                }
            }
        }
        return instance;
    }
	
	public String getProperty(String key){
		return mainConfigProp.getProperty(key);
	}
	
	public InputStream getInputStreamForFileInConfigFolder(String fileName){
		try{
			String string = getProperty(IN_RESOURCES);
			boolean inResources = Boolean.parseBoolean(string);
			if (inResources){
				InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
				return in;
			}
			else{
				String configsFolder = getProperty(CONFIGS_FOLDER);
		
				String directoryPath = new File(PropertiesLoader.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParent();
				String finalPath = directoryPath + ((configsFolder == null) || (configsFolder.isEmpty())?"":("/" + configsFolder))  + "/" + fileName;
				InputStream in = new FileInputStream(finalPath);
				return in;
			}
		}
		catch(Exception e){
			// TODO Mejorar esto
			return null;
		}
	}
	
}
