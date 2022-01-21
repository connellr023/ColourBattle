package com.connell.colourbattle.networking;

public class Packet {
	private static final char SPLIT_CHAR = ':';
	
	private String event;
	private String data;
	
	/**
	 * Represents data that can be sent between the client and server
	 * @param event The name of the event
	 * @param data The data to be sent
	 */
	public Packet(String event, String data) {
		this.setEvent(event);
		this.setData(data);
	}
	
	public Packet(String event) {
		this(event, "");
	}
	
	public Packet() {
		this("", "");
	}
	
	/**
	 * Turns the packet into a string that can be sent
	 */
	public String encode() {
		return event + SPLIT_CHAR + data;
	}
	
	/**
	 * Converts a packet string into a packet object
	 * @param message The packet string to be decoded
	 */
	public static Packet decode(String message) {
		String[] splitMessage = message.split("" + SPLIT_CHAR);

		String event = splitMessage[0];
		
		if (splitMessage.length > 1) {			
			return new Packet(event, splitMessage[1]);
		}
		else if (splitMessage.length == 1) {
			return new Packet(event, splitMessage[0]);
		}
		
		return new Packet();
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
