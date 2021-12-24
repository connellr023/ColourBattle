package com.connell.colourbattle.utilities;

public class Vector2 {
	
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
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}