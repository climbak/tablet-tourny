package com.jrm.tablettournament.shared;

public final class MathUtil {
	
	/**
	 * determine if two points are close to each other (no sqrt=faster)
	 * 
	 * @return true if points are within the given max distance
	 */
	public static boolean pointsClose(float x1, float y1, float x2, float y2, float maxDistance){
		return Math.pow(x1-x2,2) + Math.pow(y1-y2,2) < Math.pow(maxDistance, 2);
	}
}
