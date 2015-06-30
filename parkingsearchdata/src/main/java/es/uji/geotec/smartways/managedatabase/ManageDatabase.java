package es.uji.geotec.smartways.managedatabase;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;



public class ManageDatabase {
	private		String		server;
	private		Integer 	port;
	//private		String		username;
	//private		String		password;
	private		MongoClient mongoClient;
	private		DB			database;
	private		String		databasename;
	private     boolean     connected = false;    
	
	public ManageDatabase() {
		super();
		//server 		 = "150.128.80.204";
		server 		 = "127.0.0.1";
		port   		 = 27017;
		//username 	 = "";
		//password 	 = "";
		databasename = "smartparking";
	}
	
	
	public ManageDatabase(String server, Integer port, MongoClient mongoClient,
			DB database, String databasename, boolean connected) {
		super();
		this.server = server;
		this.port = port;
		this.mongoClient = mongoClient;
		this.database = database;
		this.databasename = databasename;
		this.connected = connected;
	}


	public void connect(){
		try {
			this.mongoClient = new MongoClient(server, port);
			this.database    = this.mongoClient.getDB(databasename);
			this.connected   = true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			this.connected   = false;
		}
	}
	
	
	public void disconnect(){
		this.mongoClient.close();
	}
	
	public boolean isConnected(){
		return this.connected;
	}
	
	public void setDB(String database){
		this.databasename = database; 
		this.database     = this.mongoClient.getDB(database);
	}
	
	public DBCollection getCollection(String collection){
		return this.database.getCollection(collection);
	}
	
}
