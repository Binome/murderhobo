package com.github.binome.murderhobo.scenes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.map.Cell;
import com.github.binome.murderhobo.entities.Arrow;
import com.github.binome.murderhobo.entities.Hero;
import com.github.binome.murderhobo.entities.Monster;
import com.github.binome.murderhobo.entities.Treasure;

public class Level1 extends Level {
	public final int CELLS_WIDE = 60;
	public final int CELLS_TALL = 40;

	@SuppressWarnings("unused")
	private Scene nextScene;

	public Level1() {
		nextScene = this;

		initLevel();

	}

	/**
	 * Draws the level and places Entities
	 */
	private void initLevel() {
		murderMode=false;
		Main.aman.play("level1-peaceful");
		SoundStore.get().setCurrentMusicVolume(Reference.musicVolume);
		grid = new Cell[CELLS_WIDE][CELLS_TALL];
		arrows = new LinkedList<Arrow>();
		monsters = new LinkedList<Monster>();
		treasures = new LinkedList<Treasure>();
		createGrid();

		placeMoney();
		placeEntities();
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
		processArrows(delta);
		processMonsters(delta);
		processTreasure();

		Hero.getInstance().update(delta, this);
		Hero.getInstance().draw();

		drawGUI();
		
		return true;
	}

	private void processArrows(float delta) {
		Arrow arr;
		Iterator<Arrow> arrIt = arrows.iterator();
		while (arrIt.hasNext()) {
			arr = arrIt.next();
			arr.update(delta);

			// destroy on impact with wall
			ArrayList<Cell> in = arr.inCells(this);
			Iterator<Cell> it = in.iterator();
			Cell c;
			while (it.hasNext()) {
				c = it.next();
				if (!c.passable) {
					arr.isActive = false;
				}
			}
			
			Monster mon;
			Iterator<Monster> monIt = monsters.iterator();
			while (monIt.hasNext()){
				mon = monIt.next();
				if (arr.getHitBox().intersects(mon.getHitBox())){
					mon.applyAttack(arr.getVelocity(), getInstance());
					arr.isActive=false;
				}
			}

			if (!arr.isActive) {
				// System.out.println("removing inactive entity");
				arrIt.remove();
			} else {
				arr.draw();
			}
		}
		
	}

	private void processMonsters(float delta){
		Monster mon;
		Iterator<Monster> monIt = monsters.iterator();
		while (monIt.hasNext()) {
			mon = monIt.next();
			mon.update(delta, this.getInstance());
			if (mon.isHostile() && !murderMode){
				beginFight();
			}
			
			if (!mon.isActive) {
				// System.out.println("removing inactive entity");
				monIt.remove();
			} else {
				mon.draw();
			}
		}
	}
	
	private void processTreasure(){
		Treasure t;
		Iterator<Treasure> treasureIt = treasures.iterator();
		while (treasureIt.hasNext()) {
			t = treasureIt.next();
			if (t.getHitBox().intersects(Hero.getInstance().getHitBox()))
			{
				Hero.getInstance().addScore(t.getValue());
				t.isActive = false;
			}
			if (!t.isActive) {
				// System.out.println("removing inactive entity");
				treasureIt.remove();
			} else {
				t.draw();
			}
		}
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
		for (int i = 1; i < CELLS_TALL - 1; i++) {
			grid[0][i].setTileType(Cell.TileType.WWALL);
		}
		// east wall
		for (int i = 1; i < CELLS_TALL - 1; i++) {
			grid[CELLS_WIDE - 1][i].setTileType(Cell.TileType.EWALL);
		}
		// north wall
		for (int i = 1; i < CELLS_WIDE - 1; i++) {
			grid[i][0].setTileType(Cell.TileType.NWALL);
		}
		// south wall
		for (int i = 1; i < CELLS_WIDE - 1; i++) {
			grid[i][CELLS_TALL - 1].setTileType(Cell.TileType.NWALL);
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
		if (instance == null) {
			instance = new Level1();
			Hero.getInstance().setLoc(64, 64);
		}
		return instance;
	}

	private void placeEntities() {
		Monster mon1 = new Monster();
		mon1.setLoc(12*Reference.GRID_SIZE, 9 * Reference.GRID_SIZE);
		monsters.add(mon1);
	}
	
	private void beginFight(){
		murderMode = true;
		Main.aman.play("level1-murder");
		SoundStore.get().setCurrentMusicVolume(Reference.musicVolume);
	}
	
	private void placeMoney(){
		Treasure t1 = new Treasure(Reference.LOOT_DEFAULT);
		t1.setLoc(10*Reference.GRID_SIZE, 8 * Reference.GRID_SIZE);
		treasures.add(t1);
	}
	
	public void spawnTreasure(int x, int y, int value){
		Treasure t = new Treasure(value);
		t.setLoc(x,y);
		t.makeSmall();
		this.getInstance().treasures.add(t);
	}


}
