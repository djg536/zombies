package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.StateManager.StateID;

public class EndScreen extends State {

	private Texture banner;
	
	public EndScreen() {
		super();
		banner = new Texture("win.png");
		Zombies.soundEndMusic.loop();
	}
	
	@Override
	public void update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
			StateManager.loadState(StateID.MAINMENU);
		}
	}
	
	@Override
	public void render() {
		UIBatch.begin();
		UIBatch.draw(banner, Zombies.InitialWindowWidth/2-banner.getWidth()/2, Zombies.InitialWindowHeight-banner.getHeight()-20);
		Zombies.mainFont.draw(UIBatch, "Click to continue", 424, 140);
		UIBatch.end();
	}
	
	@Override
	public void dispose() {
		Zombies.soundEndMusic.stop();
		banner.dispose();
	}
}
