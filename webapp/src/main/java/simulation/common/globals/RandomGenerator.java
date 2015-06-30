package simulation.common.globals;

import ec.util.MersenneTwisterFast;

//TODO Hacer que no sea un singleton
public class RandomGenerator{
	private MersenneTwisterFast random = new MersenneTwisterFast(System.currentTimeMillis()); 
	
    private RandomGenerator() {
    	System.currentTimeMillis();
    }
    
	private static volatile RandomGenerator instance = null;
 
    public static RandomGenerator getInstance() {
        if (instance == null) {
            synchronized (RandomGenerator.class) {
                if (instance == null) {
                    instance = new RandomGenerator();
                }
            }
        }
        return instance;
    }
    
    public long nextLong(long n){
    	return random.nextLong(n);
    }
    
    public int nextInt(int n){
    	return random.nextInt(n);
    }
    
    public double nextDouble(boolean includeZero, boolean includeOne){
    	return random.nextDouble(includeZero, includeOne);
    }
    
    public double getNormalDistDoubleInRange(double low, double high){
		double randomN = -1;
		while ((randomN < 0) || (randomN > 1)){
			randomN = random.nextGaussian() / 6.0f + 0.5f; // Try to make it be in [0,1]
		}
		return low + randomN * (high - low);
    }
    
    public long getNormalDistLong(long n){
		return (long)getNormalDistDoubleInRange(0, n);
    }
}
