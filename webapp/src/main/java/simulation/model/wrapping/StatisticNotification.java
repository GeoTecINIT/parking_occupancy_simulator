package simulation.model.wrapping;

public class StatisticNotification {
	private String key;
	private double value;
	
	public StatisticNotification(String action,
			double agentData) {
		super();
		this.key = action;
		this.value = agentData;
	}

	public String getKey() {
		return key;
	}

	public double getValue() {
		return value;
	}
}
