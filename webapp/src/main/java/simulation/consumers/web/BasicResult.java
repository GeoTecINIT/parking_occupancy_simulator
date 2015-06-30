package simulation.consumers.web;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BasicResult {
	@XmlElement
	private boolean result;
	
	public BasicResult(){
		this(false);
	}
	
	public BasicResult(boolean result) {
		super();
		this.result = result;
	}

	public boolean isResult() {
		return result;
	}
}
