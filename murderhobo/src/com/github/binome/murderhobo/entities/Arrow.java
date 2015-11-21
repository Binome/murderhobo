package com.github.binome.murderhobo.entities;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;

import com.github.binome.murderhobo.map.Cell;

public class Arrow extends InertialSprite {
	private float drag = 0.01f;
	
	public Arrow(Image i, Vector2f v) {
		super(16, 16, i);
		velocity = v;
	}

	@Override
	public void update(float delta) {
		hitBox.setX((int) (hitBox.getX() + velocity.getX() * delta));
		hitBox.setY((int) (hitBox.getY() + velocity.getY() * delta));
		
		// apply drag
		if (velocity.getX() > 0.0f) {
			Vector2f.add(velocity, new Vector2f(-1 * drag, 0), velocity);
		}
		if (velocity.getX() < 0.0f) {
			Vector2f.add(velocity, new Vector2f(drag, 0), velocity);
		}
		if (velocity.getY() > 0.0f) {
			Vector2f.add(velocity, new Vector2f(0, -1 * drag), velocity);
		}
		if (velocity.getY() < 0.0f) {
			Vector2f.add(velocity, new Vector2f(0, drag), velocity);
		}
		
		
		//unmake arrow if it's moving too slowly
		if (Math.abs((float)velocity.getX()) < drag  && Math.abs((float)velocity.getY()) < drag){
			isActive = false;
		}
	}
}
