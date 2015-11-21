package com.github.binome.murderhobo.scenes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.map.Cell;
import com.github.binome.murderhobo.entities.Arrow;
import com.github.binome.murderhobo.entities.Entity;
import com.github.binome.murderhobo.entities.Hero;

public class Level1 extends Level {
	public final int CELLS_WIDE = 60;
	public final int CELLS_TALL = 40;
	private int xScale = 400;
	private int yScale = 300;
	
	//private static Level instance;
	private Scene nextScene;

	public Level1() {
		nextScene = this;

		initLevel();

	}
	
	//public static Level getInstance() {
	//	if (instance == null){
	//		instance = new Level1();
	//		Hero.getInstance().setLoc(64, 64);
	//	}
	//	return instance;
	//}

	/**
	 * Draws the level and places Entities
	 */
	private void initLevel() {
		Main.aman.play("level1-peaceful");
		SoundStore.get().setCurrentMusicVolume(Reference.musicVolume);
		grid = new Cell[CELLS_WIDE][CELLS_TALL];
		arrows = new LinkedList<Arrow>();
		createGrid();

		// TODO Place entities
	}

	@Override
	public boolean drawFrame(float delta) {

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// GL11.glOrtho(0, Settings.SCR_WIDTH, Settings.SCR_HEIGHT, 0, 1, -1);
		GL11.glOrtho(Hero.getInstance().getX() - xScale, Hero.getInstance().getX() + xScale,
				Hero.getInstance().getY() + yScale, Hero.getInstance().getY() - yScale, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		drawGrid();

		Entity ent;
		Iterator<Arrow> arrIt = arrows.iterator();
		while (arrIt.hasNext())
		{
			ent = arrIt.next();
			ent.update(delta);

			//destroy on impact with wall
			ArrayList<Cell> in = ent.inCells(this);
			Iterator<Cell> it = in.iterator();
			Cell c;
			while (it.hasNext()) {
				c = it.next();
				if (!c.passable) {
					ent.isActive=false;
				}
				}
			
			if (! ent.isActive)
			{
				//System.out.println("removing inactive entity");
				arrIt.remove();
			}
			else 
			{
				ent.draw();
			}
		}
		
		Hero.getInstance().update(delta, this);
		Hero.getInstance().draw();

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
			grid[0][i].setTileType(Cell.TileType.WWALL);
		}
		// east wall
		for (int i = 1;i < CELLS_TALL-1; i++){
			grid[CELLS_WIDE-1][i].setTileType(Cell.TileType.EWALL);
		}
		// north wall
		for (int i = 1;i < CELLS_WIDE-1; i++){
			grid[i][0].setTileType(Cell.TileType.NWALL);
		}
		// south wall
		for (int i = 1;i < CELLS_WIDE-1; i++){
			grid[i][CELLS_TALL-1].setTileType(Cell.TileType.NWALL);
		}
		// floor
		for (int i = 1; i < CELLS_WIDE - 1; i++) {
			for (int j = 1; j < CELLS_TALL - 1; j++) {
				grid[i][j] = new Cell(i, j, Cell.TileType.FLOOR);
			}
		}
		
		// first room
		grid[6][6].setTileType(Cell.TileType.INNER_NWWALL);
		grid[7][6].setTileType(Cell.TileType.INNER_NWALL);
		grid[8][6].setTileType(Cell.TileType.INNER_NWALL);
		grid[9][6].setTileType(Cell.TileType.INNER_NWALL);
		grid[10][6].setTileType(Cell.TileType.INNER_NWALL);
		grid[13][6].setTileType(Cell.TileType.INNER_NEWALL);
		grid[13][7].setTileType(Cell.TileType.INNER_EWALL);
		grid[13][8].setTileType(Cell.TileType.INNER_EWALL);
		grid[13][9].setTileType(Cell.TileType.INNER_EWALL);
		grid[13][10].setTileType(Cell.TileType.INNER_EWALL);
		grid[13][11].setTileType(Cell.TileType.INNER_SEWALL);
		grid[12][11].setTileType(Cell.TileType.INNER_SWALL);
		grid[11][11].setTileType(Cell.TileType.INNER_SWALL);
		grid[8][11].setTileType(Cell.TileType.INNER_SWALL);
		grid[7][11].setTileType(Cell.TileType.INNER_SWALL);
		grid[6][11].setTileType(Cell.TileType.INNER_SWWALL);
		grid[6][10].setTileType(Cell.TileType.INNER_WWALL);
		grid[6][7].setTileType(Cell.TileType.INNER_WWALL);
		
		
	}

	private void drawGrid() {
		for (int i = 0; i < CELLS_WIDE; i++) {
			for (int j = 0; j < CELLS_TALL; j++) {
				grid[i][j].draw();
			}

		}
	}

	public Level getInstance() {
		if (instance == null){
			instance = new Level1();
			Hero.getInstance().setLoc(64, 64);
		}
		return instance;
	}
	

}
