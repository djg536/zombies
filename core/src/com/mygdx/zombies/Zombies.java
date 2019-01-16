package com.mygdx.zombies;

import java.util.Random;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
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
	public static Sound soundShoot;
	public static Sound soundSelect;
	public static Sound soundLaser;
	public static Sound soundSwing;
	public static Sound soundAmmo;
	public static Sound soundPowerUp;
	public static Sound soundAmbientWind;
	public static Sound soundEndMusic;
	public static Sound[] soundArrayZombie;
	public static Random random;
	private StateManager sm;

	// Collision masks. Can OR these together to combine.
	// Use categoryBits (1 default) and mask bits (-1 default)
	// (maskBitsA & categoryBitsB) && (categoryBitsA & maskBitsB);
	public static short playerFilter = 2;
	public static short projectileFilter = 1;
	

	public static BitmapFont generateFont(String name, int size) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(name));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}
	
	public static double angleBetweenRads(Vector2 p1, Vector2 p2) {
		double diffx = p1.x - p2.x;
		double diffy = p1.y - p2.y;
		return Math.atan2(diffy, diffx);
	}
	
	public static double distanceBetween(Vector2 p1, Vector2 p2) {
		return Math.sqrt(Math.pow(p1.x-p2.x, 2) + Math.pow(p1.y-p2.y, 2));
	}

	@Override
	public void create() {
		Box2D.init();
		random = new Random();
		
		sm = new StateManager();
		mainFont = Zombies.generateFont("NESCyrillic.ttf", 55);
		titleFont = Zombies.generateFont("Amatic-Bold.ttf", 150);
		pointsFont = Zombies.generateFont("KaushanScript-Regular.otf", 50);
		creditsFont = Zombies.generateFont("SourceSansPro-Regular.otf", 50);
		soundShoot = Gdx.audio.newSound(Gdx.files.internal("sounds/gun.wav"));
		soundSwing = Gdx.audio.newSound(Gdx.files.internal("sounds/swing.wav"));
		soundSelect = Gdx.audio.newSound(Gdx.files.internal("sounds/select.wav"));
		soundLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
		soundAmmo = Gdx.audio.newSound(Gdx.files.internal("sounds/ammo.wav"));
		soundPowerUp = Gdx.audio.newSound(Gdx.files.internal("sounds/powerup.wav"));
		soundAmbientWind = Gdx.audio.newSound(Gdx.files.internal("sounds/wind.mp3"));
		soundEndMusic = Gdx.audio.newSound(Gdx.files.internal("sounds/alligator_crawl.mp3"));
		
		soundArrayZombie = new Sound[8];
		for(int i = 0; i<soundArrayZombie.length; i++)
			soundArrayZombie[i] = Gdx.audio.newSound(Gdx.files.internal(String.format("sounds/zombie%d.wav", i+1)));
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
		soundShoot.dispose();
		soundSwing.dispose();
		soundSelect.dispose();
		soundLaser.dispose();
		soundAmmo.dispose();
		soundPowerUp.dispose();
		soundAmbientWind.dispose();
	}

	@Override
	public void resize(int width, int height) {
		sm.resize(width, height);
	}
}
