package simulation.model.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import simulation.common.globals.RandomGenerator;
import simulation.model.support.BuildingEntranceDestination;

public class DestinationMap {
	private HashMap<Integer, List<BuildingEntranceDestination>> destinationsPerBuilding;
	private List<BuildingEntranceDestination> destinations;
	
	public DestinationMap(List<BuildingEntranceDestination> destinations) {
		super();
		this.destinations = destinations;
		destinationsPerBuilding = new HashMap<Integer, List<BuildingEntranceDestination>>();
		for (BuildingEntranceDestination destinationPoint : destinations) {
			int building = destinationPoint.getBuilding();
			if (building != 0){
				List<BuildingEntranceDestination> buildingDest = destinationsPerBuilding.get(building);
				if (buildingDest == null){
					buildingDest = new ArrayList<BuildingEntranceDestination>();
					destinationsPerBuilding.put(building, buildingDest);
				}
				buildingDest.add(destinationPoint);
			}
		}
	}
	
	public BuildingEntranceDestination getDestinationFor(int building, int door){
		if ((door != 0) && (building != 0)){
			for (BuildingEntranceDestination destinationPoint : destinations) {
				if ((destinationPoint.getDoor() == door) && (destinationPoint.getBuilding() == building)){
					return destinationPoint;
				}
			}
		}
		return BuildingEntranceDestination.NULL;
	}
	
	public BuildingEntranceDestination getRandomDestinationFor(int building){
		if (building != 0){
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
	public List<BuildingEntranceDestination> getDestinationsFor(int building){
		List<BuildingEntranceDestination> buildingDestinations = new LinkedList<BuildingEntranceDestination>();
		for (BuildingEntranceDestination destinationPoint : destinationsPerBuilding.get(building)) {
			buildingDestinations.add(destinationPoint);
		}
		return buildingDestinations;
	}
	
	public List<Integer> getBuildings(){
		List<Integer> buidlings = new LinkedList<Integer>();
		for (Integer integer : destinationsPerBuilding.keySet()) {
			buidlings.add(integer);
		}
		return buidlings;
	}
}
