package com.connell.colourbattle.graphics.ui.button;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.networking.client.SocketClientManager;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;

import processing.core.PFont;

public class BackHomeButton extends StopServerButton {

	public BackHomeButton(Vector2 screenPosition, String body, float scale, PFont font) {
		super(screenPosition, body, scale, font);
		
		this.setHighlightColour(new Colour(147, 86, 232));
	}

	@Override
	public void onClick() {
		RenderingManager.setActiveViewIndex(0);
		SocketClientManager.stopClient();
	}
}
