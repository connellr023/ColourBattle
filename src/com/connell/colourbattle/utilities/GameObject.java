package com.connell.colourbattle.utilities;

import java.util.UUID;

public class GameObject {
	private String id;
	
	private Vector2 position;
	private Hitbox hitbox;
	private Colour colour;
	
	public GameObject(Vector2 startPosition, Hitbox hitbox, Colour colour) {
		this.setPosition(startPosition);
		this.setHitbox(hitbox);
		this.setColour(colour);
		
		this.setId(UUID.randomUUID().toString());
	}
	
	public Hitbox getRelativeHitbox() {
		return new Hitbox(
			new Vector2(
				this.getHitbox().getTopLeft().getX() + this.getPosition().getX(),
				-this.getHitbox().getTopLeft().getY() + this.getPosition().getY() + 1
			),
			new Vector2(
				this.getHitbox().getBottomRight().getX() + this.getPosition().getX(),
				-this.getHitbox().getBottomRight().getY() + this.getPosition().getY() + 1
			)
		);
	}
	
	public void setX(float x) {
		this.setPosition(new Vector2(x, this.position.getY()));
	}
	
	public void setY(float y) {
		this.setPosition(new Vector2(this.position.getX(), y));
	}
	
	public Vector2 getCenter() {
		return new Vector2(this.position.getX() + (this.getHitbox().getWidth() / 2), this.position.getY() - (this.getHitbox().getHeight() / 2) + 1);
	}
	
	public float getLeftX() {
		return Math.round(this.getCenter().getX() - (this.getHitbox().getWidth() / 2));
	}
	
	public float getRightX() {
		return Math.round(this.getCenter().getX() + (this.getHitbox().getWidth() / 2));
	}
	
	public float getTopY() {
		return Math.round(this.getCenter().getY() - (this.getHitbox().getHeight() / 2));
	}
	
	public float getBottomY() {
		return Math.round(this.getCenter().getY() + (this.getHitbox().getHeight() / 2));
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
