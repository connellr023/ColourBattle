package com.connell.colourbattle.graphics.ui.button;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.InputField;
import com.connell.colourbattle.networking.client.SocketClientManager;
import com.connell.colourbattle.networking.server.SocketServerManager;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Constants;
import com.connell.colourbattle.utilities.Vector2;

import processing.core.PFont;

public class CreateServerButton extends JoinServerButton {

	public CreateServerButton(Vector2 screenPosition, float scale, PFont font, InputField input) {
		super(screenPosition, scale, font, input);
		
		this.setBody("Create Server");
		this.setHighlightColour(new Colour(255, 191, 51));
	}
	
	@Override
	public void onClick() {
		String input = this.getInput().getBody();
		
		if (input.length() > 0) {
			int port = Integer.parseInt(input);
			
			try {
				RenderingManager.setActiveViewIndex(1);
				
				SocketServerManager.start(port);
				SocketClientManager.start(Constants.DEFAULT_IP, port);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
