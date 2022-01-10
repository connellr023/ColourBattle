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
			
			while (!this.getServerSocket().isClosed()) {
				this.handleClientAcceptions();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleClientAcceptions() {
		try {			
			for (RoomHandler room : this.getRooms()) {
				if (!room.isFull()) {
					room.acceptClient(this.getServerSocket().accept());
					return;
				}
			}
			
			this.openNewRoomAndAcceptClient();
		}
		catch (Exception e) {
			System.out.println("Failed to Accept Client");
		}
	}
	
	private void openNewRoomAndAcceptClient() throws IOException {
		if (this.getRoomCount() < this.getMaxRoomCount()) {
			int maxClientCount = 2;
			
			RoomHandler room = new RoomHandler(maxClientCount);
			this.getRooms().add(room);
			
			room.acceptClient(this.getServerSocket().accept());
			
			System.out.println(this.getRoomCount() + " Active Room(s) Currently");
		}
	}
	
	public void stop() {
		try {			
			for (RoomHandler room : this.getRooms()) {
				room.stop();
			}
			
			this.getServerSocket().close();
			
			System.out.println("Closed Socket Server");
		}
		catch (Exception e) {
			System.out.println("Failed to Stop Socket Server");
			e.printStackTrace();
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
