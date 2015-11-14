package com.github.binome.murderhobo.entities;


public class Tile extends Fixture {

	private boolean isPassable;
	private enum TileType {NWALL}
	private TileType type;
	
	private String pngPath;
	private int texX;
	private int texY;
	
	
	
	public Tile(int boxWidth, String pngpath, int xTexOffset, int texWidth, int yTexOffset, int texHeight) {
		super(32,32,pngpath, xTexOffset, texWidth, yTexOffset, texHeight);
		this.type=type;
		decodeType(type);
		this.isPassable = isPassable;
	}

	public boolean isPassable() {
		return isPassable;
	}
	
	public String decodeType(TileType type){
		String path = null;
		
		switch (type) {
			case NWALL:
				path = "res/dawnlike/Objects/Wall.png";
				texX = 16;
				texY = 49;
				break;
			default:
				break;
		}
		
		
		return path;
	}
}
