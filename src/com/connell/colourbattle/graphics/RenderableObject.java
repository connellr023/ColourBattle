package com.connell.colourbattle.graphics;

import com.connell.colourbattle.graphics.view.View;

public abstract class RenderableObject {
	private boolean isActive;
	private View parentView;
	
	private RenderingManager renderer;
	
	/*
	 * Represents an object that is able to be rendered on the client side
	 */
	public RenderableObject() {
		this.setActive(true);
		this.setRenderer(RenderingManager.getRenderer());
	}
	
	/**
	 * Executes every frame
	 * Code relating to displaying this object goes here
	 */
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
