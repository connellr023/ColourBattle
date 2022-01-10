package com.connell.colourbattle.graphics.view;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.InputField;
import com.connell.colourbattle.graphics.ui.Text;
import com.connell.colourbattle.graphics.ui.button.CreateServerButton;
import com.connell.colourbattle.graphics.ui.button.JoinServerButton;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;

public class Menu extends View {
	private Text mainTitle;
	private Text joinTitle;
	private Text hostTitle;
	private Text credit;
	
	private InputField addressInput;
	private InputField portInput; 
	
	private JoinServerButton joinButton;
	private CreateServerButton createButton;
	
	@Override
	public void load() {
		Vector2 screenSize = RenderingManager.getScreenSize();
		Vector2 screenCenter = RenderingManager.getScreenCenter();
		
		float scale = RenderingManager.getScale();
		
		this.mainTitle = createStandardText("COLOUR BATTLE", 80, new Colour(224, 11, 40), new Vector2(screenCenter.getX(), screenCenter.getY() - (5.5f * scale)));
		this.joinTitle = createStandardText("Join Game <IP>:<PORT>", 40, new Colour(255, 255, 255), new Vector2(screenCenter.getX(), screenCenter.getY() - (2.5f * scale)));
		this.hostTitle = createStandardText("Host Game <PORT>", 40, new Colour(255, 255, 255), new Vector2(screenCenter.getX(), screenCenter.getY() + (3.5f * scale)));
		this.credit = createStandardText("© Connell Reffo", 32, new Colour(171, 171, 171), new Vector2(95, (screenSize.getY() * scale) - 25));
		
		this.addressInput = createStandardInputField(1.5f, new Vector2(screenCenter.getX(), screenCenter.getY() - (1.1f * scale)));
		this.portInput = createStandardInputField(1.5f, new Vector2(screenCenter.getX(), screenCenter.getY() + (4.8f * scale)));
		
		this.joinButton = new JoinServerButton(new Vector2(screenCenter.getX(), screenCenter.getY() + (0.5f * scale)), 1.5f, RenderingManager.getMainFont(), addressInput);
		this.createButton = new CreateServerButton(new Vector2(screenCenter.getX(), screenCenter.getY() + (6.4f * scale)), 1.5f, RenderingManager.getMainFont(), portInput);
	}

	@Override
	public void render() {
		this.mainTitle.render();
		this.credit.render();
		
		this.joinTitle.render();
		this.addressInput.render();
		this.joinButton.render();
		
		this.hostTitle.render();
		this.portInput.render();
		this.createButton.render();
	}
}
