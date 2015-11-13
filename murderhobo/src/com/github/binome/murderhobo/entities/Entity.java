package com.github.binome.murderhobo.entities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public abstract class Entity {

	protected Rectangle hitBox;
	protected float wr;
	protected float hr;
	protected final float SPEED = 0.05f;

	protected Texture texture;
	protected boolean hasTex;
	Color boxColor;

	protected boolean isActive = true;

	protected Entity(int width, String pngpath) {
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(pngpath));

			wr = (1.0f) * texture.getImageWidth() / texture.getTextureWidth();
			hr = (1.0f) * texture.getImageHeight() / texture.getTextureHeight();

			hitBox = new Rectangle(0, 0, width, (width * texture.getImageHeight()) / texture.getImageWidth());

			boxColor = new Color(1, 1, 1);
			hasTex = true;
		} catch (java.io.IOException e) {
			e.printStackTrace();
			System.err.println("Cannot open resource " + pngpath);
		}
	}

	protected Entity(int width, int height, Color c) {
		boxColor = c;
		hitBox = new Rectangle(0, 0, width, height);
		hasTex = false;
	}

	protected Entity(int x, int y, int width, int height, Color c) {
		boxColor = c;
		hitBox = new Rectangle(x, y, width, height);
		hasTex = false;
	}

	public void setLoc(int x, int y) {
		hitBox.setX(x);
		hitBox.setY(y);
	}

	public void init() {
	}

	public void destroy() {

	}

	public void update(float delta) {

	}

	public void draw() {
		if (!hasTex) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glColor3f(boxColor.getRed(), boxColor.getGreen(), boxColor.getBlue());

			GL11.glVertex2f(hitBox.getX(), hitBox.getY());
			GL11.glVertex2f(hitBox.getX() + hitBox.getWidth(), hitBox.getY());
			GL11.glVertex2f(hitBox.getX() + hitBox.getWidth(), hitBox.getY() + hitBox.getHeight());
			GL11.glVertex2f(hitBox.getX(), hitBox.getY() + hitBox.getHeight());
			// System.out.println("X: "+x+"Y: "+y+"W: "+w+"H: "+h);

			GL11.glEnd();
		} else {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(boxColor.getRed(), boxColor.getGreen(), boxColor.getRed());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

			GL11.glBegin(GL11.GL_QUADS);

			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(hitBox.getX(), hitBox.getY());

			GL11.glTexCoord2f(wr, 0);
			GL11.glVertex2f(hitBox.getX() + hitBox.getWidth(), hitBox.getY());

			GL11.glTexCoord2f(wr, hr);
			GL11.glVertex2f(hitBox.getX() + hitBox.getWidth(), hitBox.getY() + hitBox.getHeight());

			GL11.glTexCoord2f(0, hr);
			GL11.glVertex2f(hitBox.getX(), hitBox.getY() + hitBox.getHeight());

			GL11.glEnd();
		}
	}

	public boolean testCollision(Entity e) {
		if (hitBox.intersects(e.hitBox))
			return true;
		else
			return false;
	}

	public void onCollision() {
	}

	public int getX() {
		return hitBox.getX();
	}

	public int getY() {
		return hitBox.getY();
	}

	public int getHeight() {
		return hitBox.getHeight();
	}

	public int getWidth() {
		return hitBox.getWidth();
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

}