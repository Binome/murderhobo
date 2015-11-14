package com.github.binome.murderhobo.scenes;

import java.util.HashMap;
import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Settings;
import com.github.binome.murderhobo.map.Cell;
import com.github.binome.murderhobo.entities.Fixture;

public class Level1 extends Scene {
	protected LinkedList<Fixture> fixtures = new LinkedList<Fixture>();
	public final int CELLS_WIDE = 60;
	public final int CELLS_TALL = 40;
	
	private Cell[][] grid;
	
	private Scene nextScene;
	
	public Level1(){
		nextScene = this;
		
		initLevel();
		
	}
	
	/**
	 * Draws the level and places Entities
	 */
	private void initLevel() {
		Main.aman.play("level1-peaceful");
		SoundStore.get().setCurrentMusicVolume(Settings.musicVolume);
		grid = new Cell[CELLS_WIDE][CELLS_TALL];
		populateGrid();
		
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

		drawGrid();
		
		return true;
	}

	private void populateGrid(){
		
		for (int i = 0; i < CELLS_WIDE; i++){
			for (int j = 0; j < CELLS_TALL; j++){
				grid[i][j] = new Cell(i, j, Cell.TileType.FLOOR);
			}
		}
	}
	
	private void drawGrid(){
		int xLoc, yLoc;
		for (int i = 0; i < CELLS_WIDE; i++){
			xLoc=i*Settings.GRID_SIZE;
			for (int j = 0; j < CELLS_TALL; j++){
				yLoc=j*Settings.GRID_SIZE;
				grid[0][0].getImage().draw(xLoc, yLoc, Settings.TILE_SIZE, Settings.TILE_SIZE);
			}
		}
	}
}
