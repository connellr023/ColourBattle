package com.connell.colourbattle.networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

public class ServerSocketStream implements Runnable {
	private ServerSocket serverSocket;
	private LinkedList<RoomHandler> rooms;
	
	private int maxRoomCount;
	private int port;
	
	public ServerSocketStream(int port, int maxRoomCount) throws IOException {
		this.setRooms(new LinkedList<RoomHandler>());
		this.setMaxRoomCount(maxRoomCount);
		this.setPort(port);
	}
	
	@Override
	public void run() {
		try {			
			this.setServerSocket(new ServerSocket(this.getPort()));
			
			System.out.println("Started New Socket Server on Port: " + this.getServerSocket().getLocalPort());
			
			while (!this.getServerSocket().equals(null)) {
				this.handleClientAcceptions();
			}
			
			this.stop();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleClientAcceptions() throws IOException {
		int availableRoomCounter = 0;
		
		for (RoomHandler room : this.getRooms()) {
			room.handleClientConnections();
			
			if (!room.isFull() && availableRoomCounter <= 0) {
				room.acceptClient(this.getServerSocket().accept());
				
				availableRoomCounter++;
			}
		}
		
		if (availableRoomCounter <= 0) {			
			this.openNewRoomAndAcceptClient();
			System.out.println(this.getRoomCount() + " Active Room(s) Currently");
		}
	}
	
	private void openNewRoomAndAcceptClient() throws IOException {
		if (this.getRoomCount() < this.getMaxRoomCount()) {			
			RoomHandler room = new RoomHandler(2);
			this.getRooms().add(room);
			
			room.acceptClient(this.getServerSocket().accept());
		}
	}
	
	public void stop() throws IOException {
		this.getServerSocket().close();
		
		for (RoomHandler room : this.getRooms()) {
			room.stop();
		}
	}
	
	public int getRoomCount() {
		return this.getRooms().size();
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	public LinkedList<RoomHandler> getRooms() {
		return rooms;
	}

	public void setRooms(LinkedList<RoomHandler> rooms) {
		this.rooms = rooms;
	}

	public int getMaxRoomCount() {
		return maxRoomCount;
	}

	public void setMaxRoomCount(int maxRoomCount) {
		this.maxRoomCount = maxRoomCount;
	}
}
