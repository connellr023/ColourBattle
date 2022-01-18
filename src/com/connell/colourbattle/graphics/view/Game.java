package com.connell.colourbattle.graphics.view;

import java.util.concurrent.ConcurrentHashMap;

import com.connell.colourbattle.graphics.ClientGameObject;
import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.Text;
import com.connell.colourbattle.networking.SocketEvent;
import com.connell.colourbattle.networking.server.game.ServerGameObject;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.GameObject;
import com.connell.colourbattle.utilities.Vector2;

public class Game extends View {
	private ConcurrentHashMap<String, ClientGameObject> gameObjects = new ConcurrentHashMap<String, ClientGameObject>();
	
	private Text timerText;
	
	private int timeLeft;
	
	@Override
	public void start() {
		Vector2 screenCenter = RenderingManager.getScreenCenter();
		float scale = RenderingManager.getScale();
		
		this.timerText = createStandardText("", 38, new Colour(33, 255, 55), new Vector2(screenCenter.getX(), 1.2f * scale));
		
		addClientSocketEvent(new SocketEvent("update_timer", this) {
			@Override
			public void call(String data) {
				Game g = (Game) this.getObject();
				g.setTimeLeft(Integer.parseInt(data));
			}
		});
		
		addClientSocketEvent(new SocketEvent("new_object", this) {
			@Override
			public void call(String data) {
				Game g = (Game) this.getObject();
				GameObject o = ServerGameObject.decode(data);
				
				String id = o.getId();
				
				g.getGameObjects().put(id, new ClientGameObject(o));
			}
		});
	}

	@Override
	public void render() {
		for (ClientGameObject object : this.getGameObjects().values()) {
			object.render();
		}
		
		this.timerText.setBody("Time Left: " + this.getTimeLeft());
		this.timerText.render();
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public ConcurrentHashMap<String, ClientGameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(ConcurrentHashMap<String, ClientGameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}
}
