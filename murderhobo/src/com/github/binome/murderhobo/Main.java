package com.github.binome.murderhobo;
// skeleton game driver

// ctanis 8/19/15

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.lwjgl.LWJGLException;

import com.github.binome.murderhobo.entities.Hero;
import com.github.binome.murderhobo.entities.SimpleEntity;
import com.github.binome.murderhobo.scenes.Level1;
import com.github.binome.murderhobo.scenes.Scene;
import com.github.binome.murderhobo.scenes.StartMenu;

import java.io.IOException;

/**
 * @author SkylarRowan Main class for Murderhobo
 *
 */
public class Main {

	public static AudioManager aman;
	public static SpriteManager spriteMan;
	public static AngelCodeFont guiFont;
	public static SimpleEntity guiBG;
	
	/**
	 * Main loop
	 * 
	 * @param args
	 *            Ignored at this time.
	 * @throws LWJGLException
	 * @throws IOException
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws LWJGLException, IOException, SlickException {
		initGL(Reference.SCR_WIDTH, Reference.SCR_HEIGHT);
		initAudio();
		initSprites();
		initGUI();

		StartMenu menu = new StartMenu();
		Scene currScene = menu;
		
        while (currScene.go() )
        {
        	System.out.println("Scene is: " + currScene);
             // if nextScene() returns null (the default) reload the menu
            currScene = currScene.nextScene();

            if (currScene == null)
            {
            	//System.out.println ("currScene is null");
                currScene = menu;
            }

            //System.out.println("Changing Scene: " + currScene);
        }
		
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
		
		aman.loadSample("shoot", "res/audio/sfx/shoot.wav");
		aman.loadSample("playerHurt", "res/audio/sfx/playerHurt.wav");
		aman.loadSample("monsterHurt", "res/audio/sfx/monsterHurt.wav");
		aman.loadSample("money", "res/audio/sfx/money.wav");
	}
	
	public static void initSprites() throws SlickException {
		spriteMan = SpriteManager.getInstance();
		
		spriteMan.loadSpriteSheet("floor", "res/dawnlike/Objects/Floor.png");
		spriteMan.loadSpriteSheet("wall", "res/dawnlike/Objects/Wall.png");
		
		spriteMan.loadSpriteSheet("treasure", "res/dawnlike/Items/Money.png");
		
		spriteMan.loadSpriteSheet("hero", "res/antifarea/ranger_f.png",32,36);
		spriteMan.loadSpriteSheet("monster", "res/antifarea/monster.png",32,36);
		
		spriteMan.loadSpriteSheet("arrowE", "res/arrowR.png");
		spriteMan.loadSpriteSheet("arrowNE", "res/arrowNE.png");
		
		spriteMan.loadSpriteSheet("GUI", "res/dawnlike/GUI/GUI0.png");
		spriteMan.loadSpriteSheet("POW", "res/pow.png",32,32);
	}
	
	public static void initGUI() throws SlickException {
		Image img = new Image("res/font/League_Gothic_0.png",false,Image.FILTER_NEAREST);
		guiFont = new AngelCodeFont("res/font/League_Gothic.fnt", img);
		guiBG = new SimpleEntity(Reference.SCR_WIDTH,32,(Color) Color.BLACK);
	}

	public static void reset() {
		Level1.reset();
		Hero.getInstance().reset();
	}
}