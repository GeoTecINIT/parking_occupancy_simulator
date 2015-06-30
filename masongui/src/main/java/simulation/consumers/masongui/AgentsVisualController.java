package simulation.consumers.masongui;

import java.util.HashMap;

import com.esri.core.geometry.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;

import sim.field.geo.GeomVectorField;
import sim.portrayal.geo.GeomVectorFieldPortrayal;
import sim.util.geo.MasonGeometry;
import sim.util.geo.PointMoveTo;
import simulation.model.support.ParkingAgentDescriptor;
import simulation.model.wrapping.AgentChangeNotification;
import simulation.model.wrapping.AgentsChangeUpdater;
import static simulation.consumers.masongui.GUIGlobalConstants.*;

public class AgentsVisualController extends AgentsChangeUpdater implements PortrayalElement{
	
	private final String ATTRIBUTE_NAME = "NAME";
	private final String ATTRIBUTE_TYPE = "TYPE";
	private final String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";
	
	private GeomVectorField agentsField;
	
	private GeomVectorFieldPortrayal agentsPortrayal;
	
	private HashMap<Long, MasonGeometry> movingLocations;
	private GeometryFactory fact;
	private PointMoveTo pointMoveTo;
	
	public AgentsVisualController(int width, int height) {
		super();
		fact = new GeometryFactory();
		pointMoveTo = new PointMoveTo();
		movingLocations = new HashMap<Long, MasonGeometry>();
		agentsField = new GeomVectorField(width, height);
		agentsPortrayal = new GeomVectorFieldPortrayal();
	}
	
	@Override
	public void setFieldMBR (Envelope MBR) {
		agentsField.setMBR(MBR);
	}
	
	@Override
	public Envelope getFieldMBR () {
		return agentsField.getMBR();
	}
	
	@Override
	public GeomVectorFieldPortrayal getPortrayal() {
		return agentsPortrayal;
	}
	
	@Override
	public void setup(){
		agentsField.clear();
		movingLocations.clear();
        agentsPortrayal.setField(agentsField);
        agentsPortrayal.setPortrayalForAll(new AgentOvalPortrayal2D(CAR_SYMBOL_SCALE, ATTRIBUTE_NAME, ATTRIBUTE_TYPE, ATTRIBUTE_DESCRIPTION));
	}

	@Override
	protected void doUpdate(AgentChangeNotification unit) {
		ParkingAgentDescriptor agentData = unit.getAgentData();
		Point position = agentData.getPosition();
		MasonGeometry location;
		// TODO En vez de mantener un map con los ids, meter los ids como atributos de las geometrias
		switch (unit.getAction()) {
		case AGENT_ADDING:
			location = new MasonGeometry(fact.createPoint(new Coordinate(position.getX(), position.getY())));
        	location.isMovable = true;
        	location.addAttribute(ATTRIBUTE_NAME, agentData.getType().toString());
        	location.addAttribute(ATTRIBUTE_TYPE, agentData.getParkingAgentState().toString());
        	location.addAttribute(ATTRIBUTE_DESCRIPTION, agentData.getDescription());
        	movingLocations.put(agentData.getId(), location);
        	agentsField.addGeometry(location);
			break;
		case AGENT_MOVING:
			location = movingLocations.get(agentData.getId());
			location.addAttribute(ATTRIBUTE_TYPE, agentData.getParkingAgentState().toString());
			pointMoveTo.setCoordinate(new Coordinate(position.getX(), position.getY()));
	        location.getGeometry().apply(pointMoveTo);
	        location.geometry.geometryChanged();
	        agentsField.setGeometryLocation(location, pointMoveTo);
			break;
		case AGENT_PARKING:
			location = movingLocations.get(agentData.getId());
			location.addAttribute(ATTRIBUTE_TYPE, agentData.getParkingAgentState().toString());
			break;
		case AGENT_LEAVING:
			location = movingLocations.get(agentData.getId());
			movingLocations.remove(agentData.getId());
			agentsField.getGeometries().remove(location);
			agentsField.updateSpatialIndex();	// Update visual changes
			break;
		default:
			System.out.println("ERRRRRRROOOOOORRRRR");
			break;
		}
	}
}
