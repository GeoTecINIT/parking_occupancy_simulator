package simulation.consumers.web;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CO2Indicators {
	@XmlElement
	private int guidedCO2;
	@XmlElement
	private int explorerCO2;
	@XmlElement
	private int accumulatedGuidedCO2;
	@XmlElement
	private int accumulatedExplorerCO2;
	
	public CO2Indicators(){
		this(0, 0, 0, 0);
	}
	
	public CO2Indicators(int guidedCO2, int explorerCO2, int accumulatedGuidedCO2, int accumulatedExplorerCO2) {
		super();
		this.guidedCO2 = guidedCO2;
		this.explorerCO2 = explorerCO2;
		this.accumulatedGuidedCO2 = accumulatedGuidedCO2;
		this.accumulatedExplorerCO2 = accumulatedExplorerCO2;
	}

	public int getGuidedCO2() {
		return guidedCO2;
	}

	public int getExplorerCO2() {
		return explorerCO2;
	}

	public int getAccumulatedGuidedCO2() {
		return accumulatedGuidedCO2;
	}

	public int getAccumulatedExplorerCO2() {
		return accumulatedExplorerCO2;
	}
}
