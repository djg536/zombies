package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.StateManager.StateID;

public class OptionsMenu extends State {
	private Texture background;
	private Button back;
	private Button fullscreen;

	public OptionsMenu(StateManager stateManager) {
		super(stateManager);
		background = new Texture("background.jpg");
		back = new Button(UIBatch, 500, 10, "Back");
		fullscreen = new Button(UIBatch, 500, 450, new String[] { "Fullscreen", "Windowed" });
		if(Gdx.graphics.isFullscreen()) {
			fullscreen.setMode(1);
		}
	}

	@Override
	public void render() {
		UIBatch.begin();
		UIBatch.draw(background, 0, 0);
		back.render();
		fullscreen.render();
		Zombies.titleFont.draw(UIBatch, "Options", 500, 700);
		UIBatch.end();
	}

	@Override
	public void update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
			if (fullscreen.isHover()) {
				
				Zombies.soundSelect.play();	
				fullscreen.nextMode();			
				if(fullscreen.getMode() == 0) {
					Gdx.graphics.setWindowedMode(Zombies.InitialWindowWidth, Zombies.InitialWindowHeight);
				}
				else {
					DisplayMode mode = Gdx.graphics.getDisplayMode();
					Gdx.graphics.setFullscreenMode(mode);
				}
			}
			else if (back.isHover()) {
				Zombies.soundSelect.play();
				stateManager.loadState(StateID.MAINMENU, 0);
			}
		}
	}
}
