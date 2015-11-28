package com.github.binome.murderhobo.entities;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.map.Cell;
import com.github.binome.murderhobo.map.Pathfinder;
import com.github.binome.murderhobo.scenes.Level;

public class Monster extends SpriteEntity {
	private int speed = 4;
	private boolean hostile;
	private int idleCount = 0;
	private float health = 2.0f;

	public Monster() {
		super(32, 36, Main.spriteMan.get("monster").getSprite(1, 2));
		hostile = false;
	}

	public void draw() {
		// 4 pixel offset to allow for head to slightly overlap tile above
		img.draw(getX() - 4, getY(), 1.0f);
	}

	public void update(float delta, Level lvl) {
		// Idle animation until hostility has been triggered
		if (!hostile) {
			idleCount += delta;
			if (idleCount <= Reference.IDLE_CYCLE) {
				img = Main.spriteMan.get("monster").getSprite(0, 2);
			} else if (idleCount <= Reference.IDLE_CYCLE * 2) {
				img = Main.spriteMan.get("monster").getSprite(2, 2);
			} else {
				idleCount = 0;
			}
		}
		if (hostile) {
			Cell heroLoc = Hero.getInstance().getLocInLvl(lvl);
			Cell monLoc = getLocInLvl(lvl);
			System.out.println("Hero: " + heroLoc.getX() + " " + heroLoc.getY());
			System.out.println("Mon: " + monLoc.getX() + " " + monLoc.getY());

			ArrayList<Cell> pathToHero = Pathfinder.findPath(monLoc, heroLoc, lvl.getGrid());
			if (pathToHero.size() > 1) {
				Cell next = pathToHero.get(pathToHero.size() - 1);
				if (next.getX() * Reference.GRID_SIZE < getX()) {
					hitBox.setX(getX() - speed);
				}else if (next.getX() * Reference.GRID_SIZE > getX()){
					hitBox.setX(getX() + speed);
				}
				
				if (next.getY() * Reference.GRID_SIZE < getY()) {
					hitBox.setY(getY() - speed);
				}else if (next.getY() * Reference.GRID_SIZE > getY()){
					hitBox.setY(getY() + speed);
				}
			}
		}
	}

	public void applyAttack(Vector2f v) {
		health = health - v.length();
		if (health <= 0) {
			die();
		}
		goHostile();
	}

	public void die() {
		isActive = false;
	}

	public void goHostile() {
		hostile = true;
	}

	public boolean isHostile() {
		return hostile;
	}
}
