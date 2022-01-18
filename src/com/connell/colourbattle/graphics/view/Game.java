package com.connell.colourbattle.graphics.view;

import java.util.concurrent.ConcurrentHashMap;

import com.connell.colourbattle.graphics.ClientGameObject;
import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.Text;
import com.connell.colourbattle.networking.Packet;
import com.connell.colourbattle.networking.SocketEvent;
import com.connell.colourbattle.networking.client.ClientSocketStream;
import com.connell.colourbattle.networking.client.SocketClientManager;
import com.connell.colourbattle.networking.server.game.ServerGameObject;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.GameObject;
import com.connell.colourbattle.utilities.Vector2;

public class Game extends View {
	private ConcurrentHashMap<String, ClientGameObject> gameObjects = new ConcurrentHashMap<String, ClientGameObject>();
	
	private Text timerText;
	
	private int timeLeft;
	
	private boolean justJumped;
	
	@Override
	public void start() {
		Vector2 screenCenter = RenderingManager.getScreenCenter();
		float scale = RenderingManager.getScale();
		
		this.justJumped = false;
		
		this.timerText = createStandardText("", 42, new Colour(252, 20, 47), new Vector2(screenCenter.getX(), 0.5f * scale));
		
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
		
		ClientSocketStream client = SocketClientManager.getClient();
		RenderingManager renderer = RenderingManager.getRenderer();
		
		if (renderer.keyPressed) {
			int lastKey = RenderingManager.getLastKeyCode();
			
			if (!this.justJumped && (lastKey == 87 || lastKey == 38 || lastKey == 32)) { // Jump
				client.sendData(new Packet("jump"));
				this.justJumped = true;
			}
			
			if (lastKey == 65 || lastKey == 37) { // Move left
				client.sendData(new Packet("move_left"));
			}
			else if (lastKey == 68 || lastKey == 39) { // Move right
				client.sendData(new Packet("move_right"));
			}
		}
		else {
			this.justJumped = false;
		}
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
