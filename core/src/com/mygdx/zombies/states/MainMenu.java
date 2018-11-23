package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MainMenu extends State {
	private Button play;
	private Button exit;
	public MainMenu() {
		play = new Button(spriteBatch, 500, 500, "Play");
		exit = new Button(spriteBatch, 500, 350 , "Exit");
	}
	public void render() {
		spriteBatch.begin();
		play.render();
		exit.render();
		spriteBatch.end();
	}
	@Override
	public boolean update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			if (exit.isHover()) {
				Gdx.app.exit();
			}
		}		
		return false;
	}
}
