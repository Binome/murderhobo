package com.github.binome.murderhobo.entities;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Image;

public class SpriteEntity extends Entity {
	private Image img;
	
	public SpriteEntity(int width, int height, Image i) {
		hitBox = new Rectangle(0, 0, width, height);
		img = i;
	}
	
	public void draw(){
		img.draw(getX(), getY(), 1.0f);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

}
