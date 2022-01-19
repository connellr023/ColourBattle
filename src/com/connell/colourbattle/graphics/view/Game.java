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
import com.connell.colourbattle.utilities.Constants;
import com.connell.colourbattle.utilities.GameObject;
import com.connell.colourbattle.utilities.Vector2;

public class Game extends View {
	private ConcurrentHashMap<String, ClientGameObject> gameObjects = new ConcurrentHashMap<String, ClientGameObject>();
	
	private Text timerText;
	public Text gameEndText;
	
	private int timeLeft;
	
	private boolean justJumped;
	public boolean gameOver;
	
	@Override
	public void start() {
		Vector2 screenCenter = RenderingManager.getScreenCenter();
		float scale = RenderingManager.getScale();
		
		this.justJumped = false;
		this.gameOver = false;
		
		this.timerText = createStandardText("", 42, new Colour(252, 20, 47), new Vector2(screenCenter.getX(), 0.5f * scale));
		this.gameEndText = createStandardText("", 50, new Colour(252, 255, 255), screenCenter);
		
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
		
		addClientSocketEvent(new SocketEvent("game_end_win", this) {
			@Override
			public void call(String data) {
				Game g = (Game) this.getObject();
				
				g.gameEndText.setBody("You Win!");
				g.gameEndText.setColour(new Colour(0, 255, 0));
				
				g.gameOver = true;
			}
		});
		
		addClientSocketEvent(new SocketEvent("game_end_lose", this) {
			@Override
			public void call(String data) {
				Game g = (Game) this.getObject();
				
				g.gameEndText.setBody("You Lose!");
				g.gameEndText.setColour(new Colour(255, 0, 0));
				
				g.gameOver = true;
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
		
		if (!this.gameOver) {			
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
		else {
			renderer.fill(renderer.color(0, 0, 0, 100));
			renderer.rect(0, 0, Constants.GAME_SIZE.getX(), Constants.GAME_SIZE.getY());
			
			this.gameEndText.render();
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
