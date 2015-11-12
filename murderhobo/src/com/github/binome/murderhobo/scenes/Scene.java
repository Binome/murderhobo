package com.github.binome.murderhobo.scenes;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import com.github.binome.murderhobo.Main;
import com.github.binome.murderhobo.Settings;

/**
 * @author SkylarRowan
 * Abstract superclass for all game scenes. Title, pause, game over, lvj, etc.
 */
public abstract class Scene
{
    protected boolean keepGoing;
    
    /**
     * Updates the scene than draw the frame
     * @param delta time passed since last frame draw
     * @return false when time to exit
     */
    public abstract boolean drawFrame(float delta);

    protected Scene nextScene()
    {
	return this;
    }

    /**
     * First loop to be called when starting any scene.
     * @return
     */
    public Scene go()
    {
	keepGoing=true;
	long lastloop = (Sys.getTime() * 1000 / Sys.getTimerResolution());
	do
	{
	    Display.sync(Settings.TARGET_FPS);
	    long now = (Sys.getTime() * 1000 / Sys.getTimerResolution());
	    long delta = now - lastloop;
	    lastloop = now;

	    keepGoing = drawFrame(delta);

	    // UPDATE DISPLAY
	    Display.update();
	} while (keepGoing && !Display.isCloseRequested());

	return nextScene();
    }
}