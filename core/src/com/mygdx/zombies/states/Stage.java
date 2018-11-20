package com.mygdx.zombies.states;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.zombies.Entity;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.zombies.Player;
import com.mygdx.zombies.Zombie;
import com.mygdx.zombies.Zombies;

public class Stage extends State {

	private Zombies game;
	private TiledMap map;
	private String mapFile;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private Player player;
	private Zombie zombie;

	private World world;
	private Box2DDebugRenderer b2dr;
	
	/**Constructor for the stage
	 * @param path - name of .tmx file for tiled grid
	 */
	public Stage(String path) {
		super();
		String p;
		try {
			mapFile = String.format("%s.tmx", path);
			p = String.format("stages/%s", mapFile);
			
			map = new TmxMapLoader().load(p);
			renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
			
			camera = new OrthographicCamera();
			camera.setToOrtho(false, 40, 40);
			camera.update();

			world = new World(new Vector2(0,-10), true);
			b2dr = new Box2DDebugRenderer();
			player = new Player(spriteBatch, 100, 100);
			zombie = new Zombie(world);

			BodyDef bdef = new BodyDef();
			PolygonShape shape = new PolygonShape();
			FixtureDef fdef = new FixtureDef();
			Body body;

			//create border bodies/fixtures
			for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();

				bdef.type = BodyDef.BodyType.StaticBody;
				bdef.position.set(rect.getX() + rect.getWidth()/2, rect.getY() + rect.getHeight()/2);

				body = world.createBody(bdef);

				shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
				fdef.shape = shape;
				body.createFixture(fdef);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}



	}
	
	/**
	 * Call to draw stage to screen
	 */
	@Override
	public void render() {
		renderer.setView(camera);
		renderer.render();
		b2dr.render(world, camera.combined);
		spriteBatch.begin();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		player.render();
		zombie.draw(game.batch);
		game.batch.end();
		spriteBatch.end();

	}

	public void update() {
		player.update();
		zombie.update();
	}


}
