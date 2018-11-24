package com.mygdx.zombies;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.zombies.states.BaseGame;

public class ZombieLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1000;
        config.height = 800;
        config.title = "Silence of the Lamberts";

        BaseGame myGame = new BaseGame();
        LwjglApplication launcher = new LwjglApplication(myGame, config);
    }
}
