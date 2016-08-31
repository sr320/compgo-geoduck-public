/*
 * StatUtils.java
 * Created on Jul 13, 2004
 * Created by Michael Riffle <mriffle@u.washington.edu>
 */

package org.yeastrc.compgo.geoduck.stats;

import java.lang.Math;

/**
 * Description of class goes here.
 * 
 * @author Michael Riffle <mriffle@u.washington.edu>
 * @version Jul 13, 2004
 */

public class StatUtils {

	/**
	 * Returns the natural log of the gamma function
	 * @param xx the value for which we are calculating gammaln
	 * @return the natural log of the gamma function for the given argument
	 */
	public static double gammaln(double xx) {
	 	double x, tmp, ser;
	 	double cof[] = {76.18009173, -86.50532033, 24.01409822, -1.231739516, 0.120858003e-2, -0.536382e-5};
	 	int j;
	 	
	 	x = xx - 1.0;
	 	tmp = x + 5.5;
	 	tmp-= (x+0.5)*Math.log(tmp);
	 	ser = 1.0;
	 	
	 	for(j = 0; j <= 5; j++) {
	 		 x += 1.0;
	 		 ser+= cof[j]/x;
	 	}

	 	return -tmp+Math.log(2.50662827465*ser);
	 }

	/**
	 * Calculate the probability that an intersection size a (or bigger) between sets of size
	 * b and c in a universe of size T
	 * @param ixn The size of the intersection
	 * @param a The size of the first set
	 * @param b The size of the second set
	 * @param T The size of the universe
	 * @return The probabiliyt of size a or bigger given b, c and T.
	 */
	public static double PScore(int ixn, int a, int b, int T) {
		double pp = 0.000;
		int i, m;
		
		if (ixn > a || ixn > b)
			throw new IllegalArgumentException("Intersection size must be <= sizes of sets a and b. (ixn: "+ ixn + " a,b:" + a + "," + b + ")");
		
		if (T < a || T < b)
			throw new IllegalArgumentException("Size of the Universe can not be less than sizes of sets a and b.");

        m = (a < b) ? a : b;

        for (i = 0; i < ( (m - ixn) + 1); i++) {
                pp += Math.exp(P(m-i,a-m+i,b-m+i,T-b-a+m-i));
        }

        return pp;
		
	}
	
	/**
	 * Desc here
	 * @param x
	 * @param y
	 * @param z
	 * @param N
	 * @return
	 */
	private static double P(double x, double y, double z, double N) {
		return gammaln(x+y+1)+gammaln(x+z+1)+gammaln(z+N+1)+gammaln(y+N+1)+
        -1*(gammaln(x+1)+gammaln(y+1)+gammaln(z+1)+gammaln(N+1)+
        gammaln(x+y+z+N+1));
	}
	
}
