package com.connell.colourbattle.networking;

public class Packet {
	private static final char SPLIT_CHAR = ':';
	
	private String event;
	private String data;
	
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
	
	public String encode() {
		return event + SPLIT_CHAR + data;
	}
	
	public static Packet decode(String message) {
		String[] splitMessage = message.split("" + SPLIT_CHAR);

		if (splitMessage.length > 1) {			
			return new Packet(splitMessage[0], splitMessage[1]);
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
