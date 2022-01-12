package com.connell.colourbattle.networking.server.game;

import java.util.LinkedList;

import com.connell.colourbattle.networking.Packet;
import com.connell.colourbattle.networking.server.RoomHandler;

public class GameManager implements Runnable {
	private LinkedList<Player> players;
	
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
		
		this.setPlayers(new LinkedList<Player>());
	}
	
	@Override
	public void run() {
		this.setRunning(true);
		
		try {
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
	
	private void update() {
		this.updateTimer();
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

	public LinkedList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(LinkedList<Player> players) {
		this.players = players;
	}
}
