package simulation.data.configs.profiles;

import javax.xml.bind.annotation.XmlElement;

import org.junit.Assert;

import simulation.common.globals.RandomGenerator;
import simulation.model.support.Timing;


public class TimeRandomizer {
	@XmlElement(name="UpLimit", required = true)
	private Timing upLimit;
	@XmlElement(name="DownLimit", required = true)
	private Timing downLimit;
	
	public TimeRandomizer(){}
	
	public TimeRandomizer(Timing upLimit, Timing downLimit) {
		super();
		this.upLimit = upLimit;
		this.downLimit = downLimit;
	}

	public Timing getUpLimit() {
		return upLimit;
	}

	public Timing getDownLimit() {
		return downLimit;
	}
	
	public Timing getRandom(){
		if (upLimit.compareTo(downLimit) < 0) {
			Assert.fail("Problems");
		}
		long difference = (long) (Timing.toBaseTime(upLimit) - Timing.toBaseTime(downLimit));
		long chosen = RandomGenerator.getInstance().nextLong(difference);
		Timing result = downLimit.getAddedTime(Timing.fromBaseTime(chosen));
		return result;
	}
}
