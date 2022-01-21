package com.connell.colourbattle.graphics.ui.button;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.UserInterface;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;

import processing.core.PConstants;
import processing.core.PFont;

public abstract class Button extends UserInterface {	
	private String body;
	private PFont font;
	
	private Colour textColour;
	private Colour highlightColour;
	
	private float width;
	private float height;
	
	private float minWidth;
	private float charSize;
	
	private boolean isSelected;
	
	private boolean justClicked;
	
	/**
	 * Represents a button user interface element
	 * @param screenPosition The position this should be on the screen
	 * @param textColour The colour of the button inner text
	 * @param colour The colour of the overall button
	 * @param highlightColour The colour of the button when being highlighted
	 * @param scale How big the button is
	 * @param body The inner text of the button
	 * @param font The font the text should be in
	 */
	public Button(Vector2 screenPosition, Colour textColour, Colour colour, Colour highlightColour, float scale, String body, PFont font) {
		super(screenPosition, colour, scale);
		
		this.setMinWidth(4.3f);
		this.setCharSize(0.86f);
		
		this.setJustClicked(false);
		
		this.setBody(body);
		this.setFont(font);
		
		this.setTextColour(textColour);
		this.setHighlightColour(highlightColour);
		
		this.setWidth();
		this.setHeight();
	}

	@Override
	public void render() {
		RenderingManager r = this.getRenderer();
		Vector2 center = this.getCenter();
		
		Colour tc = this.getTextColour();
		Colour c = this.getColour();
		Colour hc = this.getHighlightColour();
		Colour bc = (this.isMouseHovering() ? hc : c);
		
		float scale = RenderingManager.getScale();
		float width = this.getWidth() * scale;
		float height = this.getHeight() * scale;
		
		r.fill(bc.r, bc.g, bc.b);
		r.noStroke();
		r.rectMode(PConstants.CENTER);
		r.rect(center.getX(), center.getY() + ((height) / 4), width, height);
		
		r.fill(tc.r, tc.g, tc.b);
		r.textFont(this.getFont());
		r.textAlign(PConstants.CENTER, PConstants.CENTER);
		r.textSize(this.getScale() * scale);
		r.text(this.getBody(), center.getX(), center.getY());
		
		if (this.isBeingClicked()) {
			if (!this.isJustClicked()) {				
				this.onClick();
				this.setJustClicked(true);
			}
		}
		
		if (this.isSelected()) {
			this.whileSelected();
		}
	}
	
	/**
	 * Executes every frame this button is hovered over
	 */
	protected void whileSelected() {}
	
	/**
	 * Checks if the user's mouse pointer is hovering over this button
	 */
	protected boolean isMouseHovering() {
		Vector2 mouse = new Vector2(this.getRenderer().mouseX, this.getRenderer().mouseY - 7);
		Vector2 center = this.getCenter();
		
		float scale = RenderingManager.getScale();
		float width = this.getWidth() * scale;
		float height = this.getHeight() * scale;

		if (mouse.getX() >= center.getX() - (width / 2) && mouse.getX() <= center.getX() + (width / 2)) {
			if (mouse.getY() >= center.getY() - (height / 2) && mouse.getY() <= center.getY() + (height / 2)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if the user clicked on this button
	 */
	private boolean isBeingClicked() {
		if (this.getRenderer().mousePressed) {
			if (this.isMouseHovering()) {
				this.setSelected(true);
				
				return true;
			}
			else {
				this.setSelected(false);
			}
		}
		
		this.setJustClicked(false);
		return false;
	}
	
	/**
	 * Executes upon this being clicked
	 */
	public abstract void onClick();

	protected void setWidth() {
		final float MIN = this.getMinWidth();
		float w = this.getBody().length();
		
		w *= this.getCharSize() * 0.6f;
		
		if (w < MIN) {
			w = MIN;
		}
		
		this.width =  w * this.getScale();
	}
	
	protected float getWidth() {
		return width;
	}
	
	private void setHeight() {
		this.height = this.getCharSize() * this.getScale();
	}
	
	protected float getHeight() {
		return height;
	}

	protected Vector2 getCenter() {
		return new Vector2(this.getScreenPosition().getX() + (width / 2), this.getScreenPosition().getY() + (height / 2));
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public PFont getFont() {
		return font;
	}

	public void setFont(PFont font) {
		this.font = font;
	}
	
	public Colour getTextColour() {
		return textColour;
	}

	public void setTextColour(Colour textColour) {
		this.textColour = textColour;
	}

	public Colour getHighlightColour() {
		return highlightColour;
	}

	public void setHighlightColour(Colour highlightColour) {
		this.highlightColour = highlightColour;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	private boolean isJustClicked() {
		return justClicked;
	}

	private void setJustClicked(boolean justClicked) {
		this.justClicked = justClicked;
	}

	public float getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(float minWidth) {
		this.minWidth = minWidth;
	}

	public float getCharSize() {
		return charSize;
	}

	public void setCharSize(float charSize) {
		this.charSize = charSize;
	}
}
