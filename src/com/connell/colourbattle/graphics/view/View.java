package com.connell.colourbattle.graphics.view;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.*;
import com.connell.colourbattle.networking.SocketEvent;
import com.connell.colourbattle.networking.client.SocketClientManager;
import com.connell.colourbattle.utilities.*;

import processing.core.PConstants;

public abstract class View {
	
	/**
	 * A list of pending client socket events to be registered after the client socket connection is established
	 */
	private static ConcurrentLinkedQueue<SocketEvent> pendingEvents = new ConcurrentLinkedQueue<SocketEvent>();
	
	/**
	 * Initializes all of the various views in the game before it starts
	 */
	public static void registerViews() {
		RenderingManager.addView(new Menu());
		RenderingManager.addView(new WaitingRoom());
		RenderingManager.addView(new Game());
	}
	
	/**
	 * Loads the views just as the game starts
	 */
	public static void loadViews() {
		for (View v : RenderingManager.getViews()) {
			v.start();
		}
	}
	
	/**
	 * Loads the pending socket events after the client socket is ready
	 */
	public static void loadEvents() {
		for (SocketEvent e : getPendingEvents()) {
			SocketClientManager.getClient().listen(e);
		}
	}
	
	/**
	 * Allows different view objects to add to the list of pending socket events
	 * @param event The event to add
	 */
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
