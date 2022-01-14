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
