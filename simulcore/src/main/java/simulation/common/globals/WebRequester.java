package simulation.common.globals;

import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;


public abstract class WebRequester<T> {
	private Class<T> classType;
	private Client client;
	int count = 0;
	
	private int timeOut = 0;
	private String url = null;
	private MediaType requestMediaType = null;
	private Map<String, String> params = null;
	
	public WebRequester(Class<T> classType) {
		this.classType = classType;
		client = ClientBuilder.newClient();
	}
	
	public WebRequester(Class<T> classType, int timeOut, String url, MediaType requestMediaType, Map<String, String> params) {
		this.classType = classType;
		this.timeOut = timeOut;
		this.url = url;
		this.requestMediaType = requestMediaType;
		this.params = params;
		client = ClientBuilder.newClient();
	}
	
	protected T makeRequest() throws Exception{
		
		if (timeOut == 0 || url==null || requestMediaType == null || params == null){
			throw new Exception("WebRequester parameters not set");
		}
		
	    Response response = createRequestForGet().get();
	    preProcessResponse(response);
	    return response.readEntity(classType);
	}
	
	private Invocation.Builder createRequestForGet(){
	    String paramString = "";
	    for (Entry<String, String> entry : params.entrySet()) {
	    	paramString += entry.getKey() + "=" + entry.getValue() + "&";
		}
	    
	    WebTarget wt = client.target(getUrl() + paramString);
	    Invocation.Builder request = wt.request(getRequestMediaType());
	    request.property(ClientProperties.CONNECT_TIMEOUT, getTimeOut());
	    request.property(ClientProperties.READ_TIMEOUT, getTimeOut());
		return request;
	}
	
	protected void preProcessResponse(Response response){}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MediaType getRequestMediaType() {
		return requestMediaType;
	}

	public void setRequestMediaType(MediaType requestMediaType) {
		this.requestMediaType = requestMediaType;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}
