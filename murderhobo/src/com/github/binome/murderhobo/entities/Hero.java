package com.github.binome.murderhobo.entities;


import org.lwjgl.input.Keyboard;

public class Hero extends SpriteEntity{
	private int speed = 5;
	private int x;
	private int y;
	
	private static Hero ourHero;

	public static Hero getInstance() {
		if (ourHero == null)
			ourHero = new Hero();
		return ourHero;
	}

	private Hero() {
		//ourHero.super();
	}

	public void update(float delta)
    {
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			//hitBox.setX(getX() - speed);

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			//hitBox.setX(getX() - speed);

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			//hitBox.setX(getX() - speed);

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			//hitBox.setX(getX() - speed);

		}


		
	}
}