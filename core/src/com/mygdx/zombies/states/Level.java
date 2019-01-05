package com.mygdx.zombies.states;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.zombies.Boss1;
import com.mygdx.zombies.CustomContactListener;
import com.mygdx.zombies.Entity;
import com.mygdx.zombies.Gate;
import com.mygdx.zombies.Player;
import com.mygdx.zombies.Zombie;
import com.mygdx.zombies.Enemy;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.items.MeleeWeapon;
import com.mygdx.zombies.items.PowerUp;
import com.mygdx.zombies.items.Projectile;
import com.mygdx.zombies.items.RangedWeapon;
import com.mygdx.zombies.InfoContainer;
import com.mygdx.zombies.NPC;
import com.mygdx.zombies.PickUp;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Level extends State {


	public Player player;
	private ArrayList<Enemy> zombiesList;
	public ArrayList<Projectile> bulletsList;

	private ArrayList<PickUp> pickUpsList;
	public World box2dWorld;
	private Box2DDebugRenderer box2dDebugRenderer;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private RayHandler rayHandler;
	private ArrayList<PointLight> lightsList;
	private ArrayList<NPC> npcList;

	/**
	 * Constructor for the level
	 * 
	 * @param path
	 * 		- name of .tmx file for tiled grid
	 */
	public Level(StateManager stateManager, String path) {
		super(stateManager);	
		
		bulletsList = new ArrayList<Projectile>();
		zombiesList = new ArrayList<Enemy>();
		pickUpsList = new ArrayList<PickUp>();
		npcList = new ArrayList<NPC>();
			
		try {
			String mapFile = String.format("stages/%s.tmx", path);

			map = new TmxMapLoader().load(mapFile);
			renderer = new OrthogonalTiledMapRenderer(map, Zombies.WorldScale);

			box2dWorld = new World(new Vector2(0, 0), true);
			box2dDebugRenderer = new Box2DDebugRenderer();

			MapBodyBuilder.buildShapes(map, Zombies.PhysicsDensity / Zombies.WorldScale, box2dWorld);
			loadObjects();	
			loadGates();
			initLights();
						
			camera = new OrthographicCamera();
			resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			box2dWorld.setContactListener(new CustomContactListener(stateManager));
		}
		catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	private void loadGates() {
		MapObjects objects = map.getLayers().get("Gates").getObjects();
		
		for(MapObject object : objects) {
			
			MapProperties p = object.getProperties();
			int x = ((Float) p.get("x")).intValue();
			int y = ((Float) p.get("y")).intValue();
			int width = ((Float) p.get("width")).intValue();
			int height = ((Float) p.get("height")).intValue();
			
			new Gate(box2dWorld, new Rectangle(x, y, width, height), "");
		}
	}
	
	private void loadObjects() {
		MapObjects objects = map.getLayers().get("Objects").getObjects();
		
		for(MapObject object : objects) {
			
			MapProperties p = object.getProperties();
			int x = ((Float) p.get("x")).intValue();
			int y = ((Float) p.get("y")).intValue();

			switch(object.getName()) {
				case "powerUpHealth":
					pickUpsList.add(new PickUp(this, x, y, "pickups/health.png",
							new PowerUp(0, 2, 0), InfoContainer.BodyID.PICKUP));
				break;
				
				case "powerUpSpeed":
					pickUpsList.add(new PickUp(this, x, y, "pickups/speed.png",
							new PowerUp(1, 0, 0), InfoContainer.BodyID.PICKUP));
				break;
				
				case "powerUpStealth":
					pickUpsList.add(new PickUp(this, x, y, "pickups/stealth.png",
							new PowerUp(0, 0, 1), InfoContainer.BodyID.PICKUP));
				break;
				
				case "lasergun":
					pickUpsList.add(new PickUp(this, x, y, "pickups/pistol.png",
							new RangedWeapon(this, 60, "laser.png", 10, Zombies.soundLaser), InfoContainer.BodyID.WEAPON));
				break;
				
				case "pistol":
					pickUpsList.add(new PickUp(this, x, y, "pickups/pistol.png",
							new RangedWeapon(this, 35, "bullet.png", 15, Zombies.soundShoot), InfoContainer.BodyID.WEAPON));
				break;
				
				case "sword":
					pickUpsList.add(new PickUp(this, x, y, "sword.png",
							new MeleeWeapon(worldBatch), InfoContainer.BodyID.WEAPON));
				break;
				
				case "player":
					player = new Player(this, x, y, 5);
				break;
				
				case "zombie1":
					zombiesList.add(new Zombie(this, x, y, 3));
				break;
				
				case "NPC":
					npcList.add(new NPC(this, x, y));
				break;
				
				case "boss1":
					zombiesList.add(new Boss1(this, x, y));
				break;
				
				default:
					System.err.println("Error importing stage: unrecognised object");
				break;
			}
		}
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
		rayHandler.setAmbientLight(.2f);
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
		for (Enemy zombie : zombiesList)
			zombie.render();
		for (Projectile bullet : bulletsList)
			bullet.render();
		for(PickUp pickUp : pickUpsList)
			pickUp.render();
		for(NPC npc : npcList)
			npc.render();
		
		worldBatch.end();
		rayHandler.render();
		UIBatch.begin();
		player.hudRender();
		UIBatch.end();

		box2dDebugRenderer.render(box2dWorld, camera.combined.scl(Zombies.PhysicsDensity));
	}

	@Override
	public void update() {
		camera.position.set(player.getPositionX(), player.getPositionY(), 0);
		camera.update();
		box2dWorld.step(1 / 60f, 6, 2);

		for(Enemy zombie : zombiesList)
			zombie.update(this.inLights());
		for(NPC npc : npcList)
			npc.update();
		
		Entity.removeDeletionFlagged(zombiesList);
		Entity.removeDeletionFlagged(bulletsList);
		Entity.removeDeletionFlagged(pickUpsList);
		Entity.removeDeletionFlagged(npcList);
		//Entity.removeDeletionFlagged(player);

		rayHandler.setCombinedMatrix(camera);
		rayHandler.update();

		if(player.update(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)), this.inLights()) <= 0) {
			stateManager.loadState(new Level(stateManager, "teststage"));
		}
	}
	
	public Player getPlayer() {
		return player;
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
