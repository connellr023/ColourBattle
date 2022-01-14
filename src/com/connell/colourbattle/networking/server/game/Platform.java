package com.connell.colourbattle.networking.server.game;

import com.connell.colourbattle.utilities.Hitbox;
import com.connell.colourbattle.utilities.Vector2;

public class Platform extends ServerGameObject {

	public Platform(GameManager parentGame, Vector2 startPosition, Hitbox hitbox) {
		super(parentGame);
		
		this.setPosition(startPosition);
		this.setHitbox(hitbox);
	}

	@Override
	public void start() {
		
	}
	
	@Override
	public void update() {

	}
}
