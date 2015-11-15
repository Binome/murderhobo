package com.github.binome.murderhobo.entities;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;

public class InertialSprite extends SpriteEntity {

	protected Vector2f velocity = new Vector2f(0, 0); // start out stationary
	protected float mass;
	protected float speed;

	public InertialSprite(int width, int height, Image i) {
		super(width, height, i);
	}

	public void update(float delta) {
		hitBox.setX((int) (hitBox.getX() + velocity.getX() * delta));
		hitBox.setY((int) (hitBox.getY() + velocity.getY() * delta));
	}
}
