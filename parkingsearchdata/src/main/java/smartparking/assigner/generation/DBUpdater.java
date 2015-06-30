package smartparking.assigner.generation;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import es.uji.geotec.smartways.managedatabase.ManageDatabase;
import simulation.common.globals.EntranceWithOrderedSlots;
import simulation.common.globals.UpdaterBase;

public class DBUpdater extends UpdaterBase<EntranceWithOrderedSlots> {
	
	private static volatile DBUpdater instance = null;
	
	public static DBUpdater getInstance() {
        if (instance == null) {
            synchronized (DBUpdater.class) {
                if (instance == null) {
                    instance = new DBUpdater();
                }
            }
        }
        return instance;
 	}
	
	private ManageDatabase slotDB = null;
	private DBCollection distCollection = null;
	private ObjectMapper mapper;
	private boolean finished = false;
	
	private DBUpdater(){
		slotDB = new ManageDatabase();
		slotDB.connect();
		distCollection = slotDB.getCollection("entranceWithOrderedSlots");
		
		mapper = new ObjectMapper();
	 	mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
	            .withFieldVisibility(Visibility.ANY)
	            .withGetterVisibility(Visibility.NONE)
	            .withSetterVisibility(Visibility.NONE)
	            .withCreatorVisibility(Visibility.NONE));
	}
	
	@Override
	protected void doUpdate(EntranceWithOrderedSlots arg) {
		if (arg != EntranceWithOrderedSlots.NULL){
			System.out.println("updating");
			BasicDBObject dbObject = mapper.convertValue(arg, BasicDBObject.class);
			distCollection.insert(dbObject);
		}
		else{
			System.out.println("disconnecting");
			slotDB.disconnect();
			finished = true;
		}
	}
	
	public boolean isFinished(){
		return finished;
	}
}
