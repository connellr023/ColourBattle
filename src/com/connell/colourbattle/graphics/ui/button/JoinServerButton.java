package com.connell.colourbattle.graphics.ui.button;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.InputField;
import com.connell.colourbattle.networking.client.SocketClientManager;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;

import processing.core.PFont;

public class JoinServerButton extends Button {
	private InputField addressInput;
	
	public JoinServerButton(Vector2 screenPosition, float scale, PFont font, InputField input) {
		super(screenPosition, new Colour(0, 0, 0), new Colour(171, 171, 171), new Colour(41, 242, 95), scale, "Join Server", font);
		
		this.setInput(input);
	}
	
	@Override
	public void onClick() {		
		String[] input = this.getInput().getBody().split(":");

		if (input.length > 1) {
			String ip = input[0];
			int port = Integer.parseInt(input[1]);

			RenderingManager.setActiveViewIndex(1);
			SocketClientManager.start(ip, port);
		}
	}

	public InputField getInput() {
		return addressInput;
	}

	public void setInput(InputField addressInput) {
		this.addressInput = addressInput;
	}
}
