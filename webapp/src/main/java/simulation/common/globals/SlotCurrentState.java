package simulation.common.globals;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.esri.core.geometry.Point;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SlotCurrentState {
	@XmlTransient
	public static SlotCurrentState NULL = new SlotCurrentState(null, 0);
	
	private SlotData slot;
	private Integer status;
	
	public SlotCurrentState() {}
	
	public SlotCurrentState(SlotData slot, Integer status) {
		super();
		this.slot = slot;
		this.status = status;
	}

	public SlotData getSlot() {
		return slot;
	}

	public void setSlot(SlotData slot) {
		this.slot = slot;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Point getPoint(){
		double x = slot.getCoordinates().getLongitude();
		double y = slot.getCoordinates().getLatitude();
		return new Point(x, y);
	}
	
	public static List<SlotCurrentState> createSlotsStates(List<SlotData> slots){
		List<SlotCurrentState> states = new ArrayList<SlotCurrentState>();
		for (SlotData slot : slots) {
			states.add(new SlotCurrentState(slot, SlotStatusStates.AVAILABLE.getValue()));
		}
		return states;
	}
	
	@Override
	public SlotCurrentState clone(){
		Slot newSlot = new Slot(getSlot().getParking(), getSlot().getSlot());
		SlotData newSlotData = new SlotData(getSlot().getCoordinates().clone(), getSlot().getObjectId(), newSlot, getSlot().getType());
		SlotCurrentState newOne = new SlotCurrentState(newSlotData, getStatus());
		return newOne;
	}
}
