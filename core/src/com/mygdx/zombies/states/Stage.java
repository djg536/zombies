package com.mygdx.zombies.states;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.zombies.Entity;

public class Stage extends State {
	
	private ArrayList<Entity> clippingList;
	private ArrayList<Entity> nonClippingList;
	

	/**Constructor for the stage
	 * @param path - filename of the stage layout
	 */
	public Stage(String path) {
		super();
			
		HashMap<Character, Texture> dictionary = new HashMap<Character, Texture>();
		dictionary.put('b', new Texture("block.png"));
			
		clippingList = new ArrayList<Entity>();
		nonClippingList = new ArrayList<Entity>();
	
		BufferedReader br;
		String line;
		int y = 0;
		try {
			br = new BufferedReader(new FileReader("stages/" + path));
			while((line = br.readLine()) != null) {
				for(int x = 0; x < line.length(); x++)
				{
					Character c = line.charAt(x);
					if(c != ' ')					
						clippingList.add(new Entity(spriteBatch, dictionary.get(c), x*32, 720-32-y*32));
				}
				y++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Call to draw stage to screen
	 */
	@Override
	public void render() {
		spriteBatch.begin();
		for(Entity entity : clippingList) {
			entity.render();
		}
		for(Entity entity : nonClippingList) {
			entity.render();
		}
		spriteBatch.end();
	}
}
