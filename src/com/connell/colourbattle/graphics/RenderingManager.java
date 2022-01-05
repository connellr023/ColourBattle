package com.connell.colourbattle.graphics;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.LinkedList;

import com.connell.colourbattle.graphics.view.View;
import com.connell.colourbattle.utilities.Vector2;

public class RenderingManager extends PApplet {
	private static LinkedList<View> views;
	private static int activeViewIndex;
	
	private static int frameRate;
	private static int scale;
	
	private static Vector2 screenSize;
	private static RenderingManager renderer;
	private static PFont mainFont;
	
	private static int lastKeyCode;
	
	public static void init(int frameRate, int scale, Vector2 canvasSize) {
		setViews(new LinkedList<View>());
		setActiveViewIndex(0);
		
		setRenderer(new RenderingManager());
		setFrameRate(frameRate);
		setScale(scale);
		setScreenSize(canvasSize);
	}
	
	public static void run(String[] pArgs) {
		View.registerViews();
		PApplet.runSketch(pArgs, getRenderer());
	}
	
	public void settings() {
		Vector2 canvasSize = getScreenSize();
		int scale = getScale();
		
		this.noSmooth();
		this.size((int) canvasSize.getX() * scale, (int) canvasSize.getY() * scale);
	}
	
	public void setup() {
		this.surface.setTitle("Colour Battle");
		this.surface.setFrameRate(getFrameRate());
		
		setMainFont(getRenderer().createFont("com/connell/colourbattle/graphics/assets/game.ttf", 1));
		View.loadViews();
	}
	
	public void draw() {
		this.background(0);
		getViews().get(getActiveViewIndex()).render();
	}
	
	public void keyPressed() {
		setLastKeyCode(this.keyCode);
	}
	
	public static void addView(View view) {
		getViews().add(view);
	}

	public static int getFrameRate() {
		return frameRate;
	}

	public static void setFrameRate(int frameRate) {
		RenderingManager.frameRate = frameRate;
	}

	public static int getScale() {
		return scale;
	}

	public static void setScale(int scale) {
		RenderingManager.scale = scale;
	}

	public static Vector2 getScreenSize() {
		return screenSize;
	}

	public static void setScreenSize(Vector2 screenSize) {
		RenderingManager.screenSize = screenSize;
	}

	public static RenderingManager getRenderer() {
		return renderer;
	}

	public static void setRenderer(RenderingManager renderer) {
		RenderingManager.renderer = renderer;
	}

	public static LinkedList<View> getViews() {
		return views;
	}

	public static void setViews(LinkedList<View> views) {
		RenderingManager.views = views;
	}

	public static int getActiveViewIndex() {
		return activeViewIndex;
	}

	public static void setActiveViewIndex(int activeViewIndex) {
		if (activeViewIndex <= getViews().size() && activeViewIndex >= 0) {			
			RenderingManager.activeViewIndex = activeViewIndex;
		}
		else {
			System.out.println("Supplied View Index is Out of Range");
		}
	}
	
	public static Vector2 getScreenCenter() {
		return new Vector2((RenderingManager.getScreenSize().getX() / 2) * getScale(), (RenderingManager.getScreenSize().getY() / 2) * getScale());
	}

	public static int getLastKeyCode() {
		return lastKeyCode;
	}

	public static void setLastKeyCode(int lastKeyCode) {
		RenderingManager.lastKeyCode = lastKeyCode;
	}

	public static PFont getMainFont() {
		return mainFont;
	}

	private static void setMainFont(PFont mainFont) {
		RenderingManager.mainFont = mainFont;
	}
}
