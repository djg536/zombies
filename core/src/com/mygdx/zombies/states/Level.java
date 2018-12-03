package com.mygdx.zombies.states;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.zombies.CustomContactListener;
import com.mygdx.zombies.Entity;
import com.mygdx.zombies.InfoContainer;
import com.mygdx.zombies.Player;
import com.mygdx.zombies.Zombie;
import com.mygdx.zombies.Zombies;
import pickups.Projectile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Level extends State {


	private Player player;
	private ArrayList<Zombie> zombiesList;
	private ArrayList<Projectile> bulletsList;
	public World box2dWorld;
	private Box2DDebugRenderer box2dDebugRenderer;
	public BodyDef mob;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private RayHandler rayHandler;
	private ArrayList<PointLight> lightsList;
	private PointLight p1;
	private PointLight p2;
	private PointLight p3;
	private PointLight p4;


	/**
	 * Constructor for the level
	 * 
	 * @param path
	 *            - name of .tmx file for tiled grid
	 */
	public Level(String path) {
		super();
		String p;
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

		mob = new BodyDef() {
			{
				type = BodyDef.BodyType.DynamicBody;
			}
		};


		bulletsList = new ArrayList<Projectile>();
		zombiesList = new ArrayList<Zombie>();
		
		player = new Player(this, 400, 400, 3);
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
		rayHandler.useDiffuseLight(true);
		lightsList = new ArrayList<PointLight>();
		lightsList.add(new PointLight(rayHandler, 128, Color.WHITE, 512, 80, 80));
		lightsList.add(new PointLight(rayHandler, 128, Color.WHITE, 512, 80, 880));
		lightsList.add(new PointLight(rayHandler, 128, Color.WHITE, 512, 880, 80));
		lightsList.add(new PointLight(rayHandler, 128, Color.WHITE, 512, 880, 880));
		//System.out.println(lightsList.get(0).get);
	}

	public float getLights() {
		System.out.println(p1.getDistance());
		System.out.println(p1.getX());
		System.out.println(p1.getY());
		System.out.println(p1.getPosition());
		return p1.getDistance();
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
		worldBatch.end();
		rayHandler.render();
		UIBatch.begin();
		player.hudRender();
		UIBatch.end();

		box2dDebugRenderer.render(box2dWorld, camera.combined.scl(Zombies.PhysicsDensity));
	}

	@Override
	public int update() {

		if (Gdx.input.justTouched()) {
			bulletsList.add(new Projectile(this, player.getPositionX(), player.getPositionY(),
					player.getAngleRads() + Math.PI / 2));
		}

		camera.position.set(player.getPositionX(), player.getPositionY(), 0);
		camera.update();
		box2dWorld.step(1 / 60f, 6, 2);
		
		for(Zombie zombie : zombiesList) {
			zombie.update();
		}
		
		Entity.removeDeletionFlagged(zombiesList);
		Entity.removeDeletionFlagged(bulletsList);
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
