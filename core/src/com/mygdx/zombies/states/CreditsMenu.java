package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.zombies.Zombies;

public class CreditsMenu extends State {
	private Texture background;
	private Button back;

	public CreditsMenu() {
		background = new Texture("backround.jpg");
		back = new Button(UIBatch, 500, 10, "Back");
	}

	@Override
	public void render() {
		UIBatch.begin();
		UIBatch.draw(background, 0, 0);
		back.render();

		Zombies.titleFont.draw(UIBatch, "Credits", 500, 700);
		Zombies.creditsFont.draw(UIBatch, "This game is created by Yeezy Games, the", 150, 550);
		Zombies.creditsFont.draw(UIBatch, "memebers that helped produce this game are:", 150, 500);
		Zombies.creditsFont.draw(UIBatch, "Gurveer Gawera, Billy Macleod, Rafee Jenkins,", 150, 450);
		Zombies.creditsFont.draw(UIBatch, "Andy McIsaac, Henry Gray and David Gillman.", 150, 400);
		UIBatch.end();
	}

	@Override
	public int update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if (back.isHover()) {
				return 3;
			}

		}
		return 0;
	}
}
