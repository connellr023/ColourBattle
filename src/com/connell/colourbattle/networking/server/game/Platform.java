package com.connell.colourbattle.networking.server.game;

import com.connell.colourbattle.utilities.Vector2;

public class Platform extends ServerGameObject {

	public Platform(GameManager parentGame, Vector2 startPosition) {
		super(parentGame);
		
		this.setPosition(startPosition);
	}

	@Override
	public void start() {
		
	}
	
	@Override
	public void update() {

	}
}
