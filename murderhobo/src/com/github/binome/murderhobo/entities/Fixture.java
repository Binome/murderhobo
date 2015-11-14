package com.github.binome.murderhobo.entities;

import org.lwjgl.util.Color;

public class Fixture extends SimpleEntity {

	public Fixture(int width, int height, String pngpath) {
		super(width, height, pngpath);
	}

	public Fixture(int width, int height, Color c) {
		super(width, height, c);
	}

	public Fixture(int x, int y, int width, int height, Color c) {
		super(x, y, width, height, c);
	}

}
