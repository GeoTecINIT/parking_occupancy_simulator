package simulation.model.wrapping;

public class ModelNotification {
	private ModelNotificationType type;
	private Object value;
	
	public ModelNotification(ModelNotificationType type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}

	public ModelNotificationType getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
}
