package com.mygdx.zombies.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.zombies.Zombies;

public class State {

	public SpriteBatch worldBatch;
	public SpriteBatch UIBatch;

	public State() {
		worldBatch = new SpriteBatch();
		UIBatch = new SpriteBatch();
		resize();
	}

	public void render() {

	}

	public void update() {
		
	}
	
	private void resize() {
		UIBatch.getProjectionMatrix()
			.setToOrtho2D(0, 0, Zombies.InitialWindowWidth, Zombies.InitialWindowHeight);
	}

	public void resize(int width, int height) {
		resize();
	}

	public void dispose() {
		worldBatch.dispose();
		UIBatch.dispose();
	}
}
