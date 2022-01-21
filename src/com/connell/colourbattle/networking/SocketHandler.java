package com.connell.colourbattle.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SocketHandler implements Runnable {
	private Socket clientSocket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private ConcurrentHashMap<String, SocketEvent> socketEvents;
	
	/**
	 * Represents a socket object that is able to send and receive data
	 */
	public SocketHandler() {
		this.setSocketEvents(new ConcurrentHashMap<String, SocketEvent>());
		
		this.start();
	}
	
	/**
	 * A method that can be used for initialization
	 */
	public abstract void start();
	
	/**
	 * A method used to stop the socket
	 */
	public abstract void stop();
	
	/**
	 * Used to register socket events to listen for on this socket
	 * @param event The event to watch for
	 */
	public void listen(SocketEvent event) {
		String eventName = event.getEvent();
		
		this.getSocketEvents().put(eventName, event);
	}
	
	/**
	 * Initializes the input and output stream for this socket
	 */
	protected void initIOStream() {
		try {			
			this.setOut(new PrintWriter(this.getClientSocket().getOutputStream(), true));
			this.setIn(new BufferedReader(new InputStreamReader(this.getClientSocket().getInputStream())));
		}
		catch (Exception e) {
			System.out.println("Failed to Initialize I/O Socket Stream");
		}
	}
	
	/**
	 * Watches for incoming packets
	 * @throws IOException If the input stream fails to read the socket
	 */
	public void handleDataReceival() throws IOException {
		String recvData;
		
		if (!(recvData = this.getIn().readLine()).equals(null)) {
			Packet message = Packet.decode(recvData);
			String eventName = message.getEvent();
			SocketEvent event = this.getSocketEvents().get(eventName);

			if (event != null) {
				event.call(message.getData());
			}
		}
	}
	
	/**
	 * Method that is executed when a disconnect occurs
	 */
	public abstract void handleDisconnect();
	
	/**
	 * Writes a message to the socket
	 * @param message The message to send
	 */
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

	private ConcurrentHashMap<String, SocketEvent> getSocketEvents() {
		return socketEvents;
	}

	private void setSocketEvents(ConcurrentHashMap<String, SocketEvent> socketEvents) {
		this.socketEvents = socketEvents;
	}
}
