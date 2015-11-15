package com.github.binome.murderhobo.entities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public abstract class Entity {

	protected Rectangle hitBox;
	protected boolean isActive = true;
	
	protected Entity(){}

	public void setLoc(int x, int y) {
		hitBox.setX(x);
		hitBox.setY(y);
	}

	public void init() {
	}

	public void destroy() {

	}

	public abstract void update(float delta);

	public abstract void draw();

	public boolean testCollision(Entity e) {
		if (hitBox.intersects(e.hitBox))
			return true;
		else
			return false;
	}

	public void onCollision() {
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

}