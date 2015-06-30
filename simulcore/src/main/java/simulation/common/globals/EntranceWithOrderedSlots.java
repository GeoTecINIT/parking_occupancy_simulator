package simulation.common.globals;

import java.util.List;

public class EntranceWithOrderedSlots {
	public static EntranceWithOrderedSlots NULL = new EntranceWithOrderedSlots();
	
	private BuildingEntrance buildingEntrance;
	private List<Integer> orderedSlotIds;
	
	public EntranceWithOrderedSlots(){}

	public EntranceWithOrderedSlots(BuildingEntrance buildingEntrance,
			List<Integer> entryDistances) {
		super();
		this.buildingEntrance = buildingEntrance;
		this.orderedSlotIds = entryDistances;
	}

	public BuildingEntrance getBuildingEntrance() {
		return buildingEntrance;
	}

	public void setBuildingEntrance(BuildingEntrance buildingEntrance) {
		this.buildingEntrance = buildingEntrance;
	}

	public List<Integer> getOrderedSlotIds() {
		return orderedSlotIds;
	}

	public void setOrderedSlotIds(List<Integer> entryDistances) {
		this.orderedSlotIds = entryDistances;
	}
}
