package com.connell.colourbattle.graphics;

import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.GameObject;

public class PlayerMarker extends RenderableObject {
	private GameObject player;
	private Colour colour;
	
	public PlayerMarker(GameObject player) {
		this.setPlayer(player);
		this.setColour(new Colour(255, 236, 69));
	}
	
	@Override
	public void render() {
		RenderingManager renderer = this.getRenderer();
		
		GameObject p = this.getPlayer();
		Colour c = this.getColour();
		
		float scale = RenderingManager.getScale();
		
		float x = p.getPosition().getX() + (p.getHitbox().getWidth() / 2);
		float y = p.getPosition().getY() - 0.2f;
		
		renderer.fill(c.r, c.g, c.b);
		renderer.textFont(RenderingManager.getMainFont());
		renderer.textSize(60);
		renderer.text("ˇ", x * scale, y * scale);
	}

	public GameObject getPlayer() {
		return player;
	}

	public void setPlayer(GameObject player) {
		this.player = player;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}
}
