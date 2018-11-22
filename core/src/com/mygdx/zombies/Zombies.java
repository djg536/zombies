package com.mygdx.zombies;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.zombies.states.GameScreen;
import com.mygdx.zombies.states.StateManager;

public class Zombies extends Game {
		
	private StateManager sm;
	
	@Override
	public void create () {		
		sm = new StateManager();
	}

	@Override
	public void render () {		
		sm.gameLoop();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		sm.render();
	}
	
	@Override
	public void dispose () {
		sm.dispose();
	}
}
