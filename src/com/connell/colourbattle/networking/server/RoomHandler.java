package com.connell.colourbattle.networking.server;

import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.connell.colourbattle.networking.Packet;
import com.connell.colourbattle.networking.server.game.GameManager;

public class RoomHandler {
	private LinkedList<ClientHandler> clients;
	private ExecutorService clientPool;
	
	private GameManager game;
	private Thread gameThread;
	
	private int maxClientCount;
	
	public RoomHandler(int maxClientCount) {
		this.setMaxClientCount(maxClientCount);
		
		this.setClients(new LinkedList<ClientHandler>());
		this.setClientPool(Executors.newFixedThreadPool(this.getMaxClientCount()));
	}
	
	public void acceptClient(Socket clientSocket) {
		ClientHandler client = new ClientHandler(clientSocket, this);
		this.getClients().add(client);
		
		clientPool.execute(client);

		client.sendData(new Packet("max_client_count", this.getMaxClientCount() + ""));
		this.sendDataToAll(new Packet("update_current_client_count", this.getClientCount() + ""));
		
		System.out.println(this.getClientCount() + " Client(s) in Room");
		
		if (this.getClientCount() >= this.getMaxClientCount()) {
			this.sendDataToAll(new Packet("game_start"));
			
			this.setGame(new GameManager(this, 5, 60));
			this.setGameThread(new Thread(this.getGame()));
			
			this.getGameThread().start();
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
	
	public void onClientDisconnect() {
		int clientCount = this.getClientCount();
		
		if (clientCount <= 1) {
			SocketServerManager.stopServer();
		}
	}
	
	public void stop() {
		ServerSocketStream server = SocketServerManager.getServer();
		
		this.getGame().stop();
		
		for (ClientHandler client : this.getClients()) {
			client.stop();
		}
		
		server.getRooms().remove(this);
		
		System.out.println("Closed Room (" + server.getRoomCount() + " Room(s) Active)");
	}
	
	public boolean isFull() {
		return (this.getClientCount() >= this.getMaxClientCount());
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

	public int getMaxClientCount() {
		return maxClientCount;
	}

	public void setMaxClientCount(int maxClientCountCount) {
		this.maxClientCount = maxClientCountCount;
	}

	public ExecutorService getClientPool() {
		return clientPool;
	}

	public void setClientPool(ExecutorService clientPool) {
		this.clientPool = clientPool;
	}

	public GameManager getGame() {
		return game;
	}

	public void setGame(GameManager game) {
		this.game = game;
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}	
}
