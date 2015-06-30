package simulation.profiles.generator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

public class XMLPersistenceManager<T> {
	private Marshaller m;
	private Unmarshaller um;
	private String fileName;
	
	public XMLPersistenceManager(Class<T> classType, String fileName) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(classType);
		m = context.createMarshaller();
		um = context.createUnmarshaller();
		this.fileName = fileName;
	}
	
	public void setPretty() throws PropertyException{
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	}
	
	public T read() throws JAXBException, IOException{
		FileInputStream fr = new FileInputStream(fileName);
     	@SuppressWarnings("unchecked")
		T result = (T)um.unmarshal(fr);
     	fr.close();
     	return result;
	}
	
	public void write(T object) throws JAXBException, IOException{
		FileOutputStream fw = new FileOutputStream(fileName, false);
		m.marshal(object, fw);
		fw.close();
	}
}
