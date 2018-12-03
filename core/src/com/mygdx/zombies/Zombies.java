package com.mygdx.zombies;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.mygdx.zombies.states.StateManager;

public class Zombies extends Game {

	public static int InitialWindowWidth = 1280;
	public static int InitialWindowHeight = 720;
	public static int InitialViewportWidth = 1280;
	public static int InitialViewportHeight = 720;
	public static float WorldScale = 1.5f;
	public static float PhysicsDensity = 100;
	public static BitmapFont mainFont;
	public static BitmapFont titleFont;
	public static BitmapFont pointsFont;
	public static BitmapFont creditsFont;
	private StateManager sm;

	// Collision masks. Can OR these together to combine.
	// Use categoryBits (1 default) and mask bits (-1 default)
	// (maskBitsA & categoryBitsB) && (categoryBitsA & maskBitsB);
	public static short playerFilter = 2;
	public static short projectileFilter = 1;
	

	public static BitmapFont GenerateFont(String name, int size) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(name));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}

	@Override
	public void create() {
		Box2D.init();
		sm = new StateManager();
		mainFont = Zombies.GenerateFont("NESCyrillic.ttf", 70);
		titleFont = Zombies.GenerateFont("Amatic-Bold.ttf", 150);
		pointsFont = Zombies.GenerateFont("KaushanScript-Regular.otf", 50);
		creditsFont = Zombies.GenerateFont("SourceSansPro-Regular.otf", 50);
	}

	@Override
	public void render() {
		sm.gameLoop();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sm.render();
	}

	@Override
	public void dispose() {
		sm.dispose();
		mainFont.dispose();
		titleFont.dispose();
		pointsFont.dispose();
		creditsFont.dispose();
	}

	@Override
	public void resize(int width, int height) {
		sm.resize(width, height);
	}
}
