package com.github.binome.murderhobo.scenes;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.map.Cell;
import com.github.binome.murderhobo.map.Cell.TileType;
import com.github.binome.murderhobo.entities.Hero;

public class Level1 extends Level {
	public final int CELLS_WIDE = 60;
	public final int CELLS_TALL = 40;
	private int xScale = 400;
	private int yScale = 300;

	private Cell[][] grid;

	private Scene nextScene;

	public Level1() {
		nextScene = this;

		initLevel();

	}

	/**
	 * Draws the level and places Entities
	 */
	private void initLevel() {
		Main.aman.play("level1-peaceful");
		SoundStore.get().setCurrentMusicVolume(Reference.musicVolume);
		grid = new Cell[CELLS_WIDE][CELLS_TALL];
		createGrid();
		Hero.getInstance(this).setLoc(32, 32);

		// TODO Place entities
	}

	@Override
	public boolean drawFrame(float delta) {

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// GL11.glOrtho(0, Settings.SCR_WIDTH, Settings.SCR_HEIGHT, 0, 1, -1);
		GL11.glOrtho(Hero.getInstance(this).getX() - xScale, Hero.getInstance(this).getX() + xScale,
				Hero.getInstance(this).getY() + yScale, Hero.getInstance(this).getY() - yScale, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		drawGrid();

		Hero.getInstance(this).update(delta);
		Hero.getInstance(this).draw();

		return true;
	}

	private void createGrid() {
		// initialize with default texture
		for (int i = 0; i < CELLS_WIDE; i++) {
			for (int j = 0; j < CELLS_TALL; j++) {
				grid[i][j] = new Cell(i, j, Cell.TileType.ERROR_TEX);
			}
		}

		// walls
		// corners
		grid[0][0].setTileType(Cell.TileType.NWWALL);
		grid[CELLS_WIDE - 1][0].setTileType(Cell.TileType.NEWALL);
		grid[CELLS_WIDE - 1][CELLS_TALL - 1].setTileType(Cell.TileType.SEWALL);
		grid[0][CELLS_TALL - 1].setTileType(Cell.TileType.SWWALL);

		// west wall
		for (int i = 1;i < CELLS_TALL-1; i++){
			
		}
		
		//floor
		for (int i = 1; i < CELLS_WIDE - 1; i++) {
			for (int j = 1; j < CELLS_TALL - 1; j++) {
				grid[i][j] = new Cell(i, j, Cell.TileType.FLOOR);
			}
		}
	}

	private void drawGrid() {
		for (int i = 0; i < CELLS_WIDE; i++) {
			for (int j = 0; j < CELLS_TALL; j++) {
				grid[i][j].draw();
			}

		}
	}
}
