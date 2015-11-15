package com.github.binome.murderhobo.entities;


import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Image;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.scenes.Level;

public class Hero extends SpriteEntity{
	private int speed = 5;
	Level lvl;

	private static Hero ourHero;

	public static Hero getInstance(Level lvl) {
		if (ourHero == null)
			ourHero = new Hero(32, 36, Main.spriteMan.get("hero").getSprite(1, 2), lvl);
		return ourHero;
	}

	public Hero(int width, int height, Image i, Level lvl) {
		super(width, height, i);
		this.lvl = lvl;
	}



	public void update(float delta)
    {
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			hitBox.setX(getX() - speed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			hitBox.setX(getX() + speed);

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			hitBox.setY(getY() - speed);

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			hitBox.setY(getY() + speed);
		}	
	}
}