package simulation.data.gisdata.places;

import java.util.ArrayList;
import java.util.List;

import simulation.common.globals.BuildingEntrance;
import simulation.model.support.BuildingEntranceDestination;

public class DestinationsGetter {
	private static volatile DestinationsGetter instance = null;
	
	private DestinationsGetter() {}
	
	public static DestinationsGetter getInstance() {
        if (instance == null) {
            synchronized (DestinationsGetter.class) {
                if (instance == null) {
                    instance = new DestinationsGetter();
                }
            }
        }
        return instance;
    }
	
	// TODO Replace buildingEntrance.getScBuildingId() for buildingEntrance.getDescription() when Description are available
	public List<BuildingEntranceDestination> getEntries(){
		List<BuildingEntranceDestination> list = new ArrayList<BuildingEntranceDestination>();
		SmartCampusEntrances smartCampusEntrances = SmartCampusEntrances.getInstance();
		try{
			for (BuildingEntrance buildingEntrance : smartCampusEntrances.getBuildingEntrances()) {
				list.add(new BuildingEntranceDestination(buildingEntrance.getDoorId(), buildingEntrance.getScBuildingId(),
						buildingEntrance.getDoorId(), /*buildingEntrance.getDescription()*/buildingEntrance.getScBuildingId(),
						buildingEntrance.getCoordinates().getLongitude(), buildingEntrance.getCoordinates().getLatitude()));
			}
		}
		catch(Exception e){
			//TODO Improve this 
			e.printStackTrace();
		}
		return list;
	}
}
