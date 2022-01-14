package com.connell.colourbattle.graphics;

import com.connell.colourbattle.utilities.Colour;
import com.connell.colourbattle.utilities.GameObject;
import com.connell.colourbattle.utilities.Hitbox;
import com.connell.colourbattle.utilities.Vector2;

public class ClientGameObject extends RenderableObject {
	private GameObject referenceObject;
	
	public ClientGameObject(GameObject referenceObject) {
		this.setReferenceObject(referenceObject);
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
		r.rect(p.getX() * scale, p.getY() * scale, h.getBottomRight().getX() * scale, h.getBottomRight().getY() * scale);
	}

	public GameObject getReferenceObject() {
		return referenceObject;
	}

	public void setReferenceObject(GameObject referenceObject) {
		this.referenceObject = referenceObject;
	}
}
