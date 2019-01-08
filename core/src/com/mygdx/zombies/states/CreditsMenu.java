package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.StateManager.StateID;

public class CreditsMenu extends State {
	private Texture background;
	private Button back;

	public CreditsMenu(StateManager stateManager) {
		super(stateManager);
		background = new Texture("backround.jpg");
		back = new Button(UIBatch, 500, 10, "Back");
	}

	@Override
	public void render() {
		UIBatch.begin();
		UIBatch.draw(background, 0, 0);
		back.render();

		Zombies.titleFont.draw(UIBatch, "Credits", 500, 700);
		Zombies.creditsFont.draw(UIBatch, "This game is created by Yeezy Games, the"
				+ "\nmembers that helped produce this game are:"
				+ "\nGurveer Gawera, Billy Macleod, Rafee Jenkins,"
				+ "\nAndy McIsaac, Henry Gray and David Gillman.", 150, 550);
		UIBatch.end();
	}

	@Override
	public void update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if (back.isHover()) {
				Zombies.soundSelect.play();
				stateManager.loadState(StateID.MAINMENU);
			}
		}
	}
}
