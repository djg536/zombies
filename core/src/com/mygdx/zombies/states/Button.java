package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.zombies.Zombies;

public class Button {

	private SpriteBatch spriteBatch;
	private Sprite mainSprite;
	private Sprite hoverSprite;
	private int positionX;
	private int positionY;
	private String text;
	private int mode;
	private String[] modeTextArray;

	public Button(SpriteBatch spriteBatch, int x, int y, String text) {
		this.text = text;	
		setup(spriteBatch, x, y);
	}
	
	public Button(SpriteBatch spriteBatch, int x, int y, String[] modeTextArray) {
		mode = 0;
		text = modeTextArray[mode];
		this.modeTextArray = modeTextArray;
		setup(spriteBatch, x, y);
	}
	
	private void setup(SpriteBatch spriteBatch, int x, int y) {
		this.spriteBatch = spriteBatch;
		
		mainSprite = new Sprite(new Texture(Gdx.files.internal("button.jpg")));
		mainSprite.setPosition(x, y);
		hoverSprite = new Sprite(new Texture(Gdx.files.internal("hover_button.jpg")));
		hoverSprite.setPosition(x, y);
		
		positionX = x;
		positionY = y;
	}
	
	public void nextMode() {
		mode++;
		if(mode >= modeTextArray.length) {
			mode = 0;
		}
		text = modeTextArray[mode];
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
		text = modeTextArray[mode];
	}

	public boolean isHover() {
		// Adjust mouse coordinates in case the window is resized
		float adjustedMouseX = Gdx.input.getX() * Zombies.InitialWindowWidth / (float) Gdx.graphics.getWidth();
		float adjustedMouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) * Zombies.InitialWindowHeight
				/ (float) Gdx.graphics.getHeight();

		return new Rectangle(positionX, positionY, mainSprite.getWidth(), mainSprite.getHeight())
				.contains(adjustedMouseX, adjustedMouseY);
	}

	public void render() {
		
		if (isHover()) {
			hoverSprite.draw(spriteBatch);
		} else {
			mainSprite.draw(spriteBatch);
		}
		Zombies.mainFont.draw(spriteBatch, text, (float) ((positionX + 148) - (text.length() * 14)), positionY + 69);
	}

	public void update() {
		Gdx.input.getX();
		Gdx.input.getY();
	}

	public void dispose() {
		spriteBatch.dispose();
	}
}
