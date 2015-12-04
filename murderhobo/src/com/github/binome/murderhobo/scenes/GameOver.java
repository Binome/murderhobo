package com.github.binome.murderhobo.scenes;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.entities.Fixture;
import com.github.binome.murderhobo.entities.Hero;

public class GameOver extends Scene {
	private int score;
	private int kills;
	private Scene nextScene;

	public GameOver(int score, int kills) {
		this.score = score;
		this.kills = kills;
		nextScene = this;
	}

	@Override
	public boolean drawFrame(float delta) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Reference.SCR_WIDTH, Reference.SCR_HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		String s0;
		if (Hero.getInstance().getHealth() <= 0) {
			s0 = "You have been slain!";
		} else {
			s0 = "You are victorious!";
		}
		Main.guiFont.drawString((Reference.SCR_WIDTH - Main.guiFont.getWidth(s0)) / 2, 0.0f, s0, Color.red);

		String s1 = "You collected " + score + " gold!";
		Main.guiFont.drawString((Reference.SCR_WIDTH - Main.guiFont.getWidth(s1)) / 2, 64, s1);
		if (kills != 1) {
			String s2 = "And killed " + kills + " innocent monsters!";
			Main.guiFont.drawString((Reference.SCR_WIDTH - Main.guiFont.getWidth(s2)) / 2, 96, s2);
		} else {
			String s3 = "And killed only 1 innocent monster!";
			Main.guiFont.drawString((Reference.SCR_WIDTH - Main.guiFont.getWidth(s3)) / 2, 96, s3);
		}
		if (score == 0 && kills == 0) {
			String s4 = "I feel like you may have missed the point of the game...";
			Main.guiFont.drawString((Reference.SCR_WIDTH - Main.guiFont.getWidth(s4)) / 2, 128, s4);
		}

		Fixture quit = new Fixture(256, 128, "res/title/QUIT-End.png");
		quit.setLoc((Reference.SCR_WIDTH - quit.getWidth()) / 2, (int) (.8 * Reference.SCR_HEIGHT));
		quit.draw();
		
		Fixture replay = new Fixture(512, 128, "res/title/PLAY-AGAIN.png");
		replay.setLoc((Reference.SCR_WIDTH - replay.getWidth()) / 2, (int) (.6 * Reference.SCR_HEIGHT));
		replay.draw();

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
					if (replay.getHitBox().contains(p)) {
						// System.out.println("Clicked quit.");
						Main.reset();
						nextScene = new StartMenu();
						return false;
					}
				}
			}
		}

		return true;
	}

	public Scene nextScene() {
		return nextScene;
	};

}
