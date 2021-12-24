package com.connell.colourbattle.utilities;

public class Colour {
	
	public static final Colour EMPTY = null;

	public int r;
	public int g;
	public int b;
	
	/**
	 * RGB Color Code Constructor
	 * @param i Red channel
	 * @param j Green channel
	 * @param k Blue channel
	 */
	public Colour(int i, int j, int k) {
		this.r = i;
		this.g = j;
		this.b = k;
	}
	
}
