package com.github.binome.murderhobo.entities;

public class Tile extends Fixture {

	private boolean isPassable;
	
	public Tile(int width, String pngpath, boolean isPassable) {
		super(width, pngpath);
		this.isPassable = isPassable;
	}

	public boolean isPassable() {
		return isPassable;
	}
}
