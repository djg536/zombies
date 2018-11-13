package com.mygdx.zombies.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class State {
	
	protected SpriteBatch spriteBatch;
	
	public State() {
		spriteBatch = new SpriteBatch();
	}
	
	public void render() {
		
	}
	
	public void update() {
		
	}
	
	public void dispose() {
		spriteBatch.dispose();
	}
}
