package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.zombies.Zombies;

public class MainMenu extends State {
	
	private Button play;
	private Button exit;
	private Button options;
	private Button credits;
	private Texture background;
	
	public MainMenu() {
		background = new Texture("backround.jpg");
		play = new Button(UIBatch, 325, 350, "Play");
		exit = new Button(UIBatch, 675, 150 , "Exit");
		options = new Button(UIBatch, 675, 350 , "Options");
		credits = new Button(UIBatch, 325, 150 , "Credits");
	}

	public void render() {
		UIBatch.begin();
		UIBatch.draw(background, 0, 0);
		play.render();
		exit.render();
		options.render();
		credits.render();
		Zombies.titleFont.draw(UIBatch, "Silence Of The Lamberts", 225, 650);
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
	
	@Override
	public void dispose() {
		play.dispose();
		exit.dispose();
		options.dispose();
		credits.dispose();
		background.dispose();
	}
}
