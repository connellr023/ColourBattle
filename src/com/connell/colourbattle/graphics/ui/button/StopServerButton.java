package com.connell.colourbattle.graphics.ui.button;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.networking.server.SocketServerManager;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;

import processing.core.PFont;

public class StopServerButton extends JoinServerButton {

	public StopServerButton(Vector2 screenPosition, String body, float scale, PFont font) {
		super(screenPosition, scale, font, null);
		
		this.setBody(body);
		this.setHighlightColour(new Colour(240, 34, 58));
	}
	
	@Override
	public void onClick() {
		SocketServerManager.stopServer();
		RenderingManager.setActiveViewIndex(0);
	}
}
