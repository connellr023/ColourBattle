package com.connell.colourbattle.networking.server.game;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

import com.connell.colourbattle.networking.server.ClientHandler;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Constants;
import com.connell.colourbattle.utilities.GameObject;
import com.connell.colourbattle.utilities.Vector2;

public class Player extends ServerGameObject {
	private LinkedList<Platform> collisions;
	private ClientHandler client;
	
	private Vector2 velocity;
	
	private float gravityAcceleration;
	
	private boolean onGround;
	
	private boolean isCollidingUpward;
	private boolean isCollidingDown;
	private boolean isCollidingLeft;
	private boolean isCollidingRight;

	public Player(GameManager parentGame, ClientHandler client, Colour colour) {
		super(parentGame);
		
		this.setColour(colour);
		
		this.setClient(client);
		this.setVelocity(Vector2.ZERO);
		this.setGravityAcceleration(0.05f);
		
		this.setCollisions(new LinkedList<Platform>());
		
	}

	@Override
	public void start() {
		this.setPosition(this.generateSpawnPoint());
		this.broadcastSelf();
	}
	
	@Override
	public void update() {
		this.updateLevelCollisions();
		this.handlePhysics();
	}
	
	/**
	 * Constantly Updates the List of Level Pieces this Player is Colliding With
	 */
	private void updateLevelCollisions() {
		this.getCollisions().clear();
		
		for (Platform p : this.getParentGame().getLevel()) {
			if (this.isColliding(p)) {
				this.getCollisions().add(p);
			}
		}
	}
	
	private Vector2 generateSpawnPoint() {
		int index = ThreadLocalRandom.current().nextInt(0, this.getParentGame().getLevel().size());
		
		Platform p = (Platform) this.getParentGame().getLevel().toArray()[index];
		Vector2 pos = p.getRelativeHitbox().getTopLeft();
		
		return new Vector2(pos.getX(), pos.getY() + 10);
	}
	
	private void handlePhysics() {
		float vel = Math.abs(this.getVelocity().getY());
		this.onGround = ((vel == this.getGravityAcceleration() || vel == 0) && !this.isCollidingUpward);
		
		// Update Collisions
		this.updateLevelCollisions();
		
		// Handle Collisions With Level
		this.handlePlatformCollisions();
		
		if (!(this.getCollisionCount() > 0)) {
			this.setVerticalVelocity(this.getVelocity().getY() + this.getGravityAcceleration());
			
			if (this.getPosition().getY() > Constants.GAME_SIZE.getY() + 5) {
				this.destroy();
			}
		}
		
		this.setPosition(new Vector2(this.getPosition().getX() + this.getVelocity().getX(), this.getPosition().getY() + this.getVelocity().getY()));
	}
	
	private void handlePlatformCollisions() {
		for (Platform p : this.getCollisions()) {			
			this.isCollidingUpward = false;
			this.isCollidingLeft = false;
			this.isCollidingRight = false;
			
			// Horizontal Collisions
			if ((this.onGround && this.getCollisionCount() == 2 && this.getCollisions().get(0).getTopY() != this.getCollisions().get(1).getTopY()) || (!this.onGround && this.getCollisionCount() == 1) || (this.getCollisionCount() == 1 && this.getCollisions().get(0).getTopY() != this.getBottomY())) {
				if (this.getRightX() <= p.getLeftX()) { // On Left Side
					this.setX(p.getRelativeHitbox().getTopLeft().getX() - this.getHitbox().getBottomRight().getX());
					this.setHorizontalVelocity(0);
					
					this.isCollidingLeft = true;
				}
				else if (this.getLeftX() >= p.getRightX()) { // On Right Side
					this.setX(p.getRelativeHitbox().getBottomRight().getX());
					this.setHorizontalVelocity(0);
					
					this.isCollidingRight = true;					
				}
			}
			
			// Vertical Collisions
			if (!this.isCollidingLeft && !this.isCollidingRight) {
				this.setVerticalVelocity(0);
				
				if (this.getBottomY() <= p.getTopY()) { // On Top
					
					// Create Friction on Ground
					if (this.onGround) {
						this.setHorizontalVelocity(this.getVelocity().getX() * p.getFrictionFactor());
					}
				}
				else if (this.getTopY() >= p.getBottomY()) { // On Bottom
					this.setY(p.getPosition().getY() + this.getHitbox().getBottomRight().getY());
					
					this.isCollidingUpward = true;
				}
				else {
					this.moveOutsideObject(p);
				}
			}
		}
	}
	
	/**
	 * Move Object Outside of Hit Box if it is Inside of it
	 * @param collidingObj Is the Game Object Being Collided With
	 */
	public void moveOutsideObject(GameObject collidingObj) {
		if (this.onGround) {			
			this.setY(collidingObj.getPosition().getY() - (collidingObj.getHitbox().getBottomRight().getY()));
		}
	}
	
	public int getCollisionCount() {
		int c = this.getCollisions().size();
		
		if (c > 2) {
			return 2;
		}
		
		return c;
	}

	public ClientHandler getClient() {
		return client;
	}

	public void setClient(ClientHandler client) {
		this.client = client;
	}

	public LinkedList<Platform> getCollisions() {
		return collisions;
	}

	public void setCollisions(LinkedList<Platform> collisions) {
		this.collisions = collisions;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public void setHorizontalVelocity(float velocity) {
		this.setVelocity(new Vector2(velocity, this.getVelocity().getY()));
	}
	
	public void setVerticalVelocity(float velocity) {
		this.setVelocity(new Vector2(this.getVelocity().getX(), velocity));
	}

	public float getGravityAcceleration() {
		return gravityAcceleration;
	}

	public void setGravityAcceleration(float gravityAcceleration) {
		this.gravityAcceleration = gravityAcceleration;
	}
}
