package com.connell.colourbattle.networking;

public class SocketEvent {
	private String event;
	private Object object;
	
	public SocketEvent(String event, Object object) {
		this.setEvent(event);
		this.setObject(object);
	}
	
	public SocketEvent(String event) {
		this(event, null);
	}
	
	public void call(String data) {
		System.out.println(data);
	}
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
}
