package com.github.binome.murderhobo.scenes;

import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.newdawn.slick.openal.SoundStore;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.entities.Fixture;

public class StartMenu extends Scene {
	private Fixture title;
	private Fixture playGame;
	private Fixture quit;

	private LinkedList<Fixture> fixtures = new LinkedList<Fixture>();

	private Scene nextScene;

	/**
	 * Constructor. Plays title music and initializes graphics.
	 */
	public StartMenu() {
		nextScene = this;

		Main.aman.play("title-bgm");
		SoundStore.get().setCurrentMusicVolume(Reference.musicVolume);

		initTitle();
	}

	/**
	 * @see com.github.binome.murderhobo.scenes.Scene#drawFrame(float)
	 */
	@Override
	public boolean drawFrame(float delta) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Reference.SCR_WIDTH, Reference.SCR_HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		drawTitle();
		checkInput();

		if (nextScene == this){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks for mouse-clicks
	 */
	private void checkInput() {
		// Has the user clicked a button?
		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) {
				if (Mouse.getEventButton() == 0) {
					int x = Mouse.getEventX();
					int y = Display.getHeight() - Mouse.getEventY();
					Point p = new Point(x, y);

					// System.out.println("Clicked at " + x + "," + y);

					if (quit.getHitBox().contains(p)) {
						// System.out.println("Clicked quit.");
						exit();
					}
					if (playGame.getHitBox().contains(p)) {
						nextScene = new Level1();
					}
				}
			}
		}

	}

	private void initTitle() {
		title = new Fixture(1024, 128, "res/title/Murderhobo.png");
		title.setLoc((Reference.SCR_WIDTH - title.getWidth()) / 2, (int) (.05 * Reference.SCR_HEIGHT));
		fixtures.add(title);

		playGame = new Fixture(256, 32, "res/title/PLAY-GAME.png");
		playGame.setLoc((Reference.SCR_WIDTH - playGame.getWidth()) / 2, (int) (.5 * Reference.SCR_HEIGHT));
		fixtures.add(playGame);

		quit = new Fixture(128, 32, "res/title/QUIT.png");
		quit.setLoc((Reference.SCR_WIDTH - quit.getWidth()) / 2,
				(int) (.5 * Reference.SCR_HEIGHT) + playGame.getHeight() + 48);
		fixtures.add(quit);
		// System.out.println(quit.getX()+" "+quit.getX()+quit.getWidth()+"
		// "+quit.getY()+" "+quit.getY()+quit.getHeight());
	}

	public Scene nextScene() {
		return nextScene;
	}

	private void drawTitle() {
		Iterator<Fixture> it = fixtures.iterator();
		Fixture fix;
		while (it.hasNext()) {
			fix = it.next();
			fix.draw();
		}
	}

}
