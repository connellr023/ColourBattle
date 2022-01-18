package com.connell.colourbattle.utilities;

import java.util.concurrent.ThreadLocalRandom;

public class Colour {
	private static char SPLIT_CHAR = ',';
	
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
	
	public static Colour parse(String str) {
		String[] split = str.split(SPLIT_CHAR + "");

		int r = Integer.parseInt(split[0]);
		int g = Integer.parseInt(split[1]);
		int b = Integer.parseInt(split[2]);
		
		return new Colour(r, g, b);
	}
	
	public static Colour random() {
		int r = ThreadLocalRandom.current().nextInt(0, 255);
		int g = ThreadLocalRandom.current().nextInt(0, 255);
		int b = ThreadLocalRandom.current().nextInt(0, 255);
		
		return new Colour(r, g, b);
	}
	
	public static Colour lerp(Colour a, Colour b, float f) {
		return new Colour((int) (a.r + f * (b.r - a.r)), (int) (a.g + f * (b.g - a.g)), (int) (a.b + f * (b.b - a.b)));
	}
	
	@Override
	public String toString() {
		return (this.r + "" + SPLIT_CHAR + this.g + SPLIT_CHAR + this.b);
	}
}
