package com.github.binome.murderhobo;

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class SpriteManager {
	private HashMap<String, SpriteSheet> sprites;

	private static SpriteManager instance;

	/**
	 * @return the singleton AudioManager instance
	 */
	public static SpriteManager getInstance() {
		if (instance == null)
			instance = new SpriteManager();
		return instance;
	}

	private SpriteManager() {
		sprites = new HashMap<String, SpriteSheet>();
	}

	public void loadSpriteSheet(String name, String path) throws SlickException {
		SpriteSheet s = new SpriteSheet(path, 16, 16);
		sprites.put(name, s);
	}
	
	public void loadSpriteSheet(String name, String path, int texW, int texH) throws SlickException{
		SpriteSheet s = new SpriteSheet(path, texW, texH);
		sprites.put(name, s);
	}

	public SpriteSheet get(String name) {
		return sprites.get(name);
	}

}