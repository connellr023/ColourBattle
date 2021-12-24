package com.connell.colourbattle.graphics.ui;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;

import processing.core.PFont;

public class Text extends UserInterface {
	private String body;
	private PFont font;
	
	private int textAlignX;
	private int textAlignY;
	
	public Text(Vector2 screenPosition, Colour colour, float scale, String body, PFont font, int textAlignX, int textAlignY) {
		super(screenPosition, colour, scale);
		
		this.setBody(body);
		this.setFont(font);
		
		this.setTextAlignX(textAlignX);
		this.setTextAlignY(textAlignY);
	}
	
	@Override
	public void render() {
		RenderingManager r = this.getRenderer();
		
		r.fill(this.getColour().r, this.getColour().g, this.getColour().b);
		r.textAlign(this.getTextAlignX(), this.getTextAlignY());
		r.textFont(this.getFont());
		r.textSize(this.getScale());
		r.text(this.getBody(), this.getScreenPosition().getX(), this.getScreenPosition().getY());
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setFont(PFont font) {
		this.font = font;
	}
	
	public PFont getFont() {
		return font;
	}
	
	public void setTextAlignX(int textAlignX) {
		this.textAlignX = textAlignX;
	}
	
	public int getTextAlignX() {
		return textAlignX;
	}
	
	public void setTextAlignY(int textAlignY) {
		this.textAlignY = textAlignY;
	}
	
	public int getTextAlignY() {
		return textAlignY;
	}
}
