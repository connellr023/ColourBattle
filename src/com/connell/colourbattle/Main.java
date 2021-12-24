package com.connell.colourbattle;

import java.io.IOException;

import com.connell.colourbattle.graphics.RenderingManager;
import com.connell.colourbattle.utilities.Vector2;

public class Main {
	
	public static void main(String[] args) throws IOException {
		int frameRate = 60;
		int scale = 30;
		
		Vector2 canvasSize = new Vector2(35, 20);
		String[] processingArgs = {"Renderer"};
		
		RenderingManager.init(frameRate, scale, canvasSize);
		RenderingManager.run(processingArgs);
	}
}
