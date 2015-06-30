package tests;

import ec.util.MersenneTwisterFast;

/** 
 Generate pseudo-random floating point values, with an 
 approximately Gaussian (normal) distribution.

 Many physical measurements have an approximately Gaussian 
 distribution; this provides a way of simulating such values. 
*/
public final class RandomGaussian {

  public static void main(String... aArgs){
    RandomGaussian gaussian = new RandomGaussian();
    double LOW = 650.0f; 
    double HIGH = 680.0f;
    for (int idx = 1; idx <= 10000; ++idx){
      //log(idx + "\t- Generated : " + gaussian.getGaussian(LOW, HIGH));
    	log((int)(gaussian.getNormalDistDoubleInRange(LOW, HIGH)));
    }
  }

  private MersenneTwisterFast fRandom = new MersenneTwisterFast(System.currentTimeMillis()); 

  private double getNormalDistDoubleInRange(double low, double high){
	  double random = fRandom.nextGaussian() / 6.0f + 0.5f; //Make it be in [0,1]
	  return low + random * (high - low);
  }

  private static void log(Object aMsg){
    System.out.println(String.valueOf(aMsg));
  }
} 