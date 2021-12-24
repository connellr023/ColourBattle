package com.connell.colourbattle.networking.server;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import com.connell.colourbattle.networking.Packet;

public class RoomHandler {
	private LinkedList<ClientHandler> clients;
	
	private int maxClientCount;
	
	public RoomHandler(int maxClientCount) {
		this.setClients(new LinkedList<ClientHandler>());
		
		this.setMaxPlayerCount(maxClientCount);
	}
	
	public void acceptClient(Socket clientSocket) {
		ClientHandler client = new ClientHandler(clientSocket, this);
		this.getClients().add(client);
		
		client.sendData(new Packet("max_client_count", this.getClientPlayerCount() + ""));
		this.sendDataToAll(new Packet("update_current_client_count", this.getClientCount() + ""));
		
		if (this.getClientCount() >= this.getClientPlayerCount()) {
			this.sendDataToAll(new Packet("game_start"));
		}
		
		System.out.println(this.getClientCount() + " Client(s) in Room");
	}
	
	public void handleClientConnections() throws IOException {
		for (ClientHandler client : this.getClients()) {
			client.handleDataReceival();
		}
	}
	
	public void sendDataToAll(Packet message) {
		for (ClientHandler client : this.getClients()) {
			try {				
				client.sendData(message);
			}
			catch (Exception e) {
				System.out.println("Failed to Send Data");
			}
		}
	}
	
	public void stop() throws IOException {
		for (ClientHandler client : this.getClients()) {
			client.stop();
		}
	}
	
	public boolean isFull() {
		return (this.getClientCount() >= this.getClientPlayerCount());
	}
	
	public int getClientCount() {
		return this.getClients().size();
	}

	public LinkedList<ClientHandler> getClients() {
		return clients;
	}

	public void setClients(LinkedList<ClientHandler> clients) {
		this.clients = clients;
	}

	public int getClientPlayerCount() {
		return maxClientCount;
	}

	public void setMaxPlayerCount(int maxPlayerCount) {
		this.maxClientCount = maxPlayerCount;
	}
}
