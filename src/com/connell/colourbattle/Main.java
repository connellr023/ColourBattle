package com.connell.colourbattle;

import java.io.IOException;

import com.connell.colourbattle.graphics.RenderingManager;

public class Main {
	
	public static void main(String[] args) throws IOException {
		int frameRate = 60;
		int scale = 30;

		String[] processingArgs = {"Renderer"};
		
		RenderingManager.init(frameRate, scale);
		RenderingManager.run(processingArgs);
	}
}
