package com.github.binome.murderhobo.map;

import com.github.binome.murderhobo.entities.Tile;

public class Cell {

	private int x,y;
	private Tile tile;
	
	public Cell(int x, int y, Tile tile) {
		super();
		this.x = x;
		this.y = y;
		this.tile = tile;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
