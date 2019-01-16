package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.StateManager.StateID;

public class MainMenu extends State {

	private Button play;
	private Button exit;
	private Button options;
	private Button credits;
	private Texture background;
	private Texture logo;

	public MainMenu() {
		super();
		background = new Texture("background.jpg");
		play = new Button(UIBatch, 325, 350, "Play");
		exit = new Button(UIBatch, 675, 150, "Exit");
		options = new Button(UIBatch, 675, 350, "Options");
		credits = new Button(UIBatch, 325, 150, "Credits");
		logo = new Texture("logo.png");
	}

	@Override
	public void render() {
		UIBatch.begin();
		UIBatch.draw(background, 0, 0);
		play.render();
		exit.render();
		options.render();
		credits.render();
		Zombies.titleFont.draw(UIBatch, "Silence Of The Lamberts", 225, 650);
		UIBatch.draw(logo, 1050, 10);
		UIBatch.end();		
	}

	@Override
	public void update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
			
			if (play.isHover()) {
				Zombies.soundSelect.play();
				Zombies.soundAmbientWind.loop();
				StateManager.loadState(StateID.BRIEFINGSCREEN);
			}
			else if (credits.isHover()) {
				Zombies.soundSelect.play();
				StateManager.loadState(StateID.CREDITSMENU);
			}
			else if (options.isHover()) {
				Zombies.soundSelect.play();
				StateManager.loadState(StateID.OPTIONSMENU);
			}
			else if (exit.isHover()) {
				Gdx.app.exit();
			}
		}
	}

	@Override
	public void dispose() {
		// play.dispose();
		// exit.dispose();
		// options.dispose();
		// credits.dispose();
		// background.dispose();
		logo.dispose();
	}
}
