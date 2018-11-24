package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class MainMenu extends State {
	private Button play;
	private Button exit;
	private Button options;
	private Button credits;
	private Texture backround;
	
	public MainMenu() {
		backround = new Texture("backround.jpg");
		play = new Button(spriteBatch, 325, 350, "Play");
		exit = new Button(spriteBatch, 675, 150 , "Exit");
		options = new Button(spriteBatch, 675, 350 , "Options");
		credits = new Button(spriteBatch, 325, 150 , "Credits");
	}
	public void render() {
		spriteBatch.begin();
		spriteBatch.draw(backround, 0, 0);
		play.render();
		exit.render();
		options.render();
		credits.render();
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
