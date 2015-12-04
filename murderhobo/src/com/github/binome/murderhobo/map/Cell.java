package com.github.binome.murderhobo.map;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Image;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.entities.Entity;

public class Cell extends Entity {

	private int x, y;
	private Image i;
	private boolean passable = false;

	public Cell(int x, int y, TileType t) {
		this.x = x;
		this.y = y;
		i = t.i;
		passable = t.passable;
		hitBox = new Rectangle(x*Reference.GRID_SIZE, y*Reference.GRID_SIZE, Reference.GRID_SIZE, Reference.GRID_SIZE);
	}

	public boolean isPassable(){
		return passable;
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
		
		NWDIRT(Main.spriteMan.get("floor").getSprite(0, 24), true),
		NEDIRT(Main.spriteMan.get("floor").getSprite(2, 24), true),
		EDIRT(Main.spriteMan.get("floor").getSprite(2, 25), true),
		WDIRT(Main.spriteMan.get("floor").getSprite(0, 25), true),

		NWALL(Main.spriteMan.get("wall").getSprite(8,15), false),
		SWALL(Main.spriteMan.get("wall").getSprite(11,17), false),
		EWALL(Main.spriteMan.get("wall").getSprite(7,16), false),
		WWALL(Main.spriteMan.get("wall").getSprite(7,16), false),
		
		NWWALL(Main.spriteMan.get("wall").getSprite(7,15), false),
		NEWALL(Main.spriteMan.get("wall").getSprite(9,15), false),
		SWWALL(Main.spriteMan.get("wall").getSprite(7,17), false),
		SEWALL(Main.spriteMan.get("wall").getSprite(9,17), false),

		INNER_NWWALL(Main.spriteMan.get("wall").getSprite(0,12),false),
		INNER_NEWALL(Main.spriteMan.get("wall").getSprite(2,12),false),
		INNER_SEWALL(Main.spriteMan.get("wall").getSprite(2,14),false),
		INNER_SWWALL(Main.spriteMan.get("wall").getSprite(0,14),false),

		INNER_SEW_3WALL(Main.spriteMan.get("wall").getSprite(4,12),false),
		INNER_NEW_3WALL(Main.spriteMan.get("wall").getSprite(4,14),false),
		INNER_NSE_3WALL(Main.spriteMan.get("wall").getSprite(3,13),false),
		INNER_CEN_WALL(Main.spriteMan.get("wall").getSprite(4,13),false),
		
		INNER_NWALL(Main.spriteMan.get("wall").getSprite(1,12),false),
		INNER_EWALL(Main.spriteMan.get("wall").getSprite(0,13),false),
		INNER_SWALL(Main.spriteMan.get("wall").getSprite(1,12),false),
		INNER_WWALL(Main.spriteMan.get("wall").getSprite(0,13),false);

		private final Image i;
		protected boolean passable;

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
