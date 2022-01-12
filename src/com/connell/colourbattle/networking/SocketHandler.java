package com.connell.colourbattle.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public abstract class SocketHandler implements Runnable {
	private Socket clientSocket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private LinkedList<SocketEvent> socketEvents;
	
	public SocketHandler() {
		this.setSocketEvents(new LinkedList<SocketEvent>());
		
		this.start();
	}
	
	public abstract void start();
	public abstract void stop();
	
	public void listen(SocketEvent event) {
		this.getSocketEvents().add(event);
	}
	
	protected void initIOStream() {
		try {			
			this.setOut(new PrintWriter(this.getClientSocket().getOutputStream(), true));
			this.setIn(new BufferedReader(new InputStreamReader(this.getClientSocket().getInputStream())));
		}
		catch (Exception e) {
			System.out.println("Failed to Initialize I/O Socket Stream");
		}
	}
	
	public void handleDataReceival() throws IOException {
		String recvData;
		
		if (!(recvData = this.getIn().readLine()).equals(null)) {
			Packet message = Packet.decode(recvData);
			
			if (!message.getData().equals("")) {
				for (SocketEvent event : this.getSocketEvents()) {
					if (message.getEvent().equals(event.getEvent())) {
						event.call(message.getData());
						break;
					}
				}
			}
		}
	}
	
	public abstract void handleDisconnect();
	
	public void sendData(Packet message) {
		try {
			this.getOut().println(message.encode());
		}
		catch (Exception e) {
			System.out.println("Failed to Send Data");
			this.stop();
		}
	}
	
	public BufferedReader getIn() {
		return in;
	}
	
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	
	public PrintWriter getOut() {
		return out;
	}
	
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}
	
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	private LinkedList<SocketEvent> getSocketEvents() {
		return socketEvents;
	}

	private void setSocketEvents(LinkedList<SocketEvent> socketEvents) {
		this.socketEvents = socketEvents;
	}
}
