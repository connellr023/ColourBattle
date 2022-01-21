package com.connell.colourbattle.graphics.ui;

import com.connell.colourbattle.graphics.RenderableObject;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;

public abstract class UserInterface extends RenderableObject {
	private Vector2 screenPosition;
	private Colour colour;
	
	private float scale;
	
	/**
	 * Represents an object that acts as part of the game's user interface
	 * @param screenPosition The position to render this at
	 * @param colour The colour it should be
	 * @param scale How large this should be displayed
	 */
	public UserInterface(Vector2 screenPosition, Colour colour, float scale) {
		this.setScreenPosition(screenPosition);
		this.setColour(colour);
		this.setScale(scale);
	}

	public Vector2 getScreenPosition() {
		return screenPosition;
	}

	public void setScreenPosition(Vector2 screenPosition) {
		this.screenPosition = screenPosition;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
