package com.github.binome.murderhobo;
// skeleton game driver

// ctanis 8/19/15

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import org.lwjgl.input.Keyboard;

import org.lwjgl.LWJGLException;
import org.lwjgl.BufferUtils;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.github.binome.murderhobo.scenes.StartMenu;

import org.newdawn.slick.Color;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import org.newdawn.slick.openal.SoundStore;

/**
 * @author SkylarRowan Main class for Murderhobo
 *
 */
public class Main {

	public static AudioManager aman;

	/**
	 * Main loop
	 * 
	 * @param args
	 *            Ignored at this time.
	 * @throws LWJGLException
	 * @throws IOException
	 */
	public static void main(String[] args) throws LWJGLException, IOException {
		initGL(Settings.SCR_WIDTH, Settings.SCR_HEIGHT);
		initAudio();
		new StartMenu().go();
		Display.destroy();
		aman.destroy();
	}

	/**
	 * Initializes graphics
	 * 
	 * @param width
	 * @param height
	 * @throws LWJGLException
	 */
	public static void initGL(int width, int height) throws LWJGLException {
		// open window of appropriate size
		Display.setDisplayMode(new DisplayMode(width, height));
		Display.create();
		Display.setVSyncEnabled(true);

		// enable 2D textures
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		// disable the OpenGL depth test since we're rendering 2D graphics
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		// set "clear" color
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// set viewport to entire window
		GL11.glViewport(0, 0, width, height);

		// set up orthographic projection
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		// GLU.gluPerspective(90f, 1.333f, 2f, -2f);
		// GL11.glTranslated(0, 0, -500);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	/**
	 * Initializes audio for the game
	 * 
	 * @throws IOException
	 */
	public static void initAudio() throws IOException {
		aman = AudioManager.getInstance();
		aman.loadLoop("title-bgm", "res/audio/music/8bit.ogg");
		aman.loadLoop("level1-peaceful", "res/audio/music/Dungeon_Crawl.ogg");
		aman.loadLoop("level1-murder", "res/audio/music/Dungeon_Boss.ogg");

	}
}