package com.mygdx.zombies.states;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.zombies.CustomContactListener;
import com.mygdx.zombies.Entity;
import com.mygdx.zombies.Player;
import com.mygdx.zombies.Zombie;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.items.MeleeWeapon;
import com.mygdx.zombies.items.PowerUp;
import com.mygdx.zombies.items.Projectile;
import com.mygdx.zombies.items.RangedWeapon;
import com.mygdx.zombies.InfoContainer;
import com.mygdx.zombies.PickUp;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Level extends State {


	public Player player;
	private ArrayList<Zombie> zombiesList;
	public ArrayList<Projectile> bulletsList;

	private ArrayList<PickUp> pickUpsList;
	public World box2dWorld;
	private Box2DDebugRenderer box2dDebugRenderer;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private RayHandler rayHandler;
	private ArrayList<PointLight> lightsList;

	/**
	 * Constructor for the level
	 * 
	 * @param path
	 * 		- name of .tmx file for tiled grid
	 */
	public Level(String path) {
		super();
		try {
			String mapFile = String.format("stages/%s.tmx", path);

			map = new TmxMapLoader().load(mapFile);
			renderer = new OrthogonalTiledMapRenderer(map, Zombies.WorldScale);

			box2dWorld = new World(new Vector2(0, 0), true);
			box2dDebugRenderer = new Box2DDebugRenderer();

			initLights();

			MapBodyBuilder.buildShapes(map, Zombies.PhysicsDensity / Zombies.WorldScale, box2dWorld);

		} catch (Exception e) {
			e.printStackTrace();
		}

		bulletsList = new ArrayList<Projectile>();
		zombiesList = new ArrayList<Zombie>();
		pickUpsList = new ArrayList<PickUp>();
		
		pickUpsList.add(new PickUp(this, 200, 300, "pickups/pistol.png",
				new RangedWeapon(this, 35, "bullet.png", 1.5f, Zombies.soundShoot), InfoContainer.BodyID.WEAPON));
		
		pickUpsList.add(new PickUp(this, 400, 300, "pickups/pistol.png",
				new RangedWeapon(this, 60, "laser.png", 4, Zombies.soundLaser), InfoContainer.BodyID.WEAPON));
		
		pickUpsList.add(new PickUp(this, 200, 350, "sword.png", new MeleeWeapon(worldBatch), InfoContainer.BodyID.WEAPON));
		
		pickUpsList.add(new PickUp(this, 200, 400, "pickups/health.png", new PowerUp(0, 2), InfoContainer.BodyID.PICKUP));
		
		pickUpsList.add(new PickUp(this, 200, 500, "pickups/speed.png", new PowerUp(1, 0), InfoContainer.BodyID.PICKUP));
		
		player = new Player(this, 400, 400, 5);
		zombiesList.add(new Zombie(this, 600, 200, 3, player));
		zombiesList.add(new Zombie(this, 300, 200, 3, player));
		camera = new OrthographicCamera();
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		box2dWorld.setContactListener(new CustomContactListener());
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width * Zombies.InitialViewportWidth / (float) Zombies.InitialViewportWidth;
		camera.viewportHeight = height;
		camera.update();
	}

	private void initLights() {
		//set up lights in corners of the screen
		rayHandler = new RayHandler(box2dWorld);
		rayHandler.setAmbientLight(.5f);
		//rayHandler.useDiffuseLight(true);
		lightsList = new ArrayList<PointLight>();
		lightsList.add(new PointLight(rayHandler, 128, Color.FIREBRICK, 512, 80, 80));
		lightsList.add(new PointLight(rayHandler, 128, Color.FIREBRICK, 512, 80, 880));
		lightsList.add(new PointLight(rayHandler, 128, Color.FIREBRICK, 512, 880, 80));
		lightsList.add(new PointLight(rayHandler, 128, Color.BLUE, 512, 300, 300));
		System.out.println(lightsList.get(0).getPosition());
		System.out.println(lightsList.get(0).getDistance());
	}

	public ArrayList<PointLight> getLights() {
		return lightsList;
	}

	public boolean inLights() {
		/*
		check if the player is within the radius of any of the lights
		if so, return true
		*/
		for (PointLight light : lightsList) {
			if (Math.pow((player.getPositionX() - light.getX()),2) + Math.pow((player.getPositionY() - light.getY()),2)
					< Math.pow(light.getDistance(),2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Call to draw stage to screen
	 */
	@Override
	public void render() {
		renderer.setView(camera);
		renderer.render();

		worldBatch.setProjectionMatrix(camera.combined);
		worldBatch.begin();		
		player.render();	
		for (Zombie zombie : zombiesList)
			zombie.render();
		for (Projectile bullet : bulletsList)
			bullet.render();
		for(PickUp pickUp : pickUpsList)
			pickUp.render();
		
		worldBatch.end();
		rayHandler.render();
		UIBatch.begin();
		player.hudRender();
		UIBatch.end();

		box2dDebugRenderer.render(box2dWorld, camera.combined.scl(Zombies.PhysicsDensity));
	}

	@Override
	public int update() {
		camera.position.set(player.getPositionX(), player.getPositionY(), 0);
		camera.update();
		box2dWorld.step(1 / 60f, 6, 2);

		for(Zombie zombie : zombiesList) {
			zombie.update();
		}
		
		Entity.removeDeletionFlagged(zombiesList);
		Entity.removeDeletionFlagged(bulletsList);
		Entity.removeDeletionFlagged(pickUpsList);
		//Entity.removeDeletionFlagged(player);

		rayHandler.setCombinedMatrix(camera);
		rayHandler.update();

		return player.update(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
	}

	@Override
	public void dispose() {
		super.dispose();
		//rayHandler.dispose();
		// box2dDebugRenderer.dispose();
		// box2dWorld.dispose();
		// renderer.dispose();
		// player.dispose();
		// zombie.dispose();
		// map.dispose();
	}
}
