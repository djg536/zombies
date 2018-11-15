package com.mygdx.zombies.states;

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
}
