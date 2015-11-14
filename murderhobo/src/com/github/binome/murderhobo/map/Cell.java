package com.github.binome.murderhobo.map;

import com.github.binome.murderhobo.entities.Tile;

public class Cell {

	private int x, y;
	private Tile t;

	public Cell(int x, int y, Tile t) {
		this.x = x;
		this.y = y;
		this.t = t;
	}

	public Tile getTile() {
		return t;
	}

	public void setTile(Tile tile) {
		this.t = tile;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
