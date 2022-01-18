package com.connell.colourbattle.graphics.view;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.*;
import com.connell.colourbattle.networking.SocketEvent;
import com.connell.colourbattle.networking.client.SocketClientManager;
import com.connell.colourbattle.utilities.*;

import processing.core.PConstants;

public abstract class View {
	private static ConcurrentLinkedQueue<SocketEvent> pendingEvents = new ConcurrentLinkedQueue<SocketEvent>();
	
	public static void registerViews() {
		RenderingManager.addView(new Menu());
		RenderingManager.addView(new WaitingRoom());
		RenderingManager.addView(new Game());
	}
	
	public static void loadViews() {
		for (View v : RenderingManager.getViews()) {
			v.start();
		}
	}
	
	public static void loadEvents() {
		for (SocketEvent e : getPendingEvents()) {
			SocketClientManager.getClient().listen(e);
		}
	}
	
	public static void addClientSocketEvent(SocketEvent event) {
		getPendingEvents().add(event);
	}
	
	public abstract void start();
	public abstract void render();
	
	protected static Text createStandardText(String body, float scale, Colour colour, Vector2 screenPosition) {
		return new Text(screenPosition, colour, scale, body, RenderingManager.getMainFont(), PConstants.CENTER, PConstants.CENTER);
	}

	protected static InputField createStandardInputField(float scale, Vector2 screenPosition) {
		return new InputField(screenPosition, new Colour(0, 0, 0), new Colour(171, 171, 171), new Colour(255, 255, 255), scale, RenderingManager.getMainFont(), 17);
	}

	protected static ConcurrentLinkedQueue<SocketEvent> getPendingEvents() {
		return pendingEvents;
	}

	protected static void setPendingEvents(ConcurrentLinkedQueue<SocketEvent> pendingEvents) {
		View.pendingEvents = pendingEvents;
	}
}
