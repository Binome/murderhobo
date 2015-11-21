package com.github.binome.murderhobo.entities;

import java.util.ArrayList;

import org.lwjgl.util.Rectangle;

import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.map.Cell;
import com.github.binome.murderhobo.scenes.Level;

public abstract class Entity {

	protected Rectangle hitBox;
	public boolean isActive = true;
	
	protected Entity(){}

	public void setLoc(int x, int y) {
		hitBox.setX(x);
		hitBox.setY(y);
	}

	public abstract void update(float delta);

	public abstract void draw();

	public boolean testCollision(Entity e) {
		if (hitBox.intersects(e.hitBox))
			return true;
		else
			return false;
	}

	public int getX() {
		return hitBox.getX();
	}

	public int getY() {
		return hitBox.getY();
	}

	public int getHeight() {
		return hitBox.getHeight();
	}

	public int getWidth() {
		return hitBox.getWidth();
	}

	public Rectangle getHitBox() {
		return hitBox;
	}
	
	public ArrayList<Cell> inCells(Level lvl) {
		Cell[][] grid = lvl.getInstance().getGrid();
		int lowerX = (int) Math.floor((double) getX() / (double) Reference.GRID_SIZE);
		int upperX = (int) Math.ceil((double) getX() / (double) Reference.GRID_SIZE);
		int lowerY = (int) Math.floor((double) getY() / (double) Reference.GRID_SIZE);
		int upperY = (int) Math.ceil((double) getY() / (double) Reference.GRID_SIZE);

		ArrayList<Cell> cellList = new ArrayList<Cell>();

		if (lowerX >= 0 && lowerY >= 0) {
			cellList.add(grid[lowerX][lowerY]);
		}
		if (upperX <= grid.length && lowerY >= 0) {
			cellList.add(grid[upperX][lowerY]);
		}
		if (upperX <= grid.length && upperY <= grid[0].length) {
			cellList.add(grid[upperX][upperY]);
		}
		if (lowerX >= 0 && upperY <= grid[0].length) {
			cellList.add(grid[lowerX][upperY]);
		}

		return cellList;
	}

}