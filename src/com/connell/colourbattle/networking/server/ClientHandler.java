package com.connell.colourbattle.networking.server;

import java.net.Socket;

import com.connell.colourbattle.networking.SocketEvent;
import com.connell.colourbattle.networking.SocketHandler;

public class ClientHandler extends SocketHandler {
	private RoomHandler parentRoom;
	
	public ClientHandler(Socket clientSocket, RoomHandler parentRoom) {
		super();
		
		this.setClientSocket(clientSocket);
		this.setParentRoom(parentRoom);
		this.initIOStream();
	}
	
	@Override
	public void start() {
		this.listen(new SocketEvent("console_message") {
			@Override
			public void call(String data) {
				System.out.println("Server Received: " + data);
			}
		});
	}

	@Override
	public void stop() {
		try {			
			this.getIn().close();
			this.getOut().close();
			
			this.getClientSocket().close();
			this.getParentRoom().getClients().remove(this);
		}
		catch (Exception e) {
			System.out.println("Failed to Stop Client");
		}
	}

	public RoomHandler getParentRoom() {
		return parentRoom;
	}

	public void setParentRoom(RoomHandler parentRoom) {
		this.parentRoom = parentRoom;
	}
}
