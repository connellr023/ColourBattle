package com.connell.colourbattle.graphics.view;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.Text;
import com.connell.colourbattle.networking.SocketEvent;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;

public class Game extends View {
	private Text timerText;
	
	private int timeLeft;
	
	@Override
	public void load() {
		Vector2 screenCenter = RenderingManager.getScreenCenter();
		float scale = RenderingManager.getScale();
		
		this.timerText = createStandardText("", 23, new Colour(50, 255, 50), new Vector2(screenCenter.getX(), 1.2f * scale));
		
		addClientSocketEvent(new SocketEvent("update_timer", this) {
			@Override
			public void call(String data) {
				Game g = (Game) this.getObject();
				g.setTimeLeft(Integer.parseInt(data));
			}
		});
	}

	@Override
	public void render() {
		this.timerText.setBody("Time Left: " + this.getTimeLeft());
		this.timerText.render();
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
}
