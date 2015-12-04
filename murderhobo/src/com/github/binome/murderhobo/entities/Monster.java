package com.github.binome.murderhobo.entities;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector2f;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.map.Cell;
import com.github.binome.murderhobo.map.Pathfinder;
import com.github.binome.murderhobo.scenes.Level;

public class Monster extends InertialSprite {
	private static final int ATK_RANGE = 32;
	private boolean hostile;
	private int idleCount = 0;
	private float health = 2.0f;
	private static final int ATTACK_DELAY = 2000;
	private static final int ATK_POWER = 1;
	private int atkGap = 0;

	private float speed = 0.075f;
	private float maxSpeed = 0.27f;
	private float friction = 0.025f;

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
			
			if (!monLoc.isPassable()){
				if (lvl.getGrid()[monLoc.getX()+1][monLoc.getY()].isPassable()){
					monLoc=lvl.getGrid()[monLoc.getX()+1][monLoc.getY()];
				} else if (lvl.getGrid()[monLoc.getX()][monLoc.getY()+1].isPassable()){
					monLoc=lvl.getGrid()[monLoc.getX()][monLoc.getY()+1];
				} else {
					monLoc=lvl.getGrid()[monLoc.getX()+1][monLoc.getY()+1];
				}
			}
			int oldY = getY();
			int oldX = getX();

			ArrayList<Cell> pathToHero = Pathfinder.findPath(monLoc, heroLoc, lvl.getGrid());
			if (pathToHero.size() > 1) {
				Cell next = pathToHero.get(pathToHero.size() - 1);
				System.out.println("Second to last: " + next.getX() + " " + next.getY() + 
						" First: " + pathToHero.get(0).getX() + " "+ pathToHero.get(0).getY());
				if (next.getX() * Reference.GRID_SIZE < getX()) {
					Vector2f.add(velocity, new Vector2f(-1 * speed, 0), velocity);
				} else if (next.getX() * Reference.GRID_SIZE > getX()) {
					Vector2f.add(velocity, new Vector2f(speed, 0), velocity);
				}

				if (next.getY() * Reference.GRID_SIZE < getY()) {
					Vector2f.add(velocity, new Vector2f(0, -1 * speed), velocity);
				} else if (next.getY() * Reference.GRID_SIZE > getY()) {
					Vector2f.add(velocity, new Vector2f(0, speed), velocity);
				}
			} else if (Math.abs(Hero.getInstance().getX() - getX()) > ATK_RANGE
					|| Math.abs(Hero.getInstance().getY() - getY()) > ATK_RANGE) {
				if (Math.abs(Hero.getInstance().getX() - getX()) > ATK_RANGE && getX() > Hero.getInstance().getX()) {
					Vector2f.add(velocity, new Vector2f(-1 * speed, 0), velocity);
				} else if (Math.abs(Hero.getInstance().getX() - getX()) > ATK_RANGE
						&& getX() < Hero.getInstance().getX()) {
					Vector2f.add(velocity, new Vector2f(speed, 0), velocity);
				}
				if (Math.abs(Hero.getInstance().getY() - getY()) > ATK_RANGE && getY() > Hero.getInstance().getY()) {
					Vector2f.add(velocity, new Vector2f(0, -1 * speed), velocity);
				} else if (Math.abs(Hero.getInstance().getY() - getY()) > ATK_RANGE
						&& getY() < Hero.getInstance().getY()) {
					Vector2f.add(velocity, new Vector2f(0, speed), velocity);
				}
			}

			tweakSpeed();

			super.update(delta);

			// Check wall collision
			ArrayList<Cell> in = inCells(lvl.getInstance());
			Iterator<Cell> it = in.iterator();
			Cell c;
			while (it.hasNext()) {
				c = it.next();
				if (!c.isPassable()) {
					{
						System.out.println("In unpassable cell " + c.getX() + " " + c.getY());
						if (oldY + getHeight() <= c.getHitBox().getY()) {
							setLoc(getX(), c.getHitBox().getY() - getHeight());
							velocity.setY(0.0f);
						}
						if (oldY >= c.getHitBox().getY() + c.getHitBox().getHeight()) {
							setLoc(getX(), c.getHitBox().getY() + c.getHitBox().getHeight());
							if (velocity.getY() < 0.0f) {
								velocity.setY(0.0f);
							}
						}

						if (oldX + getWidth() <= c.getHitBox().getX()) {
							setLoc(oldX, getY());
							if (velocity.getX() > 0.0f) {
								velocity.setX(0.0f);
							}
						}
						if (oldX >= c.getHitBox().getX() + c.getHitBox().getWidth()) {
							setLoc(oldX, getY());
							if (velocity.getX() < 0.0f) {
								velocity.setX(0.0f);
							}
						}
					}
				}
			}
			if (atkGap <= 0 && Math.abs(Hero.getInstance().getX() - getX()) < ATK_RANGE
					&& Math.abs(Hero.getInstance().getY() - getY()) < ATK_RANGE) {
				hurtPlayer(lvl);
				atkGap = ATTACK_DELAY;
			}

			if (atkGap > 0) {
				atkGap = (int) (atkGap - delta);
			}
		}

	}

	protected void tweakSpeed() {
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

		// stop "creep" from friction bouncing back and forth between positive
		// and negative
		if (Math.abs((float) velocity.getX()) < friction) {
			velocity.setX(0.0f);
		}
		if (Math.abs((float) velocity.getY()) < friction) {
			velocity.setY(0.0f);
		}
	}

	public void applyAttack(Vector2f v, Level lvl) {
		health = health - v.length();
		Main.aman.play("monsterHurt");
		lvl.howlAt(getX(),getY());
		if (health <= 0) {
			die(lvl.getInstance());
		}
		goHostile();
	}

	public void die(Level lvl) {
		System.out.println("Mob slain.");
		isActive = false;
		lvl.getInstance().spawnTreasure(getX(), getY(), Reference.MOB_LOOT);
		Hero.getInstance().addKill();
	}

	public void goHostile() {
		hostile = true;
	}

	public boolean isHostile() {
		return hostile;
	}

	private void hurtPlayer(Level lvl) {
		Hero.getInstance().applyWound(ATK_POWER, lvl);
	}
}
