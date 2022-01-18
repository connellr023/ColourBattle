package com.connell.colourbattle.networking.server.game;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

import com.connell.colourbattle.networking.Packet;
import com.connell.colourbattle.networking.server.RoomHandler;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Constants;
import com.connell.colourbattle.utilities.Hitbox;
import com.connell.colourbattle.utilities.Vector2;

public class GameManager implements Runnable {
	private ConcurrentLinkedQueue<ServerGameObject> gameObjects;
	private ConcurrentLinkedQueue<Platform> level;
	private ConcurrentLinkedQueue<Player> players;
	
	private Vector2 gameSize;
	
	private boolean isRunning;
	
	private RoomHandler parentRoom;
	
	private int ticksPerSecond;
	private int tickCount;
	
	private int timeLeft;
	
	public GameManager(RoomHandler parentRoom, int startingTime) {
		this.setTicksPerSecond(50);
		
		this.setParentRoom(parentRoom);
		this.setTimeLeft(startingTime);
		this.setGameSize(Constants.GAME_SIZE);
		
		this.setTicksPerSecond(ticksPerSecond);
		this.setTickCount(0);
		
		this.setGameObjects(new ConcurrentLinkedQueue<ServerGameObject>());
		this.setLevel(new ConcurrentLinkedQueue<Platform>());
		this.setPlayers(new ConcurrentLinkedQueue<Player>());
	}
	
	@Override
	public void run() {
		try {
			this.start();
			this.loadGameObjects();
			
			this.setRunning(true);
			
			while (this.isRunning()) {
				this.update();			
				Thread.sleep((1000 / ticksPerSecond));
				
				this.setTickCount(this.getTickCount() + 1);
			}
		}
		catch (InterruptedException e) {
			this.setRunning(false);
			e.printStackTrace();
		}
	}
	
	public void stop() {
		this.setRunning(false);
		
		this.getGameObjects().clear();
		this.getLevel().clear();
		this.getPlayers().clear();
	}
	
	private void loadGameObjects() {
		for (ServerGameObject object : this.getGameObjects()) {
			object.start();
		}
	}

	private void start() {
		this.generateLevel(25, 30);
	}
	
	private void generateLevel(int minPlatforms, int maxPlatforms) {
		Vector2 gameSize = this.getGameSize();
		Platform floor = new Platform(this, new Vector2(0, gameSize.getY()), new Hitbox(new Vector2(0, 0), new Vector2(gameSize.getX(), 2)), 0.9f);
		
		this.addPlatform(floor);
		
		int count = ThreadLocalRandom.current().nextInt(minPlatforms, maxPlatforms + 1);
		
		for (int i = 0; i < count; i++) {
			Platform p = Platform.random(this, 3, 5, 3, 4, 0.85f);
			
			if (!p.doesIntersect()) {				
				this.addPlatform(p);
			}
		}
		
		for (int i = 0; i < this.getParentRoom().getClientCount(); i++) {
			this.addPlayer(new Player(this, new Colour(255, 0, 240)));
		}
	}
	
	private void update() {
		this.updateGameObjects();
		this.updateTimer();
	}
	
	private void updateGameObjects() {
		for (ServerGameObject object : this.getGameObjects()) {
			object.update();
		}
	}
	
	private void updateTimer() {
		RoomHandler room = this.getParentRoom();
		
		if (this.getTickCount() % this.getTicksPerSecond() == 0) {
			if (this.getTimeLeft() == 0) {
				room.sendDataToAll(new Packet("game_over"));
				room.stop();
			}
			
			room.sendDataToAll(new Packet("update_timer", this.getTimeLeft() + ""));
			this.setTimeLeft(this.getTimeLeft() - 1);
		}
	}
	
	private void addPlatform(Platform platform) {
		this.getGameObjects().add(platform);
		this.getLevel().add(platform);
	}
	
	private void addPlayer(Player player) {
		this.getGameObjects().add(player);
		this.getPlayers().add(player);
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public int getTicksPerSecond() {
		return ticksPerSecond;
	}

	public void setTicksPerSecond(int ticksPerSecond) {
		this.ticksPerSecond = ticksPerSecond;
	}

	public int getTickCount() {
		return tickCount;
	}

	public void setTickCount(int tickCount) {
		this.tickCount = tickCount;
	}

	public RoomHandler getParentRoom() {
		return parentRoom;
	}

	public void setParentRoom(RoomHandler parentRoom) {
		this.parentRoom = parentRoom;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public ConcurrentLinkedQueue<ServerGameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(ConcurrentLinkedQueue<ServerGameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}

	public Vector2 getGameSize() {
		return gameSize;
	}

	public void setGameSize(Vector2 gameSize) {
		this.gameSize = gameSize;
	}

	public ConcurrentLinkedQueue<Platform> getLevel() {
		return level;
	}

	public void setLevel(ConcurrentLinkedQueue<Platform> level) {
		this.level = level;
	}

	public ConcurrentLinkedQueue<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ConcurrentLinkedQueue<Player> players) {
		this.players = players;
	}
}
