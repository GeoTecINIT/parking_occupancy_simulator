package simulation.consumers.masongui;

import static simulation.consumers.masongui.GUIGlobalConstants.*;

import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.util.Observer;

import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.field.geo.GeomVectorField;
import sim.io.geo.ShapeFileImporter;
import sim.portrayal.Inspector;
import sim.portrayal.geo.GeomPortrayal;
import sim.portrayal.geo.GeomVectorFieldPortrayal;
import simulation.model.core.ParkingModel;
import simulation.model.wrapping.ModelEngine;

import com.vividsolutions.jts.geom.Envelope;

public class ParkingModelUI extends GUIState implements ModelEngine{
	
	private Display2D display;
	private JFrame displayFrame;
	
	private GeomVectorField roadLinesField;
	private GeomVectorFieldPortrayal shapePortrayal;
	
	private AgentsVisualController agentsVisualController;
	private SlotsVisualController slotsVisualController;
	
	private ParkingModel state;
    
    public ParkingModelUI(ParkingModel state, AgentsVisualController agentsVisualController, 
    		SlotsVisualController slotsVisualController) {
    	super(state);
    	this.state = state;
    	// The one that handles agents (cars) visualization
    	this.agentsVisualController = agentsVisualController;
    	this.slotsVisualController = slotsVisualController;
    	
    	try{
    		// TODO Manejar mejor esta excepción
	    	// Shape file with roads for background
			roadLinesField = new GeomVectorField(WINDOWS_WIDTH, WINDOWS_HEIGHT);
			shapePortrayal = new GeomVectorFieldPortrayal();
			String directoryPath = new File(ParkingModelUI.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParent();
	    	
			String path = "file:/" + directoryPath + "/" + ROADS_SHAPEFILE_RESOURCE_PATH;
			ShapeFileImporter.read(new URL(path), roadLinesField);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }

    public static String getName() {
    	return "UJI's Parking simulation";
    }
    
    public void start(){
        super.start();
        setupPortrayals();
//        try{
//        	state.run();
//        }
//        catch(Exception e){
//        	e.printStackTrace();
//        }
    }

    public void load(SimState state) {
        super.load(state);
        setupPortrayals();
    }
    
    public Object getSimulationInspectedObject() { return state; }

    public Inspector getInspector(){
        Inspector i = super.getInspector();
        i.setVolatile(true);
        return i;
    }

    public void setupPortrayals() {
        if (shapePortrayal.getField() == null){	// Avoid draw it twice when re-running it
        	shapePortrayal.setField(roadLinesField);
            shapePortrayal.setPortrayalForAll(new GeomPortrayal(ROADS_COLOR, true));
        }
        
    	agentsVisualController.setup();
    	slotsVisualController.setup();
    	
        Envelope MBR = roadLinesField.getMBR();
        agentsVisualController.setFieldMBR(MBR);
        slotsVisualController.setFieldMBR(MBR);

        // reschedule the displayer and redraw the display
        display.reset();
        display.setBackdrop(Color.white);
        display.repaint();
    }

    public void init(Controller c) {
        super.init(c);
        
        // make the displayer
        display = new Display2D(WINDOWS_WIDTH, WINDOWS_HEIGHT, this);

        display.attach(shapePortrayal, "Roads", true);
        display.attach(slotsVisualController.getPortrayal(), "Slots", true);
        display.attach(agentsVisualController.getPortrayal(), "Agents", true);
        
        // turn off clipping
        display.setClipping(false);

        displayFrame = display.createFrame();
        displayFrame.setTitle(getName());
        c.unregisterAllFrames();
        c.registerFrame(displayFrame);		// register the frame so it appears in the "Display" list
        displayFrame.setVisible(true);
    }

    public void quit() {
        super.quit();
        if (displayFrame!=null) {
        	displayFrame.dispose();
        }
        displayFrame = null;
        display = null;
    }

	@Override
	public Integer call() throws Exception {
        Console c = new Console(this);
        c.setVisible(true);
		return null;
	}
	
	// --------------------------------------------
	
	private boolean cancelled = false;

	@Override
	public void setCanceledFlag() {
		cancelled = true;
	}
	
	// --------------------------------------------
	// NOTIFICATIONS (TO UPDATERS) [JUST WRAPPERS]
	
	@Override
	public void addSlotChangeUpdater(Observer updater) {
		state.addSlotChangeUpdater(updater);
	}
	
	@Override
	public void addAgentChangeUpdater(Observer updater) {
		state.addAgentChangeUpdater(updater);
	}

	@Override
	public void addStaticticsChangeUpdater(Observer updater) {
		state.addStaticticsChangeUpdater(updater);
	}
	
	@Override
	public void notifySlotChange(Object arg) {
		state.notifySlotChange(arg);
	}
	
	@Override
	public void notifyAgentChange(Object arg) {
		state.notifyAgentChange(arg);
	}

	@Override
	public void notifyStaticticsChange(Object arg) {
		state.notifyStaticticsChange(arg);
	}
	
	// --------------------------------------------
}
