package com.mygdx.zombies.states;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.zombies.Boss1;
import com.mygdx.zombies.CustomContactListener;
import com.mygdx.zombies.Enemy;
import com.mygdx.zombies.Entity;
import com.mygdx.zombies.Gate;
import com.mygdx.zombies.InfoContainer;
import com.mygdx.zombies.NPC;
import com.mygdx.zombies.PickUp;
import com.mygdx.zombies.Player;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.items.MeleeWeapon;
import com.mygdx.zombies.items.PowerUp;
import com.mygdx.zombies.items.Projectile;
import com.mygdx.zombies.items.RangedWeapon;
import com.mygdx.zombies.states.StateManager.StateID;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Level extends State {


	public Player player;
	private ArrayList<Enemy> enemiesList;
	public ArrayList<Projectile> bulletsList;
	private ArrayList<PickUp> pickUpsList;
	public World box2dWorld;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private RayHandler rayHandler;
	private ArrayList<PointLight> lightsList;
	private ArrayList<NPC> npcsList;
	private ArrayList<Gate> gatesList;
	private String path;
	private int spawnEntryID;
	private Box2DDebugRenderer box2DDebugRenderer;

	/**
	 * Constructor for the level
	 * 
	 * @param path
	 * 		- name of .tmx file for tiled grid
	 */
	public Level(String path, int spawnEntryID) {
		super();	
		
		this.path = path;
		this.spawnEntryID = spawnEntryID;
		
		bulletsList = new ArrayList<Projectile>();
		enemiesList = new ArrayList<Enemy>();
		pickUpsList = new ArrayList<PickUp>();
		npcsList = new ArrayList<NPC>();
		gatesList = new ArrayList<Gate>();
			

		String mapFile = String.format("stages/%s.tmx", path);

		map = new TmxMapLoader().load(mapFile);
		renderer = new OrthogonalTiledMapRenderer(map, Zombies.WorldScale);

		box2dWorld = new World(new Vector2(0, 0), true);
		box2DDebugRenderer = new Box2DDebugRenderer();

		MapBodyBuilder.buildShapes(map, Zombies.PhysicsDensity / Zombies.WorldScale, box2dWorld);
					
		loadGates();
		loadPlayer(spawnEntryID);
		loadObjects();		
		initLights();			
								
		camera = new OrthographicCamera();
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		box2dWorld.setContactListener(new CustomContactListener());
	}
	
	public String getPath() {
		return path;
	}
	
	public int getSpawnEntryID() {
		return spawnEntryID;
	}
	
	public ArrayList<Enemy> getEnemiesList() {
		return enemiesList;
	}
	
	public static boolean gunFire() {
		return RangedWeapon.isFiring();
	}
	
	private void loadPlayer(int spawnEntryID) {
		int x, y;
		x = y = 300;
				
		if(spawnEntryID != -1)
		{
			MapObjects objects = map.getLayers().get("Entries").getObjects();
			//Parse entries until one with the correct ID is found
			//Then get x and y coordinates and supply to player
			for(MapObject object : objects) {	
				MapProperties p = object.getProperties();
				int entryID = (Integer) p.get("EntryID");		
				if(entryID==spawnEntryID) {
					x = ((Float) p.get("x")).intValue();
					y = ((Float) p.get("y")).intValue();			
					break;
				}
			}
		}	
		
		x*= Zombies.WorldScale;
		y*= Zombies.WorldScale;
		
		player = new Player(this, x, y);
	}
	
	private void loadGates() {
		MapObjects objects = map.getLayers().get("Gates").getObjects();
		
		for(MapObject object : objects) {
			
			MapProperties p = object.getProperties();
			int x = ((Float) p.get("x")).intValue();
			int y = ((Float) p.get("y")).intValue();
			int width = ((Float) p.get("width")).intValue();
			int height = ((Float) p.get("height")).intValue();
			String destination = (String) p.get("Destination");
			int entryID = (Integer) p.get("EntryID");
			
			x*= Zombies.WorldScale;
			y*= Zombies.WorldScale;
			
			Gate gate = new Gate(box2dWorld, new Rectangle(x, y, width, height),
					StateID.valueOf(destination), entryID);
			gatesList.add(gate);
		}
	}

	private void loadObjects() {
		MapObjects objects = map.getLayers().get("Objects").getObjects();
		
		for(MapObject object : objects) {
			
			MapProperties p = object.getProperties();
			int x = ((Float) p.get("x")).intValue();
			int y = ((Float) p.get("y")).intValue();
			
			x*= Zombies.WorldScale;
			y*= Zombies.WorldScale;

			switch(object.getName()) {
				case "powerUpHealth":
					pickUpsList.add(new PickUp(this, x, y, "pickups/heart.png",
							new PowerUp(0, 2, 0), InfoContainer.BodyID.PICKUP));
				break;
				
				case "powerUpSpeed":
					pickUpsList.add(new PickUp(this, x, y, "pickups/speed2.png",
							new PowerUp(1, 0, 0), InfoContainer.BodyID.PICKUP));
				break;
				
				case "powerUpStealth":
					pickUpsList.add(new PickUp(this, x, y, "pickups/stealth2.png",
							new PowerUp(0, 0, 1), InfoContainer.BodyID.PICKUP));
				break;
				
				case "lasergun":
					pickUpsList.add(new PickUp(this, x, y, "pickups/pistol.png",
							new RangedWeapon(this, 10, "laser.png", 50, Zombies.soundLaser), InfoContainer.BodyID.WEAPON));
				break;
				
				case "pistol":
					pickUpsList.add(new PickUp(this, x, y, "pickups/pistol.png",
							new RangedWeapon(this, 15, "bullet.png", 20, Zombies.soundShoot), InfoContainer.BodyID.WEAPON));
				break;
				
				case "sword":
					pickUpsList.add(new PickUp(this, x, y, "sword.png",
							new MeleeWeapon(worldBatch), InfoContainer.BodyID.WEAPON));
				break;
				
				case "zombie1":
					enemiesList.add(new Enemy(this, x, y, "zombie/zombie1.png", 6, 5));
				break;
				
				case "zombie2":
					enemiesList.add(new Enemy(this, x, y, "zombie/zombie2.png", 5, 15));
				break;
				
				case "zombie3":
					enemiesList.add(new Enemy(this, x, y, "zombie/zombie3.png", 10, 5));
				break;
				
				case "NPC":
					npcsList.add(new NPC(this, x, y));
				break;
				
				case "boss1":
					enemiesList.add(new Boss1(this, x, y));
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
		rayHandler = new RayHandler(box2dWorld);
		rayHandler.setShadows(true);
		rayHandler.setAmbientLight(.2f);
		lightsList = new ArrayList<PointLight>();
		
		MapObjects objects = map.getLayers().get("Lights").getObjects();
				
				for(MapObject object : objects) {
					
					MapProperties p = object.getProperties();
					int x = ((Float) p.get("x")).intValue();
					int y = ((Float) p.get("y")).intValue();
					
					x *=  Zombies.WorldScale;
					y *=  Zombies.WorldScale;
					
					Color color;
					int distance;
					
					switch(object.getName()) {
						case "street":
							color = Color.ORANGE;
							distance = 250;
							break;
						case "security":
							color = Color.CYAN;
							distance = 120;
							break;
						case "red":
							color = Color.FIREBRICK;
							distance = 80;
							break;
						case "torch":
							color = Color.GREEN;
							distance = 80;
							break;
						default:
							throw new IllegalArgumentException();
					}		
					
					lightsList.add(new PointLight(rayHandler, 20, color, distance, x, y));
				}
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
		for (int i = 0; i < enemiesList.size(); i++)
			enemiesList.get(i).render();
		for (Projectile bullet : bulletsList)
			bullet.render();
		for(PickUp pickUp : pickUpsList)
			pickUp.render();
		for(NPC npc : npcsList)
			npc.render();
		
		worldBatch.end();
		rayHandler.render();
		UIBatch.begin();
		player.hudRender();
		UIBatch.end();

		//Enable this line to show Box2D physics debug info
		//box2dDebugRenderer.render(box2dWorld, camera.combined.scl(Zombies.PhysicsDensity));
	}

	@Override
	public void update() {
		camera.position.set(player.getPositionX(), player.getPositionY(), 0);
		camera.update();
		box2dWorld.step(1 / 60f, 6, 2);
		
		for(int i = 0; i < enemiesList.size(); i++)
			enemiesList.get(i).update(this.inLights());
		
		
		for(NPC npc : npcsList)
			npc.update();
		
		Entity.removeDeletionFlagged(enemiesList);
		Entity.removeDeletionFlagged(bulletsList);
		Entity.removeDeletionFlagged(pickUpsList);
		Entity.removeDeletionFlagged(npcsList);
		//Entity.removeDeletionFlagged(player);

		rayHandler.setCombinedMatrix(camera);
		rayHandler.update();

		// this.inLights()
		player.update(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
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
