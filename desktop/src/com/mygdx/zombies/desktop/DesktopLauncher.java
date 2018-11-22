package com.mygdx.zombies.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.BaseGame;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 480;
		config.title = "Silence of the Lamberts";
		new LwjglApplication(new BaseGame(), config);
	}
}
