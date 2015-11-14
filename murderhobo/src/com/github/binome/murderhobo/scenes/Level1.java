package com.github.binome.murderhobo.scenes;

import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.github.binome.murderhobo.Settings;
import com.github.binome.murderhobo.entities.Fixture;
import com.github.binome.murderhobo.map.Cell;

public class Level1 extends Scene {
	protected LinkedList<Fixture> fixtures = new LinkedList<Fixture>();
	
	
	private Scene nextScene;
	
	public Level1(){
		nextScene = this;
		
		initLevel();
		
	}
	
	/**
	 * Draws the level and places Entities
	 */
	private void initLevel() {
		Cell[][] grid = new Cell[60][40];
		
		
		//TODO Place entities
	}

	@Override
	public boolean drawFrame(float delta) {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Settings.SCR_WIDTH, Settings.SCR_HEIGHT, 0, 1, -1);
		//GL11.glOrtho(ourHero.getX() - scale, ourHero.getX() + scale,
		//		ourHero.getY() + scale, ourHero.getY() - scale,
		//		1,-1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);


		return true;
	}

}
