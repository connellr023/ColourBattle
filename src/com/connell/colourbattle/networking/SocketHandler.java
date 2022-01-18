package com.connell.colourbattle.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public abstract class SocketHandler implements Runnable {
	private Socket clientSocket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private HashMap<String, SocketEvent> socketEvents;
	
	public SocketHandler() {
		this.setSocketEvents(new HashMap<String, SocketEvent>());
		
		this.start();
	}
	
	public abstract void start();
	public abstract void stop();
	
	public void listen(SocketEvent event) {
		String eventName = event.getEvent();
		
		this.getSocketEvents().put(eventName, event);
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
			String eventName = message.getEvent();
			
			SocketEvent event = this.getSocketEvents().get(eventName);
			event.call(message.getData());
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

	private HashMap<String, SocketEvent> getSocketEvents() {
		return socketEvents;
	}

	private void setSocketEvents(HashMap<String, SocketEvent> socketEvents) {
		this.socketEvents = socketEvents;
	}
}
