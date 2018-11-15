package com.mygdx.zombies.states;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.zombies.Entity;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Stage extends State {
	
	private TiledMap map;
	private String mapFile;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
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
	}
}
