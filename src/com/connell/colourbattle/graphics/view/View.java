package com.connell.colourbattle.graphics.view;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.*;
import com.connell.colourbattle.utilities.*;

import processing.core.PConstants;

public abstract class View {
	public static void initViews() {
		RenderingManager.addView(new Menu());
		RenderingManager.addView(new WaitingRoom());
	}
	
	public static void loadViews() {
		for (View v : RenderingManager.getViews()) {
			v.load();
		}
	}
	
	public abstract void load();
	public abstract void render();
	
	protected static Text createStandardText(String body, float scale, Colour colour, Vector2 screenPosition) {
		return new Text(screenPosition, colour, scale, body, RenderingManager.getMainFont(), PConstants.CENTER, PConstants.CENTER);
	}

	protected static InputField createStandardInputField(float scale, Vector2 screenPosition) {
		return new InputField(screenPosition, new Colour(0, 0, 0), new Colour(171, 171, 171), new Colour(255, 255, 255), scale, RenderingManager.getMainFont(), 17);
	}
}
