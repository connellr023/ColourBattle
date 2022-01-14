package com.connell.colourbattle.utilities;

public class Hitbox {
	private static char SPLIT_CHAR = '&';
	
	/**
	 * The Top Left Corner of the Hit Box
	 */
	private Vector2 topLeft;
	
	/**
	 * The Bottom Right Corner of the Hit Box
	 */
	private Vector2 bottomRight;
	
	public Hitbox(Vector2 topLeft, Vector2 bottomRight) {
		this.setTopLeft(topLeft);
		this.setBottomRight(bottomRight);
	}
	
	public static Hitbox parse(String str) {
		String[] split = str.split(SPLIT_CHAR + "");
		
		Vector2 topLeft = Vector2.parse(split[0]);
		Vector2 bottomRight = Vector2.parse(split[1]);
		
		return new Hitbox(topLeft, bottomRight);
	}
	
	@Override
	public String toString() {
		return (this.getTopLeft().toString() + "" + SPLIT_CHAR + this.getBottomRight().toString());
	}
	
	public void setTopLeft(Vector2 topLeft) {
		this.topLeft = topLeft;
	}
	
	public Vector2 getTopLeft() {
		return this.topLeft;
	}
	
	public float getWidth() {
		return this.getBottomRight().getX();
	}
	
	public float getHeight() {
		return this.getBottomRight().getY();
	}
	
	public void setBottomRight(Vector2 bottomRight) {
		this.bottomRight = bottomRight;
	}

	public Vector2 getBottomRight() {
		return this.bottomRight;
	}
}
