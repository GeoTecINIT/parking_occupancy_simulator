package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import simulation.common.globals.BuildingEntrance;
import simulation.common.globals.RandomGenerator;
import simulation.common.globals.SlotData;
import simulation.data.configs.common.SlotsDataLoader;
import simulation.data.gisdata.places.Entrances;
import simulation.data.gisdata.places.EntrancesGetter;
import simulation.data.gisdata.routes.CarRouteCalulator;
import simulation.data.gisdata.routes.PedestrianRouteCalulator;
import simulation.data.gisdata.routes.RouteCalulator;
import simulation.model.support.EntrancePoint;
import simulation.model.support.SimulRoute;
import smartparking.assigner.generation.screquests.SmartCampusEntrances;

import com.esri.core.geometry.Point;


public class RequestTimeMeasurer
{
	private List<BuildingEntrance> entries;
	private List<SlotData> slotsData;
	private List<EntrancePoint> entrances;
	private RouteCalulator pedestRouteCalulator;
	private RouteCalulator carRouteCalulator;
	
    public RequestTimeMeasurer(List<BuildingEntrance> entries, List<SlotData> slotsData,
    		List<EntrancePoint> entrances, RouteCalulator pedestRouteCalulator, RouteCalulator carRouteCalulator) {
		super();
		this.entries = entries;
		this.slotsData = slotsData;
		this.entrances = entrances;
		this.pedestRouteCalulator = pedestRouteCalulator;
		this.carRouteCalulator = carRouteCalulator;
	}
    
    public void calculate(int iterations) throws JAXBException, IOException{
    	
    	RandomGenerator randGen = RandomGenerator.getInstance();
//    	SummaryStatistics summaryStatistics = new SummaryStatistics();
    	
    	List<Long> valuesPedest = new ArrayList<Long>();
    	List<Long> valuesCars = new ArrayList<Long>();
    	
    	for(int i = 0; i < iterations; i++){
    		BuildingEntrance entrance = entries.get(randGen.nextInt(entries.size()));
    		SlotData slotData = slotsData.get(randGen.nextInt(slotsData.size()));
    		EntrancePoint entrancePoint = entrances.get(randGen.nextInt(entrances.size()));
    		
			Point origBuildPoint = new Point(entrance.getCoordinates().getLongitude(), entrance.getCoordinates().getLatitude());
			Point origEntraPoint = entrancePoint.getPoint();
    		Point destPoint = new Point(slotData.getCoordinates().getLongitude(), slotData.getCoordinates().getLatitude());
    		
    		// Calculation for Pedestrian Routing Service
    		try{
    			long startTime = new Date().getTime();
    			pedestRouteCalulator.getPath(origBuildPoint, destPoint);
        		long endTime = new Date().getTime();
        		System.out.println("Iter. Pedest" + i + " Time: " + (endTime - startTime) + " miliseconds");
        		valuesPedest.add(endTime - startTime);
    		}
    		catch(Exception e){
    			System.out.println("Upppsssss Pedest");
    		}
    		
    		// Calculation for Cars Routing Service
    		try{
    			long startTime = new Date().getTime();
    			carRouteCalulator.getPath(origEntraPoint, destPoint);
        		long endTime = new Date().getTime();
        		System.out.println("Iter. Car" + i + " Time: " + (endTime - startTime) + " miliseconds");
        		valuesCars.add(endTime - startTime);
    		}
    		catch(Exception e){
    			System.out.println("Upppsssss Car");
    		}
    		
		}
    	Collections.sort(valuesPedest);
    	Collections.sort(valuesCars);
    	System.out.println("Median Pedest. Time: " + valuesPedest.get(valuesPedest.size()/2) + " miliseconds");
    	System.out.println("Median Car Time: " + valuesCars.get(valuesCars.size()/2) + " miliseconds");
    }


	public static void main( String[] args )
    {    	
    	try{
    		List<BuildingEntrance> entries = SmartCampusEntrances.getInstance().getBuildingEntrances();
    		List<EntrancePoint> entrances = EntrancesGetter.getInstance().getEntries();
    		
    		SlotsDataLoader manager = new SlotsDataLoader();
    		List<SlotData> slotsData = manager.getSlotsData();
    		
    		RouteCalulator pedestRouteCalulator = PedestrianRouteCalulator.getInstance();
    		RouteCalulator carRouteCalulator = CarRouteCalulator.getInstance();
    		
    		RequestTimeMeasurer dataGenerator = new RequestTimeMeasurer(entries, slotsData, entrances, pedestRouteCalulator, carRouteCalulator);
    		
    		dataGenerator.calculate(15000);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
        System.out.println("All good!");
    }
}
