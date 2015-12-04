package com.github.binome.murderhobo.scenes;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Image;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Reference;
import com.github.binome.murderhobo.entities.Arrow;
import com.github.binome.murderhobo.entities.Hero;
import com.github.binome.murderhobo.entities.Monster;
import com.github.binome.murderhobo.entities.Treasure;
import com.github.binome.murderhobo.map.Cell;

public abstract class Level extends Scene {
	public final int CELLS_WIDE = 0;
	public final int CELLS_TALL = 0;
	protected static boolean murderMode;
	protected int xScale = 400;
	protected int yScale = 300;
	
	private Scene nextScene;

	public static Level instance;
	protected static Cell[][] grid;

	protected static LinkedList<Arrow> arrows;
	protected static LinkedList<Monster> monsters;
	protected static LinkedList<Treasure> treasures;

	public void addArrow(Arrow a) {
		arrows.add(a);
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public Level getInstance() {
		return null;
	}

	protected void drawGUI() {
		int guiX = Hero.getInstance().getX() - xScale;
		int guiY = Hero.getInstance().getY() - yScale;
		Main.guiBG.setLoc(guiX, guiY);
		Main.guiBG.draw();
		Main.guiFont.drawString(guiX, guiY, "Gold: " + Hero.getInstance().getScore());
		
		
		Image fullHeart = Main.spriteMan.get("GUI").getSprite(0, 1);
		Image emptyHeart = Main.spriteMan.get("GUI").getSprite(0, 4);

		
		switch (Hero.getInstance().getHealth()) {
		case 1:
			emptyHeart.draw(guiX + Reference.GRID_SIZE * 4, guiY + (Reference.GRID_SIZE/4));
			emptyHeart.draw(guiX + Reference.GRID_SIZE * 5, guiY + (Reference.GRID_SIZE/4));
			emptyHeart.draw(guiX + Reference.GRID_SIZE * 6, guiY + (Reference.GRID_SIZE/4));
			fullHeart.draw( guiX + Reference.GRID_SIZE * 7, guiY + (Reference.GRID_SIZE/4));
			break;
		case 2:
			emptyHeart.draw(guiX + Reference.GRID_SIZE * 4, guiY + (Reference.GRID_SIZE/4));
			emptyHeart.draw(guiX + Reference.GRID_SIZE * 5, guiY + (Reference.GRID_SIZE/4));
			fullHeart.draw( guiX + Reference.GRID_SIZE * 6, guiY + (Reference.GRID_SIZE/4));
			fullHeart.draw( guiX + Reference.GRID_SIZE * 7, guiY + (Reference.GRID_SIZE/4));
			break;
		case 3:
			emptyHeart.draw(guiX + Reference.GRID_SIZE * 4, guiY + (Reference.GRID_SIZE/4));
			fullHeart.draw( guiX + Reference.GRID_SIZE * 5, guiY + (Reference.GRID_SIZE/4));
			fullHeart.draw( guiX + Reference.GRID_SIZE * 6, guiY + (Reference.GRID_SIZE/4));
			fullHeart.draw( guiX + Reference.GRID_SIZE * 7, guiY + (Reference.GRID_SIZE/4));
			break;
		case 4:
			fullHeart.draw( guiX + Reference.GRID_SIZE * 4, guiY + (Reference.GRID_SIZE/4));
			fullHeart.draw( guiX + Reference.GRID_SIZE * 5, guiY + (Reference.GRID_SIZE/4));
			fullHeart.draw( guiX + Reference.GRID_SIZE * 6, guiY + (Reference.GRID_SIZE/4));
			fullHeart.draw( guiX + Reference.GRID_SIZE * 7, guiY + (Reference.GRID_SIZE/4));
			break;
		default:
			Main.guiFont.drawString(Reference.SCR_WIDTH - 40, 0, "ERR");
			break;
		}

	}

	public Scene nextScene() {
		return nextScene;
	}

	public abstract void applyPow();
	
	public abstract void spawnTreasure(int x, int y, int value);

	public void howlAt(int x, int y) {
		Monster mon;
		Iterator<Monster> monIt = monsters.iterator();
		while (monIt.hasNext()) {
			mon = monIt.next();
			float distance = (float) Math.sqrt((mon.getX()-x)*(mon.getX()-x) + (mon.getY()-y)*(mon.getY()-y));
			if (distance <= Reference.HOWL_RANGE){
				mon.goHostile();
			}
		}
	}
	
	protected void processTreasure() {
		Treasure t;
		Iterator<Treasure> treasureIt = treasures.iterator();
		while (treasureIt.hasNext()) {
			t = treasureIt.next();
			if (t.getHitBox().intersects(Hero.getInstance().getHitBox())) {
				Hero.getInstance().addScore(t.getValue());
				Main.aman.play("money");
				t.isActive = false;
			}
			if (!t.isActive) {
				// System.out.println("removing inactive entity");
				treasureIt.remove();
			} else {
				t.draw();
			}
		}
	}
}
