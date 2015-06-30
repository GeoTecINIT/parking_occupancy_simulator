package simulation.data.configs.profiles;

import javax.xml.bind.annotation.XmlAttribute;

import org.junit.Assert;

import simulation.common.globals.RandomGenerator;

public class DoubleRandomizer {
	@XmlAttribute (name="UpLimit", required = true)
	private double upLimit;
	@XmlAttribute(name="DownLimit", required = true)
	private double downLimit;
	
	public DoubleRandomizer(){}
	
	public DoubleRandomizer(double upLimit, double downLimit) {
		super();
		this.upLimit = upLimit;
		this.downLimit = downLimit;
	}

	public double getUpLimit() {
		return upLimit;
	}

	public double getDownLimit() {
		return downLimit;
	}
	
	public double getRandom(){
		double difference = upLimit - downLimit;
		if (difference < 0) {
			Assert.fail("Problems");
		}
		double chosen = RandomGenerator.getInstance().nextDouble(true, true);
		return downLimit + difference * chosen;
	}
}
