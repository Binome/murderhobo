package com.github.binome.murderhobo.scenes;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import com.github.binome.murderhobo.Main;

public abstract class Scene
{
    private boolean doExit = false;

    // return false if the game should be quit
    public abstract boolean drawFrame(float delta);

    // null typically means Game should load menu
    public abstract Scene nextScene();

    protected void exit()
    {
        doExit=true;
    };

    // returns false when game should be exited
    public boolean go()
    {
        long lastloop = (Sys.getTime()*1000 / Sys.getTimerResolution());

        boolean keepGoing = true;
        do
        {
            Display.sync(60);   // 60 FPS
            long now = (Sys.getTime()*1000 / Sys.getTimerResolution());
            long delta = now - lastloop;
            lastloop = now;

            keepGoing = drawFrame(delta);
            
            // UPDATE DISPLAY
            Display.update();
            Main.aman.update();

            if (Display.isCloseRequested() || doExit)
            {
                return false;
            }
            //System.out.println("keepGoing is: " + keepGoing);
            //System.out.println("Next scene is: " + nextScene());

        } while (keepGoing);

        return true;
    }


}