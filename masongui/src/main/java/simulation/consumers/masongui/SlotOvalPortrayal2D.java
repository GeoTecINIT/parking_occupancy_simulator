package simulation.consumers.masongui;

import static simulation.consumers.masongui.GUIGlobalConstants.*;

import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.simple.OvalPortrayal2D;
import sim.util.geo.MasonGeometry;
import simulation.common.globals.SlotStatusStates;

public class SlotOvalPortrayal2D extends OvalPortrayal2D{
	private String attributeState;
	
	public SlotOvalPortrayal2D(double scale, String attributeState) {
		super(scale);
		this.attributeState = attributeState;
	}

	private static final long serialVersionUID = 1L;

	public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
		MasonGeometry mg = (MasonGeometry)object;
		SlotStatusStates state = SlotStatusStates.valueOf(mg.getStringAttribute(attributeState));
		switch (state) {
		case AVAILABLE:
			paint = AVAILABLE_SLOT_COLOR;
			break;
		case OCCUPIED:
			paint = OCCUPIED_SLOT_COLOR;
			break;
		case DISABLED:
			paint = DISABLED_SLOT_COLOR;
			break;
		case REQUESTED:
			paint = REQUESTED_SLOT_COLOR;
			break;
		default:
			paint = DEFAULT_COLOR;
			break;
		}
	    super.draw(object, graphics, info);
    }
}
