package com.github.binome.murderhobo.entities;

import java.util.ArrayList;
import java.util.Iterator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.map.Cell;

public class Hero extends InertialSprite {
	private float speed = 0.05f;
	private float maxSpeed = 0.3f;
	private float friction = 0.025f;
	Cell[][] grid;

	private static Hero ourHero;

	public static Hero getInstance(Cell[][] grid) {
		if (ourHero == null)
			ourHero = new Hero(32, 32, Main.spriteMan.get("hero").getSprite(1, 2), grid);
		return ourHero;
	}

	public Hero(int width, int height, Image i, Cell[][] grid) {
		super(width, height, i);
		this.grid = grid;
	}

	public void draw() {
		// 4 pixel offset to allow for head to slightly overlap tile above
		img.draw(getX() - 4, getY(), 1.0f);
	}

	public void update(float delta, Cell[][] grid) {
		this.grid = grid;

		int oldX = getX();
		int oldY = getY();

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)) {
			Vector2f.add(velocity, new Vector2f(-1 * speed, 0), velocity);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
			Vector2f.add(velocity, new Vector2f(speed, 0), velocity);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) {
			Vector2f.add(velocity, new Vector2f(0, -1 * speed), velocity);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) {
			Vector2f.add(velocity, new Vector2f(0, speed), velocity);
		}

		tweakSpeed();

		super.update(delta);

		ArrayList<Cell> in = inCells();
		Iterator<Cell> it = in.iterator();
		Cell c;
		Rectangle r = null;
		while (it.hasNext()) {
			c = it.next();
			if (!c.passable) {
				{

					if (oldY + ourHero.getHeight() <= c.getHitBox().getY()) {
						ourHero.setLoc(ourHero.getX(), c.getHitBox().getY() - ourHero.getHeight());
						ourHero.velocity.setY(0.0f);
					}
					if (oldY >= c.getHitBox().getY() + c.getHitBox().getHeight()) {
						ourHero.setLoc(ourHero.getX(), c.getHitBox().getY() + c.getHitBox().getHeight());
						if (ourHero.velocity.getY() < 0.0f) {
							ourHero.velocity.setY(0.0f);
						}
					}

					if (oldX + ourHero.getWidth() <= c.getHitBox().getX()) {
						ourHero.setLoc(oldX, ourHero.getY());
						if (ourHero.velocity.getX() > 0.0f) {
							ourHero.velocity.setX(0.0f);
						}
					}
					if (oldX >= c.getHitBox().getX() + c.getHitBox().getWidth()) {
						ourHero.setLoc(oldX, ourHero.getY());
						if (ourHero.velocity.getX() < 0.0f) {
							ourHero.velocity.setX(0.0f);
						}
					}

				}
			}
		}

	}

	public ArrayList<Cell> inCells() {
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

	private void tweakSpeed() {
		// enforce speed limit
		if (Math.abs(velocity.getX()) > maxSpeed) {
			if (velocity.getX() > 0) {
				velocity.setX(maxSpeed);
			} else {
				velocity.setX(-1 * maxSpeed);
			}
		}
		if (Math.abs(velocity.getY()) > maxSpeed) {
			if (velocity.getY() > 0) {
				velocity.setY(maxSpeed);
			} else {
				velocity.setY(-1 * maxSpeed);
			}
		}

		// apply friction
		if (velocity.getX() > 0.0f) {
			Vector2f.add(velocity, new Vector2f(-1 * friction, 0), velocity);
		}
		if (velocity.getX() < 0.0f) {
			Vector2f.add(velocity, new Vector2f(friction, 0), velocity);
		}
		if (velocity.getY() > 0.0f) {
			Vector2f.add(velocity, new Vector2f(0, -1 * friction), velocity);
		}
		if (velocity.getY() < 0.0f) {
			Vector2f.add(velocity, new Vector2f(0, friction), velocity);
		}
		
		//stop "creep" from friction bouncing back and forth between positive and negative
		if (Math.abs((float)velocity.getX()) < friction){
			velocity.setX(0.0f);
		}
		if (Math.abs((float)velocity.getY()) < friction){
			velocity.setY(0.0f);
		}
	}
}