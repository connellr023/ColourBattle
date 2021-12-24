package com.connell.colourbattle.graphics.ui;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.button.Button;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;
import processing.core.PFont;

public class InputField extends Button {
	private int maxLength;
	
	public InputField(Vector2 screenPosition, Colour textColour, Colour colour, Colour highlightColour, float scale, PFont font, int maxLength) {
		super(screenPosition, textColour, colour, highlightColour, scale, "", font);
		
		this.setMaxLength(maxLength);
	}
	
	@Override
	protected void whileSelected() {
		RenderingManager r = this.getRenderer();
		String body = this.getBody();
		
		int kc = RenderingManager.getLastKeyCode();
		
		if (r.keyPressed && kc >= 0 && !(kc == 10 || kc == 16)) {
			RenderingManager.setLastKeyCode(-1);
			//System.out.println(kc);

			if ((kc == 8 || kc == 127)) {
				String v = ((body.length() <= 2) ? "" : body.substring(0, body.length() - 1));
				
				this.setBody(v);
			}
			else if (body.length() < this.getMaxLength()) {				
				this.setBody(body + r.key);
			}
			
			this.setWidth();
		}
	}
	
	@Override
	public void onClick() {}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
}
