package com.connell.colourbattle.utilities;

public class Hitbox {

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
