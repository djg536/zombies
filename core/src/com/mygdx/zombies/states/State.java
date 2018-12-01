package com.mygdx.zombies.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class State {

	public SpriteBatch worldBatch;
	public SpriteBatch UIBatch;

	public State() {
		worldBatch = new SpriteBatch();
		UIBatch = new SpriteBatch();
	}

	public void render() {

	}

	public int update() {
		return 0;
	}

	public void resize(int width, int height) {

	}

	public void dispose() {
		worldBatch.dispose();
		UIBatch.dispose();
	}
}
