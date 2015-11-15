package com.github.binome.murderhobo.map;

import org.newdawn.slick.Image;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.entities.Entity;

public class Cell extends Entity {

	private int x, y;
	private Image i;
	public boolean passable;

	public Cell(int x, int y, TileType t) {
		this.x = x;
		this.y = y;
		i = t.i;
		passable = t.passable;
	}

	public Image getImage() { return i; }
	
	public void setTileType(TileType t){
		i = t.i;
		passable = t.passable;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	

	public enum TileType {
		ERROR_TEX(Main.spriteMan.get("floor").getSprite(13, 1),false),
		FLOOR(Main.spriteMan.get("floor").getSprite(1, 7), true),

		NWALL(Main.spriteMan.get("wall").getSprite(8,15), false),
		SWALL(Main.spriteMan.get("wall").getSprite(11,17), false),
		WWALL(Main.spriteMan.get("wall").getSprite(7,16).getFlippedCopy(true, false), false),
		EWALL(Main.spriteMan.get("wall").getSprite(7,16), false),
		
		NWWALL(Main.spriteMan.get("wall").getSprite(7,15), false),
		NEWALL(Main.spriteMan.get("wall").getSprite(9,15), false),
		SWWALL(Main.spriteMan.get("wall").getSprite(7,17), false),
		SEWALL(Main.spriteMan.get("wall").getSprite(9,17), false);

		private final Image i;
		private boolean passable;

		TileType(Image i, boolean passable) {
			this.i = i;
			this.passable = passable;
		}
	}


	public void update(float delta) {	}

	@Override
	public void draw() {
		int xLoc= x * Reference.GRID_SIZE;
		int yLoc= y * Reference.GRID_SIZE;
		i.draw(xLoc, yLoc, Reference.TILE_SIZE, Reference.TILE_SIZE);
	}
}
