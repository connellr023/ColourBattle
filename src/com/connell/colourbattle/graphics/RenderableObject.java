package com.connell.colourbattle.graphics;

import com.connell.colourbattle.graphics.view.View;

public abstract class RenderableObject {
	private boolean isActive;
	private View parentView;
	
	private RenderingManager renderer;
	
	public RenderableObject() {
		this.setActive(true);
		this.setRenderer(RenderingManager.getRenderer());
	}
	
	public abstract void render();

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public View getParentView() {
		return parentView;
	}

	public void setParentView(View parentView) {
		this.parentView = parentView;
	}

	public RenderingManager getRenderer() {
		return renderer;
	}

	public void setRenderer(RenderingManager r) {
		this.renderer = r;
	}
}
