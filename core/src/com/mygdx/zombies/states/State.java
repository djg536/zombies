package com.mygdx.zombies.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class State {
	
	public SpriteBatch spriteBatch;
	
	public State() {
		spriteBatch = new SpriteBatch();
	}
	
	public void render() {
		
	}
	
	public boolean update() {
		return false;
	}
	
	public void dispose() {
		spriteBatch.dispose();
	}
}
