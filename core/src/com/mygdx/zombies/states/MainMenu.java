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
		play = new Button(UIBatch, 325, 350, "Play");
		exit = new Button(UIBatch, 675, 150 , "Exit");
		options = new Button(UIBatch, 675, 350 , "Options");
		credits = new Button(UIBatch, 325, 150 , "Credits");
	}
	public void render() {
		UIBatch.begin();
		UIBatch.draw(backround, 0, 0);
		play.render();
		exit.render();
		options.render();
		credits.render();
		UIBatch.end();
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
