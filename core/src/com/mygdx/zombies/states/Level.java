package com.mygdx.zombies.states;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.zombies.Entity;
import com.mygdx.zombies.Player;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Level extends State {
	
	private TiledMap map;
	private String mapFile;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private Player player;
	
	

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
			renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
			
			camera = new OrthographicCamera();
			camera.setToOrtho(false, 40, 40);
			camera.update();
						
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 player = new Player(spriteBatch, 100, 100, 3);				 		 
	}
	
	/**
	 * Call to draw stage to screen
	 */
	@Override
	public void render() {
		renderer.setView(camera);
		renderer.render();
		
		spriteBatch.begin();
		player.render();
		spriteBatch.end();
		
	}
		
	@Override 
	public boolean update() {
	
		return player.update();
	}
}
