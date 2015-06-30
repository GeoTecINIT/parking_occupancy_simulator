package simulation.data.configs.profiles;


public interface AgentsConfigsLoader {
	public abstract Configs readConfigs() throws Exception;
	public abstract void saveConfigs(Configs configs) throws Exception;
}