package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.StateManager.StateID;

public class BriefingScreen extends State {

	private Sprite banner;
	
	public BriefingScreen(StateManager stateManager) {
		super(stateManager);
		banner = new Sprite(new Texture("header.jpg"));
		banner.setScale(4.3f);		
		banner.setPosition(0, Zombies.InitialWindowHeight-banner.getHeight());
	}

	@Override
	public void update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
			stateManager.loadState(StateID.PLAYERSELECTMENU);
		}
	}
	
	@Override
	public void render() {
		UIBatch.begin();
		banner.draw(UIBatch);
		Zombies.mainFont.draw(UIBatch, "The apocalyse has arrived, and The University of\n"
									 + "York is now swarming with zombies. There are few\n"
									 + "survivors, but the fight is not over yet.\n"
									 + "Find a way to end this disaster.\n"
									 + "Be careful!", 20, 340);
		Zombies.mainFont.draw(UIBatch, "Click to continue", 424, 50);
		UIBatch.end();
	}
	
	@Override
	public void dispose() {
		banner.getTexture().dispose();
	}
}
