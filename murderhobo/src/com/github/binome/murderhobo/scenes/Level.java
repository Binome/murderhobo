package com.github.binome.murderhobo.scenes;
import java.util.LinkedList;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.entities.Arrow;
import com.github.binome.murderhobo.entities.Hero;
import com.github.binome.murderhobo.entities.Monster;
import com.github.binome.murderhobo.entities.Treasure;
import com.github.binome.murderhobo.map.Cell;

public abstract class Level extends Scene{
	public final int CELLS_WIDE = 0;
	public final int CELLS_TALL = 0;
	protected boolean murderMode;
	protected int xScale = 400;
	protected int yScale = 300;

	private Scene nextScene;
	
	public static Level instance;
	protected Cell[][] grid;

	protected static LinkedList<Arrow> arrows;
	protected static LinkedList<Monster> monsters;
	protected static LinkedList<Treasure> treasures;
	
	public void addArrow(Arrow a){
		arrows.add(a);
	}
	
	public Cell[][] getGrid(){
		return grid;
	}

	public Level getInstance() {
		return null;
	}

	protected void drawGUI() {
		int guiX = Hero.getInstance().getX() - xScale;
		int guiY = Hero.getInstance().getY() - yScale;
		Main.guiBG.setLoc(guiX, guiY);
		Main.guiBG.draw();
		Main.guiFont.drawString(guiX,guiY,
				"Gold: " + Hero.getInstance().getScore());
		
	}
	
	public Scene nextScene() {
		return nextScene;
	}
	
	public abstract void spawnTreasure(int x, int y, int value);
}
