package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.StateManager.StateID;

public class PlayerSelectMenu extends State {
	
	private Button player1;
	private Button player2;
	private Button player3;
	private Sprite sprite1; 
	private Sprite sprite2; 
	private Sprite sprite3; 
	
	public PlayerSelectMenu(StateManager stateManager)
	{
		super(stateManager);
		
		player1 = new Button(UIBatch, 50, 50, "Player 1");
		player2 = new Button(UIBatch, 500, 50, "Player 2");
		player3 = new Button(UIBatch, 950, 50, "Player 3");
		sprite1 = new Sprite(new Texture(Gdx.files.internal("player/player1_unequipped_fat.png")));
		sprite1.setPosition(150, 400);
		sprite1.scale(3);
		sprite2 = new Sprite(new Texture(Gdx.files.internal("player/player2_unequipped_fat.png")));
		sprite2.setPosition(600, 400);
		sprite2.scale(3);
		sprite3 = new Sprite(new Texture(Gdx.files.internal("player/player3_unequipped_fat.png")));
		sprite3.setPosition(1000, 400);
		sprite3.scale(3);
	}
	
	@Override
	public void render() {
		UIBatch.begin();
		player1.render();
		player2.render();
		player3.render();
		sprite1.draw(UIBatch);
		sprite2.draw(UIBatch);
		sprite3.draw(UIBatch);
		UIBatch.end();
	}
	
	@Override
	public void update() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
				if (player1.isHover()) {
					Zombies.soundSelect.play();
					stateManager.loadState(StateID.STAGE1, 1);
				}
				else if (player2.isHover()) {
					Zombies.soundSelect.play();
					stateManager.loadState(StateID.STAGE1, 2);
				}
				else if (player3.isHover()) {
					Zombies.soundSelect.play();
					stateManager.loadState(StateID.STAGE1, 3);
				}
		}
	}
}
