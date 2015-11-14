package com.github.binome.murderhobo;

import java.io.IOException;
import java.util.HashMap;

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

	public void loadSpriteSheet(String name, String path) throws IOException {
		
	}

	public SpriteSheet get(String name) {
		return sprites.get(name);
	}

}
