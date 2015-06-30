package simulation.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import simulation.common.globals.RandomGenerator;
import simulation.model.support.BuildingEntranceDestination;

public class DestinationMap {
	private HashMap<String, List<BuildingEntranceDestination>> destinationsPerBuilding;
	private List<BuildingEntranceDestination> destinations;
	
	public DestinationMap(List<BuildingEntranceDestination> destinations) {
		super();
		this.destinations = destinations;
		destinationsPerBuilding = new HashMap<String, List<BuildingEntranceDestination>>();
		for (BuildingEntranceDestination destinationPoint : destinations) {
			String building = destinationPoint.getBuilding();
			if (building != null){
				List<BuildingEntranceDestination> buildingDest = destinationsPerBuilding.get(building);
				if (buildingDest == null){
					buildingDest = new ArrayList<BuildingEntranceDestination>();
					destinationsPerBuilding.put(building, buildingDest);
				}
				buildingDest.add(destinationPoint);
			}
		}
	}
	
	public BuildingEntranceDestination getDestinationFor(String building, int door){
		if ((door != 0) && (building != null)){
			for (BuildingEntranceDestination destinationPoint : destinations) {
				if ((destinationPoint.getDoor() == door) && (destinationPoint.getBuilding().equals(building))){
					return destinationPoint;
				}
			}
		}
		return BuildingEntranceDestination.NULL;
	}
	
	public BuildingEntranceDestination getRandomDestinationFor(String building){
		if (building != null){
			List<BuildingEntranceDestination> buildingDest = destinationsPerBuilding.get(building);
			int amount = buildingDest.size();
			if (amount == 0){
				return BuildingEntranceDestination.NULL;
			}
			int chosen = RandomGenerator.getInstance().nextInt(amount);
			return buildingDest.get(chosen);
		}
		return BuildingEntranceDestination.NULL;
	}
	
	public BuildingEntranceDestination getAnyRandomDestination(){
		int amount = destinations.size();
		if (amount == 0){
			return BuildingEntranceDestination.NULL;
		}
		int chosen = RandomGenerator.getInstance().nextInt(amount);
		return destinations.get(chosen);
	}
	
	/*
	 * Returns a copy of the destination points for a building 
	 */
	public List<BuildingEntranceDestination> getDestinationsFor(String building){
		List<BuildingEntranceDestination> buildingDestinations = new LinkedList<>();
		for (BuildingEntranceDestination destinationPoint : destinationsPerBuilding.get(building)) {
			buildingDestinations.add(destinationPoint);
		}
		return buildingDestinations;
	}
	
	public List<String> getBuildings(){
		List<String> buidlings = new LinkedList<>();
		for (String string : destinationsPerBuilding.keySet()) {
			buidlings.add(string);
		}
		return buidlings;
	}
}
