package com.github.binome.murderhobo.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
//import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.map.Cell;
import com.github.binome.murderhobo.scenes.Level;

public class Hero extends InertialSprite {
	private final float ARROW_MAX = 2.0f;
	private final int STARTING_HEALTH = 4;
	private int score;
	private float speed = 0.05f;
	private float maxSpeed = 0.32f;
	private float friction = 0.025f;
	private float arrowCharge = 0;
	private int health;
	private int killCount;
	private final int INVULN_TIME = 500;
	private float invulnTimer = INVULN_TIME;

	private static Hero ourHero;

	private LinkedList<Arrow> arrows = new LinkedList<Arrow>();

	public static Hero getInstance() {
		if (ourHero == null)
			ourHero = new Hero(32, 32, Main.spriteMan.get("hero").getSprite(1, 2));
		return ourHero;
	}

	public Hero(int width, int height, Image i) {
		super(width, height, i);
		killCount = 0;
		health = STARTING_HEALTH;
	}

	public void draw() {
		// 4 pixel offset to allow for head to slightly overlap tile above
		img.draw(getX() - 4, getY(), 1.0f);
	}

	public void update(float delta, Level lvl) {
		int oldX = getX();
		int oldY = getY();

		// movement
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

		// shoot
		if (Mouse.isButtonDown(0)) {
			if (arrowCharge == 0) {
				arrowCharge = 0.05f;
			} else {
				arrowCharge = arrowCharge + delta * 0.001f;
			}
			if (arrowCharge >= ARROW_MAX) {
				arrowCharge = ARROW_MAX;
			}
		}
		if (!Mouse.isButtonDown(0) && arrowCharge > 0) {
			float arrowSpeed = 1.0f * arrowCharge;
			float mouseX = getX() + (Mouse.getX() - Reference.SCR_WIDTH / 2);
			float mouseY = getY() + (Mouse.getY() - Reference.SCR_HEIGHT / 2);

			Vector2f vToMouse = new Vector2f(mouseX - getX(), getY() - mouseY);
			vToMouse.normalise();
			vToMouse.scale(arrowSpeed);

			// TODO figure out which arrow to draw
			// double angle = Math.atan2(vToMouse.getY(), vToMouse.getX());
			// System.out.println(angle);
			Image arrowImg = Main.spriteMan.get("arrowE"); // Make to specific
															// image
			Arrow arr = new Arrow(arrowImg, vToMouse);
			arr.setLoc(getX() + getWidth() / 2, getY() + getHeight() / 2);

			lvl.addArrow(arr);
			Main.aman.play("shoot");
			arrowCharge = 0.0f;
		}

		tweakSpeed();

		super.update(delta);

		if (invulnTimer < INVULN_TIME) {
			invulnTimer = (invulnTimer + delta);
			if (invulnTimer > INVULN_TIME) {
				invulnTimer = INVULN_TIME;
			}
		}

		// Check wall collision
		ArrayList<Cell> in = inCells(lvl.getInstance());
		Iterator<Cell> it = in.iterator();
		Cell c;
		while (it.hasNext()) {
			c = it.next();
			if (!c.isPassable()) {
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

	public void addArrow(Arrow a) {
		arrows.add(a);
	}

	public void addScore(int i) {
		score = score + i;
	}

	public void subScore(int i) {
		score = score - i;
	}

	public int getScore() {
		return score;
	}

	public int getKillCount() {
		return killCount;
	}

	public void addKill() {
		killCount++;
	}

	public int getHealth() {
		return health;
	}

	public void applyWound(int damage, Level lvl) {
		if (invulnTimer >= INVULN_TIME){
			health = health - damage;
			Main.aman.play("playerHurt");
			lvl.getInstance().applyPow();
			invulnTimer = 0;
		}
	}

	public void reset() {
		health = STARTING_HEALTH;
		score = 0;
		killCount = 0;
		velocity = new Vector2f(0.0f, 0.0f);
	}
}