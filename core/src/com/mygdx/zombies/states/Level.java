package com.mygdx.zombies.states;

import com.mygdx.zombies.Player;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Level extends State {
	
	private TiledMap map;
	private String mapFile;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Player player;
	public World box2dWorld;
	private Box2DDebugRenderer box2dDebugRenderer;
	public BodyDef mob;
	public BodyDef solid;
	public static float tileSize = 16f;
	
	
	/**Constructor for the stage
	 * @param path - name of .tmx file for tiled grid
	 */
	public Level(String path) {
		super();
		String p;
		try {
			mapFile = String.format("%s.tmx", path);
			p = String.format("stages/%s", mapFile);
			
			map = new TmxMapLoader().load(p);
			renderer = new OrthogonalTiledMapRenderer(map, 1 / tileSize);
			
			camera = new OrthographicCamera(120, 120);
			camera.setToOrtho(false, 40, 40);
			camera.update();
						
			box2dWorld = new World(new Vector2(0, 0), true);
			box2dDebugRenderer = new Box2DDebugRenderer();
			
			mob = new BodyDef() { { type = BodyDef.BodyType.DynamicBody; } };			
			//solid = new BodyDef() { { type = BodyDef.BodyType.StaticBody; } };
			
			MapBodyBuilder.buildShapes(map, tileSize, box2dWorld);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 player = new Player(this, 400, 400, 3);				 		 
	}
	
	/**
	 * Call to draw stage to screen
	 */
	@Override
	public void render() {
		renderer.setView(camera);
		renderer.render();
		
		//spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();		
		player.render();
		player.hudRender();
		spriteBatch.end();
		
		box2dDebugRenderer.render(box2dWorld, camera.combined);
	}
		
	@Override 
	public boolean update() {	
		
		//mousePos = tiledCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    
		//camera.position.set(player.getPositionX(), player.getPositionY(), 0);
		camera.update();
		box2dWorld.step(1/60f, 6, 2);
		return player.update();
	}
	
	@Override
	public void dispose() {
		
		super.dispose();
		box2dDebugRenderer.dispose();
		box2dWorld.dispose();
	}
}
