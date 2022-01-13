package com.connell.colourbattle.networking.server.game;

import java.util.UUID;

import com.connell.colourbattle.networking.Packet;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Hitbox;
import com.connell.colourbattle.utilities.Vector2;

public abstract class ServerGameObject {
	private GameManager parentGame;
	
	private Vector2 position;
	private Hitbox hitbox;
	private Colour colour;
	
	private String id;
	
	public ServerGameObject(GameManager parentGame) {
		this.setParentGame(parentGame);
		
		this.setPosition(Vector2.ZERO);
		this.setHitbox(new Hitbox(new Vector2(0, 0), new Vector2(1, 1)));
		this.setColour(new Colour(255, 255, 255));
		
		this.setId(UUID.randomUUID().toString());
	}
	
	public abstract void start();
	public abstract void update();
	
	private void broadcast(Packet message) {
		this.getParentGame().getParentRoom().sendDataToAll(message);
	}
	
	protected void broadcastSelf() {
		
	}
	
	protected void broadcastPosition() {
		this.broadcast(new Packet(this.getId() + "_update_position", position.toString()));
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public GameManager getParentGame() {
		return parentGame;
	}

	public void setParentGame(GameManager parentGame) {
		this.parentGame = parentGame;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
