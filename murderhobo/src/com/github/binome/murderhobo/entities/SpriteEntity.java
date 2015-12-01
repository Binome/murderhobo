package com.github.binome.murderhobo.entities;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Image;

import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.map.Cell;
import com.github.binome.murderhobo.scenes.Level;

public class SpriteEntity extends Entity {
	protected Image img;
	
	public SpriteEntity(int width, int height, Image i) {
		hitBox = new Rectangle(0, 0, width, height);
		img = i;
	}
	
	public void draw(){
		img.draw(getX(), getY(), 1.0f);
	}

	@Override
	public void update(float delta) {
	}
	
	public Cell getLocInLvl(Level lvl){
		//Currently defaults to NW cell if Entity is in multiple Cells.
		return lvl.getGrid()[(int) Math.floor(getX()/Reference.GRID_SIZE)]
				[(int) Math.floor(getY()/Reference.GRID_SIZE)];
	}

} 
