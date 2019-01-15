package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.StateManager.StateID;

public class EndScreen extends State {

	private Texture banner;
	
	public EndScreen(StateManager stageManager) {
		super(stageManager);
		banner = new Texture("win.png");
	}
	
	@Override
	public void update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
			stateManager.loadState(StateID.MAINMENU, null);
		}
	}
	
	@Override
	public void render() {
		UIBatch.begin();
		UIBatch.draw(banner, Gdx.graphics.getWidth()/2-banner.getWidth()/2, Gdx.graphics.getHeight()-banner.getHeight()-20);
		Zombies.mainFont.draw(UIBatch, "[Insert Monty Python Intermission Here]", 160, 200);
		Zombies.mainFont.draw(UIBatch, "Click to continue", 424, 140);
		UIBatch.end();
	}
	
	@Override
	public void dispose() {
		banner.dispose();
	}
}
