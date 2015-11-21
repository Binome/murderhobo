package com.github.binome.murderhobo.scenes;


import com.github.binome.murderhobo.map.Cell;

public abstract class Level extends Scene{
	public final int CELLS_WIDE = 0;
	public final int CELLS_TALL = 0;

	public static Level instance;
	protected Cell[][] grid;

	public Cell[][] getGrid(){
		return grid;
	}

	public Level getInstance() {
		return null;
	}
}
