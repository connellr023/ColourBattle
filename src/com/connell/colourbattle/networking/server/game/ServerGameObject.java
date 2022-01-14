package com.connell.colourbattle.networking.server.game;

import com.connell.colourbattle.networking.Packet;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.GameObject;
import com.connell.colourbattle.utilities.Hitbox;
import com.connell.colourbattle.utilities.Vector2;

public abstract class ServerGameObject extends GameObject {
	private static char SPLIT_CHAR = '#';
	
	private GameManager parentGame;
	
	public ServerGameObject(GameManager parentGame) {
		super(Vector2.ZERO, new Hitbox(new Vector2(0, 0), new Vector2(1, 1)), new Colour(255, 255, 255));
		
		this.setParentGame(parentGame);
	}
	
	public abstract void start();
	public abstract void update();
	
	private void broadcast(Packet message) {
		this.getParentGame().getParentRoom().sendDataToAll(message);
	}
	
	protected void broadcastSelf() {
		this.broadcast(new Packet("new_object", this.encode()));
	}
	
	protected void broadcastPosition() {
		this.broadcast(new Packet(this.getId() + "_update_position", this.getPosition().toString()));
	}
	
	public static GameObject decode(String str) {
		String[] split = str.split(SPLIT_CHAR + "");
		
		String id = split[0];
		Colour colour = Colour.parse(split[1]);
		Hitbox hitbox = Hitbox.parse(split[2]);
		Vector2 position = Vector2.parse(split[3]);
		
		GameObject o = new GameObject(position, hitbox, colour);
		o.setId(id);
		
		return o;
	}
	
	public String encode() {
		return (this.getId() + SPLIT_CHAR + this.getColour().toString() + SPLIT_CHAR + this.getHitbox().toString() + SPLIT_CHAR + this.getPosition().toString());
	}

	public GameManager getParentGame() {
		return parentGame;
	}

	public void setParentGame(GameManager parentGame) {
		this.parentGame = parentGame;
	}
}
