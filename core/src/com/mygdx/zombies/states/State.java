package com.mygdx.zombies.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class State {

	public SpriteBatch worldBatch;
	public SpriteBatch UIBatch;
	protected StateManager stateManager;

	public State(StateManager stateManager) {
		this.stateManager = stateManager;
		worldBatch = new SpriteBatch();
		UIBatch = new SpriteBatch();
	}

	public void render() {

	}

	public void update() {
		
	}

	public void resize(int width, int height) {

	}

	public void dispose() {
		worldBatch.dispose();
		UIBatch.dispose();
	}
}
