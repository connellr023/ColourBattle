package com.connell.colourbattle.networking.server.game;

import com.connell.colourbattle.utilities.Vector2;

public class Player extends ServerGameObject {

	public Player(GameManager parentGame, Vector2 startPosition) {
		super(parentGame);
		
		this.setPosition(startPosition);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
}
