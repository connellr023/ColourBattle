package com.connell.colourbattle.networking.server.game;

import com.connell.colourbattle.utilities.Vector2;

public abstract class ServerGameObject {
	private Vector2 position;
	
	public ServerGameObject() {
		this.setPosition(Vector2.ZERO);
	}
	
	public abstract void update();

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
