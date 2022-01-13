package com.connell.colourbattle.utilities;

public class Vector2 {
	private static char SPLIT_CHAR = ',';
	
	private float x;
	private float y;
	
	public static final Vector2 ZERO = new Vector2(0, 0);
	
	/**
	 * Vector2 Constructor
	 * @param x A Coordinate on the X Axis
	 * @param y A Coordinate on the Y Axis
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static Vector2 parse(String str) {
		String[] split = str.split(SPLIT_CHAR + "");
		
		float x = Float.parseFloat(split[0]);
		float y = Float.parseFloat(split[1]);
		
		return new Vector2(x, y);
	}
	
	@Override
	public String toString() {
		return (this.getX() + "" + SPLIT_CHAR + this.getY());
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}