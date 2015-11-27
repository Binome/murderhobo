package com.github.binome.murderhobo.entities;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;

public class Monster extends SpriteEntity {
	private boolean hostile;
	private int idleCount = 0;
	
	public Monster(){
		super(32, 36, Main.spriteMan.get("monster").getSprite(1, 2));
		hostile = false;
	}

	public void draw() {
		// 4 pixel offset to allow for head to slightly overlap tile above
		img.draw(getX() - 4, getY(), 1.0f);
	}
	
	public void update(float delta){
		//Idle animation until hostility has been triggered
		if (!hostile){
			idleCount += delta;
			if (idleCount <= Reference.IDLE_CYCLE){
				img = Main.spriteMan.get("monster").getSprite(0, 2);
			} else if (idleCount <= Reference.IDLE_CYCLE * 2){
				img = Main.spriteMan.get("monster").getSprite(2, 2);
			} else {
				idleCount=0;
			}
		}
		if (hostile){

		}
	}
}
