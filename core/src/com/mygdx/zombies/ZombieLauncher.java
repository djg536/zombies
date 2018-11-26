package com.mygdx.zombies;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class ZombieLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Zombies.InitialWindowWidth;
        config.height = Zombies.InitialWindowHeight;
        config.title = "Silence of the Lamberts";
        
        new LwjglApplication(new Zombies(), config);
    }
}
