package smartparking.assigner.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.bind.JAXBException;

import simulation.common.globals.BuildingEntrance;
import simulation.common.globals.EntranceWithOrderedSlots;
import smartparking.assigner.generation.screquests.SmartCampusEntrances;

public class DBDataInserter {
	private Map<Integer, BuildingEntrance> mapEntrances;
	private DBUpdater dBUpdater;
	
	public DBDataInserter(List<BuildingEntrance> entries){
		mapEntrances = new HashMap<>();
		for (BuildingEntrance buildingEntrance : entries) {
			mapEntrances.put(buildingEntrance.getDoorId(), buildingEntrance);
		}
		
		dBUpdater = DBUpdater.getInstance();
     	ExecutorService pool = Executors.newFixedThreadPool(1);
     	pool.submit(dBUpdater);
	}
	
	public void insertInDBFromFile(XMLPersistenceManager<XMLEntranceSlotsDistancesCollection> xMLPersistenceManager) throws JAXBException, IOException{
     	XMLEntranceSlotsDistancesCollection entranceSlotsDistancesCollection = xMLPersistenceManager.read();
     	insertInDB(entranceSlotsDistancesCollection);
	}
	
	public void insertInDB(XMLEntranceSlotsDistancesCollection entranceSlotsDistancesCollection){
		for (XMLEntranceSlotsDistances xMLEntranceSlotsDistances: entranceSlotsDistancesCollection.getEntranceSlotsDistances()) {
			insertInDB(xMLEntranceSlotsDistances);
		}
	}
	
	public void insertInDB(XMLEntranceSlotsDistances xMLEntranceSlotsDistances){
		BuildingEntrance be = mapEntrances.get(xMLEntranceSlotsDistances.getBuildingEntrance());
 		List<Integer> distancesList = new ArrayList<>();
 		// Ensuring order by distance
 		Collections.sort(xMLEntranceSlotsDistances.getEntryDistances());
 		for (XMLEntryDistance xMLEntryDistance : xMLEntranceSlotsDistances.getEntryDistances()) {
 			distancesList.add(xMLEntryDistance.getSlotData());
		}
 		insertInDB(new EntranceWithOrderedSlots(be, distancesList));
	}
	
	public void insertInDB(EntranceWithOrderedSlots entranceSlotsDistances){
		dBUpdater.update(null, entranceSlotsDistances);
	}
	
	public void closeAndWait() throws InterruptedException{
		dBUpdater.update(null, EntranceWithOrderedSlots.NULL);
     	while(true){
     		if (dBUpdater.isFinished()){
     			break;
     		}
     		System.out.println("waiting");
     		Thread.sleep(50);
     	}
	}
	
	public static void main( String[] args ) throws Exception{
		DBDataInserter dataInserter = new DBDataInserter(SmartCampusEntrances.getInstance().getBuildingEntrances());
		XMLPersistenceManager<XMLEntranceSlotsDistancesCollection> xMLPersistenceManager =
				new XMLPersistenceManager<>(XMLEntranceSlotsDistancesCollection.class, "C:\\save\\distances - copia.xml");
		dataInserter.insertInDBFromFile(xMLPersistenceManager);
     	dataInserter.closeAndWait();
     	
     	System.out.println("Gooooog bye");
     	System.exit(0);
	 }
}
