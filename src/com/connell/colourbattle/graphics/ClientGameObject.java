package com.connell.colourbattle.graphics;

import com.connell.colourbattle.networking.SocketEvent;
import com.connell.colourbattle.networking.client.ClientSocketStream;
import com.connell.colourbattle.networking.client.SocketClientManager;
import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.GameObject;
import com.connell.colourbattle.utilities.Hitbox;
import com.connell.colourbattle.utilities.Vector2;

public class ClientGameObject extends RenderableObject {
	private GameObject referenceObject;
	
	public ClientGameObject(GameObject referenceObject) {
		this.setReferenceObject(referenceObject);
		
		this.start();
	}
	
	private void start() {
		ClientSocketStream client = SocketClientManager.getClient();
		
		client.listen(new SocketEvent(this.getReferenceObject().getId() + "_update_position", this.getReferenceObject()) {
			@Override
			public void call(String data) {
				GameObject g = (GameObject) this.getObject();
				g.setPosition(Vector2.parse(data));
			}
		});
	}
	
	@Override
	public void render() {
		RenderingManager r = this.getRenderer();
		GameObject ref = this.getReferenceObject();
		float scale = RenderingManager.getScale();
		
		Colour c = ref.getColour();
		Vector2 p = ref.getPosition();
		Hitbox h = ref.getHitbox();

		r.noStroke();
		r.fill(c.r, c.g, c.b);
		r.rect((p.getX() + (h.getWidth() / 2)) * scale, (p.getY() - (h.getHeight() / 2) + 1) * scale, h.getWidth() * scale, h.getHeight() * scale);
		
		if (RenderingManager.inDebugMode) {
			this.renderHitbox(new Colour(255, 0, 255));
		}
	}
	
	/**
	 * Renders this Game Object's Hit Box
	 * @param colour Is the Colour to Render the Hit Box in
	 */
	public void renderHitbox(Colour colour) {
		RenderingManager r = this.getRenderer();
		Hitbox hb = this.getReferenceObject().getRelativeHitbox();
		
		Vector2 center = this.getReferenceObject().getCenter();
		float scale = RenderingManager.getScale();
		
		r.strokeWeight(2);
		r.stroke(colour.r, colour.g, colour.b);
		r.line(
			hb.getTopLeft().getX() * scale,
			hb.getTopLeft().getY() * scale,
			hb.getBottomRight().getX() * scale,
			hb.getBottomRight().getY() * scale 
		);
		r.line(
			hb.getBottomRight().getX() * scale,
			hb.getTopLeft().getY() * scale,
			hb.getTopLeft().getX() * scale,
			hb.getBottomRight().getY() * scale 
		);
		
		r.stroke(255, 255, 255);
		r.strokeWeight(6);
		r.line(
			center.getX() * scale,
			center.getY() * scale,
			center.getX() * scale,
			center.getY() * scale 
		);
		r.line(
			this.getReferenceObject().getLeftX() * scale,
			center.getY() * scale,
			this.getReferenceObject().getLeftX() * scale,
			center.getY() * scale 
		);
		r.line(
			this.getReferenceObject().getRightX() * scale,
			center.getY() * scale,
			this.getReferenceObject().getRightX() * scale,
			center.getY() * scale 
		);
		r.line(
			center.getX() * scale,
			this.getReferenceObject().getTopY() * scale,
			center.getX() * scale,
			this.getReferenceObject().getTopY() * scale 
		);
		r.line(
			center.getX() * scale,
			this.getReferenceObject().getBottomY() * scale,
			center.getX() * scale,
			this.getReferenceObject().getBottomY() * scale 
		);
	}

	public GameObject getReferenceObject() {
		return referenceObject;
	}

	public void setReferenceObject(GameObject referenceObject) {
		this.referenceObject = referenceObject;
	}
}
