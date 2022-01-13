package com.connell.colourbattle.networking.server.game;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.connell.colourbattle.networking.Packet;
import com.connell.colourbattle.networking.server.RoomHandler;

public class GameManager implements Runnable {
	private ConcurrentLinkedQueue<ServerGameObject> gameObjects;
	
	private boolean isRunning;
	
	private RoomHandler parentRoom;
	
	private int ticksPerSecond;
	private int tickCount;
	
	private int timeLeft;
	
	public GameManager(RoomHandler parentRoom, int ticksPerSecond, int startingTime) {
		this.setParentRoom(parentRoom);
		this.setTimeLeft(startingTime);
		
		this.setTicksPerSecond(ticksPerSecond);
		this.setTickCount(0);
		
		this.setGameObjects(new ConcurrentLinkedQueue<ServerGameObject>());
	}
	
	@Override
	public void run() {
		this.setRunning(true);
		
		try {
			this.loadGameObjects();
			
			while (this.isRunning()) {
				this.update();			
				Thread.sleep((1000 / ticksPerSecond));
				
				this.setTickCount(this.getTickCount() + 1);
			}
		} catch (InterruptedException e) {
			this.setRunning(false);
			e.printStackTrace();
		}
	}
	
	private void loadGameObjects() {
		for (ServerGameObject object : this.getGameObjects()) {
			object.start();
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
}
