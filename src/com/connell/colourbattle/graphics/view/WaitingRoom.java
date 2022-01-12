package com.connell.colourbattle.graphics.view;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.Text;
import com.connell.colourbattle.graphics.ui.button.StopServerButton;
import com.connell.colourbattle.networking.SocketEvent;
import com.connell.colourbattle.networking.client.SocketClientManager;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.Vector2;

public class WaitingRoom extends View {
	private Text mainMessage;
	private Text failMessage;
	
	private StopServerButton stopButton;

	private int currentClientCount;
	private int maxClientCount;

	@Override
	public void load() {
		Vector2 screenCenter = RenderingManager.getScreenCenter();
		float scale = RenderingManager.getScale();
		
		this.setCurrentClientCount(1);
		this.setMaxClientCount(1);
		
		this.mainMessage = createStandardText(this.generateMainMessage(), 50, new Colour(255, 255, 255), screenCenter);
		this.failMessage = createStandardText("Failed To Connect", 50, new Colour(255, 0, 0), screenCenter);
		
		this.stopButton = new StopServerButton(new Vector2(screenCenter.getX(), screenCenter.getY() + (2 * scale)), "Cancel", 1.6f, RenderingManager.getMainFont());
		
		addClientSocketEvent(new SocketEvent("max_client_count", this) {
			@Override
			public void call(String data) {
				WaitingRoom r = (WaitingRoom) this.getObject();
				r.setMaxClientCount(Integer.parseInt(data));
			}
		});
		
		addClientSocketEvent(new SocketEvent("update_current_client_count", this) {
			@Override
			public void call(String data) {
				WaitingRoom r = (WaitingRoom) this.getObject();
				r.setCurrentClientCount(Integer.parseInt(data));
			}
		});
	}

	@Override
	public void render() {
		if (SocketClientManager.getClient().isRunning()) {
			this.mainMessage.setBody(this.generateMainMessage());
			this.mainMessage.render();
		}
		else {
			this.failMessage.render();
		}
		
		this.stopButton.render();
	}
	
	private String generateMainMessage() {
		return "Waiting For Player(s) [" + this.getCurrentClientCount() + "/" + this.getMaxClientCount() + "]...";
	}

	public int getCurrentClientCount() {
		return currentClientCount;
	}

	public void setCurrentClientCount(int currentClientCount) {
		this.currentClientCount = currentClientCount;
	}

	public int getMaxClientCount() {
		return maxClientCount;
	}

	public void setMaxClientCount(int maxClientCount) {
		this.maxClientCount = maxClientCount;
	}
}
