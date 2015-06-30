package smartparking.assigner.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import simulation.common.globals.BuildingEntrance;
import simulation.common.globals.SlotData;
import simulation.data.configs.common.SlotsDataLoader;
import simulation.data.gisdata.routes.PedestrianRouteCalulator;
import simulation.data.gisdata.routes.RouteCalulator;
import simulation.model.support.SimulRoute;
import smartparking.assigner.generation.screquests.SmartCampusEntrances;

import com.esri.core.geometry.Point;


public class DataGenerator
{
	private List<BuildingEntrance> entries;
	private List<SlotData> slotsData;
	private RouteCalulator routeCalulator;
	
    public DataGenerator(List<BuildingEntrance> entries,
			List<SlotData> slotsData, RouteCalulator routeCalulator) {
		super();
		this.entries = entries;
		this.slotsData = slotsData;
		this.routeCalulator = routeCalulator;
	}
    
    public void calculate(XMLPersistenceManager<XMLEntranceSlotsDistancesCollection> xMLPersistenceManager,
    		DBDataInserter dataInserter, int savingWindow) throws JAXBException, IOException{
		
    	int iteration = 0;
    	int total = entries.size() * slotsData.size();
		
    	XMLEntranceSlotsDistancesCollection entranceSlotsDistancesCollection = new XMLEntranceSlotsDistancesCollection(new ArrayList<XMLEntranceSlotsDistances>());
    	
		for (BuildingEntrance entrance : entries) {
			XMLEntranceSlotsDistances entranceSlotsDistances = new XMLEntranceSlotsDistances(entrance.getDoorId(), new ArrayList<XMLEntryDistance>());
    		entranceSlotsDistancesCollection.getEntranceSlotsDistances().add(entranceSlotsDistances);
    		
    		for (SlotData slotData : slotsData) {
    			Point origPoint = new Point(entrance.getCoordinates().getLongitude(), entrance.getCoordinates().getLatitude());
        		Point destPoint = new Point(slotData.getCoordinates().getLongitude(), slotData.getCoordinates().getLatitude());
        		double distance = 0;
        		try{
        			SimulRoute pathToDestination = routeCalulator.getPath(origPoint, destPoint);
        			distance = pathToDestination.getShape_Length();
        		}
        		catch(Exception e){
        			// Nothing: Allow continuity
        		}
        		entranceSlotsDistances.getEntryDistances().add(new XMLEntryDistance(slotData.getObjectId(), distance));
    			
    			System.out.println("Iteration: " +  iteration++ + " of " + total + " done.");
    			if (iteration % savingWindow == 0){
    				xMLPersistenceManager.write(entranceSlotsDistancesCollection);
    			}
			}
    		dataInserter.insertInDB(entranceSlotsDistances);
		}

		xMLPersistenceManager.write(entranceSlotsDistancesCollection);
    }


	public static void main( String[] args )
    {
    	long endTime, startTime = (new Date()).getTime();
    	int savingWindow = 5000;
    	
    	try{
    		List<BuildingEntrance> entries = SmartCampusEntrances.getInstance().getBuildingEntrances();
    		
    		SlotsDataLoader manager = new SlotsDataLoader();
    		List<SlotData> slotsData = manager.getSlotsData();
    		RouteCalulator routeCalulator = PedestrianRouteCalulator.getInstance();
    		
    		XMLPersistenceManager<XMLEntranceSlotsDistancesCollection> xMLPersistenceManager =
    				new XMLPersistenceManager<>(XMLEntranceSlotsDistancesCollection.class, "C:\\save\\distances.xml");
    		DBDataInserter dataInserter = new DBDataInserter(entries);
    		
    		DataGenerator dataGenerator = new DataGenerator(entries, slotsData, routeCalulator);
    		
    		dataGenerator.calculate(xMLPersistenceManager, dataInserter, savingWindow);
    		
    		dataInserter.closeAndWait();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	endTime = (new Date()).getTime();
    	
        System.out.println( "All good! Done in: " + (endTime - startTime) + " miliseconds");
    }
}
