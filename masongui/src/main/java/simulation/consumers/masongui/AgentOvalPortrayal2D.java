package simulation.consumers.masongui;

import static simulation.common.globals.SimulGlobalConstants.*;
import static simulation.consumers.masongui.GUIGlobalConstants.*;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.simple.OvalPortrayal2D;
import sim.util.geo.MasonGeometry;
import simulation.data.configs.profiles.AgentProfileTypes;
import simulation.model.support.ParkingAgentStates;

public class AgentOvalPortrayal2D extends OvalPortrayal2D{
	private String attributeName;
	private String attributeType;
	private String attributeDescription;
	
	public AgentOvalPortrayal2D(double scale, String attributeName, String attributeType, String attributeDescription) {
		super(scale);
		this.attributeName = attributeName;
		this.attributeType = attributeType;
		this.attributeDescription = attributeDescription;
	}

	private static final long serialVersionUID = 1L;

	public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
		MasonGeometry mg = (MasonGeometry)object;
		AgentProfileTypes type = AgentProfileTypes.valueOf(mg.getStringAttribute(attributeName));
		ParkingAgentStates state = ParkingAgentStates.valueOf(mg.getStringAttribute(attributeType));
		String description = mg.getStringAttribute(attributeDescription);
		
		if (state == ParkingAgentStates.Parked){
			paint = PARKED_AGENT_COLOR;
		}
		else if (state == ParkingAgentStates.Leaving){
			paint = LEAVING_AGENT_COLOR;
		}
		else if (state == ParkingAgentStates.Tricked){
			paint = TRICKED_AGENT_COLOR;
		}
		else if (PREFER_DESCRIPTION){
			if (description.equals(EXPLORER_AGENT_DESCRIPTION)){
				paint = new Color(128, 64, 0);
			}
			else{
				paint = new Color(64, 0, 128);
			}
		}
		else{
			switch (type) {
			case ESTUDIANTE:
				paint = STUDENT_COLOR;
				break;
			case PAS:
				paint = PAS_COLOR;
				break;
			case PDI:
				paint = PDI_COLOR;
				break;
			default:
				paint = DEFAULT_COLOR;
				break;
			}
		}
	    super.draw(object, graphics, info);
    }
}
