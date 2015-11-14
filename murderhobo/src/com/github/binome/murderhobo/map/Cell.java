package com.github.binome.murderhobo.map;

import org.newdawn.slick.Image;

import com.github.binome.murderhobo.Main;

public class Cell {

	private int x, y;
	private Image i;
	private boolean passable;

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
		FLOOR(Main.spriteMan.get("floor").getSprite(1, 7), true);

		private final Image i;
		private boolean passable;

		TileType(Image i, boolean passable) {
			this.i = i;
			this.passable = passable;
		}
	}
}
