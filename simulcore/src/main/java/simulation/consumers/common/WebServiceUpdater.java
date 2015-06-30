package simulation.consumers.common;

import simulation.common.globals.SlotCurrentState;
import simulation.common.smartparkingws.SmartParkingWServices;
import simulation.model.wrapping.SlotCurrentStateUpdater;

public class WebServiceUpdater extends SlotCurrentStateUpdater {
	
	private static class RequestHandler implements Runnable {
		private SlotCurrentState arg;
		
		public RequestHandler(SlotCurrentState arg){
			this.arg = arg;
		}
		
		public void run() {
			SmartParkingWServices smartParkingWS = SmartParkingWServices.getInstance();
			smartParkingWS.changeSlotStatus(arg);
		}
	}
	
	public WebServiceUpdater(){	}
	
	@Override
	protected void doUpdate(SlotCurrentState arg) {
		Thread t = new Thread(new RequestHandler(arg));
        t.start();
	}
}
