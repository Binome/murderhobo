package com.github.binome.murderhobo.entities;

import org.newdawn.slick.Image;

import com.github.binome.murderhobo.Main;

public class Treasure extends SpriteEntity {
	private int value;
	
	public Treasure(int value) {
		super(16, 16, Main.spriteMan.get("treasure").getSprite(1, 1));
		this.value=value;
	}

	public void draw() {
		img.draw(getX(), getY(), 2.0f);
	}
	
	public int getValue(){
		return value;
	}
}
