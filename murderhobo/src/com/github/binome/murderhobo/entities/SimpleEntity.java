package com.github.binome.murderhobo.entities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SimpleEntity extends Entity {

	protected Rectangle hitBox;

	protected Texture texture;
	protected boolean hasTex;
	
	protected int xTexOffset;
	protected int yTexOffset;
	protected int texWidth;
	protected int texHeight;
	
	protected float normalizedTexX;
	protected float normalizedTexY;
	protected float normalizedTexWidth;
	protected float normalizedTexHeight;
	
	protected float wr;
	protected float hr;
	Color boxColor;

	protected boolean isActive = true;
	
	public SimpleEntity(int width, int height, Color c) {
		boxColor = c;
		hitBox = new Rectangle(0, 0, width, height);
		hasTex = false;
	}

	public SimpleEntity(int x, int y, int width, int height, Color c) {
		boxColor = c;
		hitBox = new Rectangle(x, y, width, height);
		hasTex = false;
	}
	
	public SimpleEntity(int width, int height, String pngpath)
	{
		try
		{
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(pngpath));

			hitBox  = new Rectangle(0,0,width,height);
			xTexOffset = 0;
			yTexOffset = 0;
			texWidth  = width;
			texHeight = height;
			
			normalizedTexX = (float) xTexOffset/ (float) texture.getImageWidth();
			normalizedTexY = (float) yTexOffset/(float) texture.getImageHeight();
			normalizedTexWidth = (float) texWidth/(float) texture.getImageWidth();
			normalizedTexHeight = (float) texHeight/(float) texture.getImageHeight();
			
			boxColor = new Color(1,1,1);
			hasTex = true;
		}
		catch (java.io.IOException e)
		{
			e.printStackTrace();
			System.err.println("Cannot open resource " + pngpath);
		}
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
			
			GL11.glTexCoord2f(normalizedTexX,normalizedTexY);
			GL11.glVertex2f(hitBox.getX(), hitBox.getY());

			GL11.glTexCoord2f(normalizedTexX + normalizedTexWidth, normalizedTexY);
			GL11.glVertex2f(hitBox.getX() + hitBox.getWidth(), hitBox.getY());

			GL11.glTexCoord2f(normalizedTexX + normalizedTexWidth, normalizedTexY + normalizedTexHeight);
			GL11.glVertex2f(hitBox.getX() + hitBox.getWidth(), hitBox.getY() + hitBox.getHeight());

			GL11.glTexCoord2f(normalizedTexX, normalizedTexY + normalizedTexHeight);
			GL11.glVertex2f(hitBox.getX(), hitBox.getY() + hitBox.getHeight());

			GL11.glEnd();
		}
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