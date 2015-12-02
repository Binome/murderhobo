package com.github.binome.murderhobo.scenes;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.entities.Fixture;

public class WinScreen extends Scene {
	private int score;
	private int kills;
	private Scene nextScene;

	public WinScreen(int score, int kills) {
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

		Main.guiFont.drawString(0, 0, "You collected " + score + " gold!");
		if (kills != 1) {
			Main.guiFont.drawString(0, 32, "And killed " + kills + " innocent monsters!");
		} else {
			Main.guiFont.drawString(0, 32, "And killed only 1 innocent monster!");
		}
		if (score == 0 && kills == 0) {
			Main.guiFont.drawString(0, 64, "I feel like you may have missed the point of the game...");
		}
		
		Fixture quit = new Fixture(256, 128, "res/title/Quit-end.png");
		quit.setLoc((Reference.SCR_WIDTH - quit.getWidth()) / 2, (int) (.8 * Reference.SCR_HEIGHT));
		quit.draw();
		
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
				}
			}
		}
		
		return true;
	}

	public Scene nextScene() {
		return nextScene;
	};

}
