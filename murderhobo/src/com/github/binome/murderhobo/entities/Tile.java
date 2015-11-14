package com.github.binome.murderhobo.entities;

import org.newdawn.slick.SpriteSheet;

public class Tile extends Fixture {

	private boolean isPassable;

	private String pngPath;
	private int texX;
	private int texY;

	public Tile(TileType t) {
		super(32, 32, t.getPath(), t.getTexX(), 16, t.getTexY(), 16);
	}

	public boolean isPassable() {
		return isPassable;
	}

	public enum TileType {		
		FLOOR("res/dawnlike/Objects/Floor.png", 17, 112);

		private String path;
		private int texX;
		private int texY;


		TileType(String path, int texX, int texY) {
			this.path = path;
			this.texX = texX;
			this.texY = texY;
		}

		public String getPath() {
			return path;
		}

		public int getTexX() {
			return texX;
		}

		public int getTexY() {
			return texY;
		}

	}

}
