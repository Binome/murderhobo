package com.github.binome.murderhobo.entities;

import org.lwjgl.util.Color;

public class Fixture extends Entity {

	public Fixture(int width, String pngpath) {
		super(width, pngpath);
	}

	public Fixture(int width, int height, Color c) {
		super(width, height, c);
	}

	public Fixture(int x, int y, int width, int height, Color c) {
		super(x, y, width, height, c);
	}

}
