package simulation.propfilemodifier;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropFileGenerator {
	
	public static final String PROPERTIES_FILE_RESOURCE_NAME = "app.properties";
	public static final String PROPERTIES_FILE_REPETITIONS = "repetitions";
	public static final String PROPERTIES_FILE_AGENT_BEHAVIOR = "guidedagentprop";
	
    public static void main( String[] args ){
    	try{
	    	String destination = PROPERTIES_FILE_RESOURCE_NAME;
	    	int repetitions = 1;
	    	double agentProportion = 0.5;
	    	
	    	if (args.length > 0){
	    		if (args[0].equals("?")){
	    			System.out.println("Expected params:\trepetitions\tagentProportion\tdestination");
	    			System.out.println("Defult values:\t" + repetitions + "\t" + agentProportion + "\t" + destination);
	    			return;
	    		}
	    	}
	    	
	    	if (args.length > 1) {
	    		repetitions = Integer.parseInt(args[0]);
	    		agentProportion = Double.parseDouble(args[1]);
	    	}
	    	if (args.length > 2) {
	    		destination = args[2];
	    	}
	    	
	    	// Create a copy of the properties file
	    	InputStream in = PropFileGenerator.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_RESOURCE_NAME);
	    	OutputStream out = new FileOutputStream(destination);
	    	byte[] buffer = new byte[1024];
    	    int bytes;
    	    while ((bytes = in.read(buffer)) > 0){
    	    	out.write(buffer, 0, bytes);
    	    }
    	    in.close();
    	    out.close();
    	    // ------------------------- Copy created!!!

    	    in = new FileInputStream(destination);
	    	
    	    Properties configProp = new Properties();
    		configProp.load(in);
    		in.close();
    		
    		configProp.setProperty(PROPERTIES_FILE_REPETITIONS, Integer.toString(repetitions));
    		configProp.setProperty(PROPERTIES_FILE_AGENT_BEHAVIOR, Double.toString(agentProportion));

	    	out = new FileOutputStream(destination);
    		configProp.store(out, null);
    	    out.close();
    	    
	        System.out.println("Done!");
    	}
    	catch(Exception e){
    		System.out.println("Problems!!!!");
    		e.printStackTrace();
    	}
    }
}
