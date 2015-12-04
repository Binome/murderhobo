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
import com.github.binome.murderhobo.entities.Hero;
import com.github.binome.murderhobo.entities.Monster;
import com.github.binome.murderhobo.entities.Treasure;

public class Level1 extends Level {
	public final int CELLS_WIDE = 60;
	public final int CELLS_TALL = 40;
	private Scene nextScene;

	// Amount of time for pow effect to display when player is injured
	public final int POW_TIME = 250;
	public int powTimer = POW_TIME;

	public Level1() {
		nextScene = this;
		initLevel();
	}

	/**
	 * Draws the level and places Entities
	 */
	private void initLevel() {
		murderMode = false;
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

		// Is player injured?
		if (powTimer < POW_TIME) {
			Main.spriteMan.get("pow").draw(Hero.getInstance().getX(), Hero.getInstance().getY());
			powTimer += delta;
		} else if (powTimer > POW_TIME) {
			powTimer = POW_TIME;
		}

		// Is the hero dead?
		if (Hero.getInstance().getHealth() <= 0) {
			nextScene = new GameOver(Hero.getInstance().getScore(), Hero.getInstance().getKillCount());
			return false;
		}

		// Is the hero in the exit squares?
		if (Hero.getInstance().getX() >= 57 * Reference.GRID_SIZE
				&& Hero.getInstance().getY() >= 37 * Reference.GRID_SIZE) {
			nextScene = new GameOver(Hero.getInstance().getScore(), Hero.getInstance().getKillCount());
			return false;
		} else {
			return true;
		}
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
				if (!c.isPassable()) {
					arr.isActive = false;
				}
			}

			Monster mon;
			Iterator<Monster> monIt = monsters.iterator();
			while (monIt.hasNext()) {
				mon = monIt.next();
				if (arr.getHitBox().intersects(mon.getHitBox())) {
					mon.applyAttack(arr.getVelocity(), getInstance());
					arr.isActive = false;
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

	private void processMonsters(float delta) {
		Monster mon;
		Iterator<Monster> monIt = monsters.iterator();
		while (monIt.hasNext()) {
			mon = monIt.next();
			mon.update(delta, this.getInstance());
			if (mon.isHostile() && !murderMode) {
				beginFight();
			}

			if (mon.getHitBox().intersects(Hero.getInstance().getHitBox())){
				mon.goHostile();
			}
			
			if (!mon.isActive) {
				// System.out.println("removing inactive entity");
				monIt.remove();
			} else {
				mon.draw();
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

		//TODO: REPLACE ALL OF THIS WITH A BITMAP BASED LEVEL MAP
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
		for (int i = 7; i <= 10; i++) {
			grid[i][6].setTileType(Cell.TileType.INNER_NWALL);
		}
		grid[13][6].setTileType(Cell.TileType.INNER_NEWALL);
		for (int i = 7; i <= 10; i++) {
			grid[13][i].setTileType(Cell.TileType.INNER_EWALL);
		}
		grid[13][11].setTileType(Cell.TileType.INNER_NEW_3WALL);
		grid[12][11].setTileType(Cell.TileType.INNER_SWALL);
		grid[11][11].setTileType(Cell.TileType.INNER_SWALL);
		grid[8][11].setTileType(Cell.TileType.INNER_SEW_3WALL);
		grid[7][11].setTileType(Cell.TileType.INNER_SWALL);
		grid[6][11].setTileType(Cell.TileType.INNER_SWWALL);
		grid[6][10].setTileType(Cell.TileType.INNER_WWALL);
		grid[6][7].setTileType(Cell.TileType.INNER_WWALL);

		// Second room
		for (int i = 12; i <= 18; i++) {
			grid[8][i].setTileType(Cell.TileType.INNER_WWALL);
		}
		grid[8][19].setTileType(Cell.TileType.INNER_NSE_3WALL);
		for (int i = 9; i <= 23; i++) {
			grid[i][19].setTileType(Cell.TileType.INNER_SWALL);
		}
		grid[24][19].setTileType(Cell.TileType.INNER_SEWALL);
		grid[24][18].setTileType(Cell.TileType.INNER_EWALL);
		for (int i = 14; i <= 21; i++) {
			grid[i][11].setTileType(Cell.TileType.INNER_NWALL);
		}
		grid[24][11].setTileType(Cell.TileType.INNER_NEWALL);
		grid[24][12].setTileType(Cell.TileType.INNER_EWALL);
		grid[24][13].setTileType(Cell.TileType.INNER_EWALL);
		grid[24][14].setTileType(Cell.TileType.INNER_EWALL);
		grid[24][17].setTileType(Cell.TileType.INNER_EWALL);

		// Bedrooms
		for (int i = 8; i <= 23; i = i + 5) {
			for (int j = 20; j <= 25; j++) {
				grid[i][j].setTileType(Cell.TileType.INNER_WWALL);
			}
			grid[i][26].setTileType(Cell.TileType.INNER_NEW_3WALL);
			grid[i + 1][26].setTileType(Cell.TileType.INNER_SWALL);
			grid[i - 1][26].setTileType(Cell.TileType.INNER_SWALL);
		}
		grid[7][26].setTileType(Cell.TileType.FLOOR);
		grid[8][26].setTileType(Cell.TileType.INNER_SWWALL);
		grid[23][26].setTileType(Cell.TileType.INNER_SEWALL);
		grid[24][26].setTileType(Cell.TileType.FLOOR);
		grid[13][19].setTileType(Cell.TileType.INNER_SEW_3WALL);
		grid[18][19].setTileType(Cell.TileType.INNER_SEW_3WALL);
		grid[23][19].setTileType(Cell.TileType.INNER_SEW_3WALL);

		//Cross
		grid[45][20].setTileType(Cell.TileType.INNER_CEN_WALL);
		grid[44][20].setTileType(Cell.TileType.INNER_NWALL);
		grid[43][20].setTileType(Cell.TileType.INNER_NWALL);
		grid[46][20].setTileType(Cell.TileType.INNER_NWALL);
		grid[47][20].setTileType(Cell.TileType.INNER_NWALL);
		grid[45][18].setTileType(Cell.TileType.INNER_EWALL);
		grid[45][19].setTileType(Cell.TileType.INNER_EWALL);
		grid[45][21].setTileType(Cell.TileType.INNER_EWALL);
		grid[45][22].setTileType(Cell.TileType.INNER_EWALL);
		
		// Treasure Room 1
		for (int i = 3; i <= 40; i++) {
			grid[i][34].setTileType(Cell.TileType.INNER_NWALL);
		}
		grid[41][34].setTileType(Cell.TileType.INNER_NEWALL);
		for (int j = 35; j <= 38; j++) {
			grid[41][j].setTileType(Cell.TileType.INNER_EWALL);
		}
		
		//Treasure Room 2
		for (int i = 35; i <= 58; i++){
			grid[i][5].setTileType(Cell.TileType.INNER_SWALL);
		}
		grid[55][5].setTileType(Cell.TileType.INNER_NEW_3WALL);
		grid[55][4].setTileType(Cell.TileType.INNER_EWALL);
		grid[55][1].setTileType(Cell.TileType.INNER_EWALL);
		

		// exit room
		grid[56][36].setTileType(Cell.TileType.INNER_NWWALL);
		grid[56][37].setTileType(Cell.TileType.INNER_WWALL);
		grid[56][38].setTileType(Cell.TileType.INNER_WWALL);
		grid[57][37].setTileType(Cell.TileType.NWDIRT);
		grid[58][37].setTileType(Cell.TileType.NEDIRT);
		grid[57][38].setTileType(Cell.TileType.WDIRT);
		grid[57][39].setTileType(Cell.TileType.WDIRT);
		grid[58][38].setTileType(Cell.TileType.EDIRT);
		grid[58][39].setTileType(Cell.TileType.EDIRT);
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
		mon1.setLoc(12 * Reference.GRID_SIZE, 9 * Reference.GRID_SIZE);
		monsters.add(mon1);

		Monster mon2 = new Monster();
		mon2.setLoc(23 * Reference.GRID_SIZE, 12 * Reference.GRID_SIZE);
		monsters.add(mon2);

		Monster mon3 = new Monster();
		mon3.setLoc(23 * Reference.GRID_SIZE, 18 * Reference.GRID_SIZE);
		monsters.add(mon3);

		Monster mon4 = new Monster();
		mon4.setLoc(9 * Reference.GRID_SIZE, 20 * Reference.GRID_SIZE);
		monsters.add(mon4);

		Monster mon5 = new Monster();
		mon5.setLoc(14 * Reference.GRID_SIZE, 20 * Reference.GRID_SIZE);
		monsters.add(mon5);

		Monster mon6 = new Monster();
		mon6.setLoc(19 * Reference.GRID_SIZE, 20 * Reference.GRID_SIZE);
		monsters.add(mon6);
		
		for (int j = 35; j <= 38; j++){
			Monster treasureGuard = new Monster();
			treasureGuard.setLoc(38 * Reference.GRID_SIZE, j * Reference.GRID_SIZE);
			monsters.add(treasureGuard);
			treasureGuard = new Monster();
			treasureGuard.setLoc(37 * Reference.GRID_SIZE, j * Reference.GRID_SIZE);
			monsters.add(treasureGuard);
		}

		Monster mon7 = new Monster();
		mon7.setLoc(44 * Reference.GRID_SIZE, 19 * Reference.GRID_SIZE);
		monsters.add(mon7);

		Monster mon8 = new Monster();
		mon8.setLoc(44 * Reference.GRID_SIZE, 21 * Reference.GRID_SIZE);
		monsters.add(mon8);

		Monster mon9 = new Monster();
		mon9.setLoc(46 * Reference.GRID_SIZE, 19 * Reference.GRID_SIZE);
		monsters.add(mon9);

		Monster mon10 = new Monster();
		mon10.setLoc(55 * Reference.GRID_SIZE, 2 * Reference.GRID_SIZE);
		monsters.add(mon10);
		
		Monster mon11 = new Monster();
		mon11.setLoc(55 * Reference.GRID_SIZE, 3 * Reference.GRID_SIZE);
		monsters.add(mon11);
		
		for (int i = 54; i >= 35; i = i - 2){
			Monster treasureGuard = new Monster();
			treasureGuard.setLoc(i * Reference.GRID_SIZE, 1 * Reference.GRID_SIZE);
			monsters.add(treasureGuard);
			treasureGuard = new Monster();
			treasureGuard.setLoc(i * Reference.GRID_SIZE, 4 * Reference.GRID_SIZE);
			monsters.add(treasureGuard);
		}
	}

	private void beginFight() {
		murderMode = true;
		Main.aman.play("level1-murder");
		SoundStore.get().setCurrentMusicVolume(Reference.musicVolume);
	}

	private void placeMoney() {
		Treasure t1 = new Treasure(Reference.LOOT_DEFAULT);
		t1.setLoc(10 * Reference.GRID_SIZE, 8 * Reference.GRID_SIZE);
		treasures.add(t1);
		
		Treasure t2 = new Treasure(Reference.LOOT_DEFAULT);
		t2.setLoc(12 * Reference.GRID_SIZE, 20 * Reference.GRID_SIZE);
		treasures.add(t2);
		
		Treasure t3 = new Treasure(Reference.LOOT_DEFAULT);
		t3.setLoc(17 * Reference.GRID_SIZE, 20 * Reference.GRID_SIZE);
		treasures.add(t3);
		
		Treasure t4 = new Treasure(Reference.LOOT_DEFAULT);
		t4.setLoc(22 * Reference.GRID_SIZE, 20 * Reference.GRID_SIZE);
		treasures.add(t4);
		
		for (int i = 39; i <= 40; i++){
			for (int j = 35; j <= 38; j++){
				Treasure tRoomTreasure = new Treasure(Reference.LOOT_DEFAULT);
				tRoomTreasure.setLoc(i * Reference.GRID_SIZE, j * Reference.GRID_SIZE);
				treasures.add(tRoomTreasure);
			}
		}
		for (int i = 56; i <= 58; i++){
			for (int j = 1; j <= 4; j++){
				Treasure tRoomTreasure = new Treasure(Reference.LOOT_DEFAULT);
				tRoomTreasure.setLoc(i * Reference.GRID_SIZE, j * Reference.GRID_SIZE);
				treasures.add(tRoomTreasure);
			}
		}
	}

	public void spawnTreasure(int x, int y, int value) {
		Treasure t = new Treasure(value);
		t.setLoc(x, y);
		t.makeSmall();
		this.getInstance();
		Level.treasures.add(t);
	}

	@Override
	public Scene nextScene() {
		return nextScene;
	}

	public void applyPow() {
		powTimer = 0;
	}

}
