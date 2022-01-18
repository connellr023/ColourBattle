package com.connell.colourbattle.networking.server.game;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Constants;
import com.connell.colourbattle.utilities.GameObject;
import com.connell.colourbattle.utilities.Hitbox;
import com.connell.colourbattle.utilities.Vector2;

public class Player extends ServerGameObject {
	private LinkedList<Platform> collisions;
	
	private Vector2 velocity;
	
	private float gravityAcceleration;
	
	/**
	 * The Value Added to Y Component of the Player's Velocity When Jumping
	 */
	private float jumpVelocity;
	
	/**
	 * Maximum Amount of Jumps a Player Instance can Have
	 */
	private int maxJumps;
	
	/**
	 * Keeps Track of Amount of Jumps this Player has Remaining
	 */
	private int jumpsLeft;
	
	/**
	 * The Value Added to X Component of the Player's Velocity When Moving Left or Right
	 */
	private float moveVelocity;
	
	/**
	 * Player Movement Velocity When in Air
	 */
	private float inAirMoveVelocity;
	
	/**
	 * Acceleration During Horizontal Game Object Movement
	 */
	private float horizontalAcceleration;
	
	/**
	 * True for a Single Frame this Player Lands on Ground
	 */
	private boolean hasLandedJump = false;
	
	private boolean onGround;
	
	private boolean isCollidingUpward;
	private boolean isCollidingLeft;
	private boolean isCollidingRight;

	public Player(GameManager parentGame, Colour colour) {
		super(parentGame);
		
		this.setColour(colour);
		this.setHitbox(new Hitbox(new Vector2(0, 0), new Vector2(1, 1)));

		this.setVelocity(Vector2.ZERO);
		this.setGravityAcceleration(0.015f);
		this.setMoveVelocity(0.19f);
		this.setInAirMoveVelocity(0.15f);
		this.setHorizontalAcceleration(0.006f);
		
		this.setMaxJumps(2);
		this.setJumpVelocity(0.35f);
		this.setMoveVelocity(0.25f);
		this.setJumpsLeft(this.getMaxJumps());
		
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
		
		this.updatePosition();
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
		
		return new Vector2(p.getCenter().getX(), p.getTopY() - 2);
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
			else if (!this.onGround) {
				this.hasLandedJump = false;
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
						
						if (!this.hasLandedJump) {
							this.moveOutsideObject(p);
							
							this.setJumpsLeft(this.getMaxJumps());
							this.hasLandedJump = true;
						}
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
			this.setY(collidingObj.getTopY() - this.getHitbox().getHeight());
		}
	}
	
	/*
	 * Makes this Player Move Left
	 */
	public void moveLeft() {
		this.setMoveVelocity(-Math.abs(this.getMoveVelocity()))  ;
		
		if (!this.isCollidingRight) {			
			float vel = this.getMoveVelocity();
			
			if (!this.onGround) {
				vel = -this.getInAirMoveVelocity();
			}

			this.setX(this.getPosition().getX() - this.getHorizontalAcceleration());
			this.setHorizontalVelocity(vel);
		}
	}
	
	/**
	 * Makes this Player Move Right
	 */
	public void moveRight() {
		this.setMoveVelocity(Math.abs(this.getMoveVelocity()));
		
		if (!this.isCollidingLeft) {
			float vel = this.getMoveVelocity();
			
			if (!this.onGround) {
				vel = this.getInAirMoveVelocity();
			}
			
			this.setX(this.getPosition().getX() + this.getHorizontalAcceleration());
			this.setHorizontalVelocity(vel);
		}
	}
	
	/**
	 * Makes this Player Jump
	 */
	public void jump() {
		if (this.getJumpsLeft() > 0 && !this.isCollidingLeft && !this.isCollidingRight) {
			this.setY(this.getPosition().getY() - this.getGravityAcceleration());
			this.setVerticalVelocity(-this.getJumpVelocity());
			this.setJumpsLeft(this.getJumpsLeft() - 1);
			
			this.onGround = false;
		}
	}
	
	public int getCollisionCount() {
		int c = this.getCollisions().size();
		
		if (c > 2) {
			return 2;
		}
		
		return c;
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

	public float getMoveVelocity() {
		return moveVelocity;
	}

	public void setMoveVelocity(float moveVelocity) {
		this.moveVelocity = moveVelocity;
	}

	public float getInAirMoveVelocity() {
		return inAirMoveVelocity;
	}

	public void setInAirMoveVelocity(float inAirMoveVelocity) {
		this.inAirMoveVelocity = inAirMoveVelocity;
	}

	public float getHorizontalAcceleration() {
		return horizontalAcceleration;
	}

	public void setHorizontalAcceleration(float horizontalAcceleration) {
		this.horizontalAcceleration = horizontalAcceleration;
	}

	public float getJumpVelocity() {
		return jumpVelocity;
	}

	public void setJumpVelocity(float jumpVelocity) {
		this.jumpVelocity = jumpVelocity;
	}

	public int getMaxJumps() {
		return maxJumps;
	}

	public void setMaxJumps(int maxJumps) {
		this.maxJumps = maxJumps;
	}

	public int getJumpsLeft() {
		return jumpsLeft;
	}

	public void setJumpsLeft(int jumpsLeft) {
		this.jumpsLeft = jumpsLeft;
	}
}
