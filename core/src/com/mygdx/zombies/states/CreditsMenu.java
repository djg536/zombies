package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.zombies.Zombies;

public class CreditsMenu extends State {
	private Texture background;
	private Button back;
	
	public CreditsMenu(){
		background = new Texture("backround.jpg");
		back = new Button(UIBatch, 500, 10, "Back");
	}
	public void render() {
		UIBatch.begin();
		UIBatch.draw(background, 0, 0);
		back.render();
		Zombies.titleFont.draw(UIBatch, "Credits", 500, 700);
		Zombies.creditsFont.draw(UIBatch, "This is where the credits will go", 150, 550);
		UIBatch.end();		
	}
	
	public int update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			if (back.isHover()) {
				return 3;
			}
			
		}		
		return 0;
	}
}
