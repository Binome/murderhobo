package com.github.binome.murderhobo.scenes;
import java.util.LinkedList;

import com.github.binome.murderhobo.entities.Arrow;
import com.github.binome.murderhobo.entities.Monster;
import com.github.binome.murderhobo.entities.Treasure;
import com.github.binome.murderhobo.map.Cell;

public abstract class Level extends Scene{
	public final int CELLS_WIDE = 0;
	public final int CELLS_TALL = 0;
	protected boolean murderMode;

	public static Level instance;
	protected Cell[][] grid;

	protected LinkedList<Arrow> arrows;
	protected LinkedList<Monster> monsters;
	protected LinkedList<Treasure> treasures;
	
	public void addArrow(Arrow a){
		arrows.add(a);
	}
	
	public Cell[][] getGrid(){
		return grid;
	}

	public Level getInstance() {
		return null;
	}
}
