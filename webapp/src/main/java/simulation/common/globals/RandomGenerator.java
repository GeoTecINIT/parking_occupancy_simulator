package simulation.common.globals;

import ec.util.MersenneTwisterFast;


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
}
