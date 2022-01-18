package com.connell.colourbattle.networking.server.game;

import java.util.concurrent.ThreadLocalRandom;

import com.connell.colourbattle.utilities.Constants;
import com.connell.colourbattle.utilities.Hitbox;
import com.connell.colourbattle.utilities.Vector2;

public class Platform extends ServerGameObject {
	private float frictionFactor;

	public Platform(GameManager parentGame, Vector2 startPosition, Hitbox hitbox, float frictionFactor) {
		super(parentGame);
		
		this.setPosition(startPosition);
		this.setHitbox(hitbox);
		
		this.setFrictionFactor(frictionFactor);
	}

	@Override
	public void start() {
		this.broadcastSelf();
	}
	
	@Override
	public void update() {

	}
	
	public static Platform random(GameManager parentGame, int minWidth, int maxWidth, int minHeight, int maxHeight, float frictionFactor) {
		int width = ThreadLocalRandom.current().nextInt(minWidth, maxWidth + 1);
		int height = ThreadLocalRandom.current().nextInt(minHeight, maxHeight + 1);
		
		Vector2 gameSize = Constants.GAME_SIZE;
		
		Hitbox collider = new Hitbox(Vector2.ZERO, new Vector2(width, height));
		Vector2 position = new Vector2(ThreadLocalRandom.current().nextInt(0, (int) gameSize.getX()), ThreadLocalRandom.current().nextInt(0, (int) gameSize.getY() - 1));
		
		Platform p = new Platform(parentGame, position, collider, frictionFactor);
		
		return p;
	}
	
	public boolean doesIntersect() {
		for (ServerGameObject o : this.getParentGame().getGameObjects()) {
			if (this.isColliding(o)) {
				return true;
			}
		}
		
		return false;
	}

	public float getFrictionFactor() {
		return frictionFactor;
	}

	public void setFrictionFactor(float frictionFactor) {
		this.frictionFactor = frictionFactor;
	}
}
