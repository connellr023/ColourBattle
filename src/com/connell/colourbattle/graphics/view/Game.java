package com.connell.colourbattle.graphics.view;

import java.util.concurrent.ConcurrentHashMap;

import com.connell.colourbattle.graphics.ClientGameObject;
import com.connell.colourbattle.graphics.PlayerMarker;
import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.graphics.ui.Text;
import com.connell.colourbattle.graphics.ui.button.BackHomeButton;
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
	
	private BackHomeButton backButton;
	private BackHomeButton leaveButton;
	
	public PlayerMarker marker;
	
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
		this.gameEndText = createStandardText("SERVER CLOSED", 70, new Colour(255, 0, 0), screenCenter);
		
		this.backButton = new BackHomeButton(new Vector2(screenCenter.getX(), screenCenter.getY() + (2.5f * scale)), "Back To Menu", 1.6f, RenderingManager.getMainFont());
		this.leaveButton = new BackHomeButton(new Vector2(3.57f * scale, 0.5f * scale), "Leave Game", 1.2f, RenderingManager.getMainFont());
		
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
		
		addClientSocketEvent(new SocketEvent("player_id", this) {
			@Override
			public void call(String data) {
				Game g = (Game) this.getObject();
				g.marker = new PlayerMarker(g.getGameObjects().get(data).getReferenceObject());
			}
		});
		
		addClientSocketEvent(new SocketEvent("game_end_win", this) {
			@Override
			public void call(String data) {
				Game g = (Game) this.getObject();
				
				g.gameEndText.setBody("YOU WIN!");
				g.gameEndText.setColour(new Colour(0, 255, 0));
				
				g.gameOver = true;
			}
		});
		
		addClientSocketEvent(new SocketEvent("game_end_lose", this) {
			@Override
			public void call(String data) {
				Game g = (Game) this.getObject();
				
				g.gameEndText.setBody("YOU LOSE!");
				g.gameOver = true;
			}
		});
		
		addClientSocketEvent(new SocketEvent("game_end_draw", this) {
			@Override
			public void call(String data) {
				Game g = (Game) this.getObject();
				
				g.gameEndText.setBody("DRAW!");
				g.gameEndText.setColour(new Colour(235, 235, 235));
				
				g.gameOver = true;
			}
		});
	}

	@Override
	public void render() {
		ClientSocketStream client = SocketClientManager.getClient();
		RenderingManager renderer = RenderingManager.getRenderer();

		if (!this.gameOver) {
			for (ClientGameObject object : this.getGameObjects().values()) {
				object.render();
			}
			
			this.timerText.setBody("Time Left: " + this.getTimeLeft());
			this.timerText.render();
			
			if (this.marker != null) {
				this.marker.render();
			}
			
			this.leaveButton.render();
			
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
			renderer.fill(0);
			renderer.rect(0, 0, Constants.GAME_SIZE.getX(), Constants.GAME_SIZE.getY());
			
			this.gameEndText.render();
			this.backButton.render();
		}
	}
	
	public static void reset() {
		Game game = (Game) RenderingManager.getViews().get(2);
		
		game.gameOver = true;
		game.getGameObjects().clear();
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
