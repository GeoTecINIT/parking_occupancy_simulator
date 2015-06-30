package simulation.data.configs.profiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import simulation.data.configs.common.MainPropertiesLoader;
import static simulation.common.globals.SimulGlobalConstants.AGENTS_DEFINITION_RESOURCE_NAME;

public class AgentsConfigsLoaderXML implements AgentsConfigsLoader {
	
	private static Configs unmarshal(InputStream in) throws JAXBException, IOException{
		JAXBContext jaxbContext = JAXBContext.newInstance(Configs.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Configs data = (Configs) jaxbUnmarshaller.unmarshal(in);
		// Close and return the slots
		in.close();
		return data;
	}
	
	private static void marshal(Configs data, OutputStream out) throws JAXBException, IOException{
		JAXBContext jaxbContext = JAXBContext.newInstance(Configs.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.marshal(data, out);
		// Close and return the slots
		out.close();
	}
	
	/* (non-Javadoc)
	 * @see parking.configs.xml.ConfigsLoader#readConfigs()
	 */
	@Override
	public Configs readConfigs() throws JAXBException, IOException {
		MainPropertiesLoader mainProps = MainPropertiesLoader.getInstance();
		InputStream in = mainProps.getInputStreamForFileInConfigFolder(AGENTS_DEFINITION_RESOURCE_NAME);
    	Configs configs = AgentsConfigsLoaderXML.unmarshal(in);
    	return configs;
	}
	
	/* (non-Javadoc)
	 * @see parking.configs.xml.ConfigsLoader#saveConfigs(parking.configs.xml.ConfigsXML)
	 */
	@Override
	public void saveConfigs(Configs configs) throws JAXBException, IOException, URISyntaxException {
		// TODO Cambiar a la nueva forma
		URI uri = AgentsConfigsLoaderXML.class.getClassLoader().getResource(AGENTS_DEFINITION_RESOURCE_NAME).toURI();
		FileOutputStream out = new FileOutputStream(new File(uri));
    	AgentsConfigsLoaderXML.marshal(configs, out);
	}
}