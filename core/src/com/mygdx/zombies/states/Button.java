package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.zombies.Zombies;

public class Button {
	
	private SpriteBatch spriteBatch;
	private Texture texture;
	private int positionX;
	private int positionY;
	private String text;
	private Texture hoverTexture;
		
	public Button(SpriteBatch spriteBatch, int x, int y, String draw) {
		this.spriteBatch = spriteBatch;
		texture = new Texture("button.jpg");
		hoverTexture = new Texture("hover_button.jpg");
		positionX = x;
		positionY = y;
		text = draw;
	}
	
	public boolean isHover() {
		//Adjust mouse coordinates in case the window is resized
		float adjustedMouseX = Gdx.input.getX()*Zombies.InitialWindowWidth/(float)Gdx.graphics.getWidth();
		float adjustedMouseY = (Gdx.graphics.getHeight() - Gdx.input.getY())*Zombies.InitialWindowHeight/(float)Gdx.graphics.getHeight();
		
		return new Rectangle(positionX, positionY, texture.getWidth(), texture.getHeight())
						.contains(adjustedMouseX, adjustedMouseY);			
	}
	
	public void render() {
		if (isHover() == true) {
			spriteBatch.draw(hoverTexture, positionX, positionY);
		}
		else {
		spriteBatch.draw(texture, positionX, positionY);
		}
		Zombies.mainFont.draw(spriteBatch, text, (float) ((positionX + 140) - (text.length() * 17.5))  , (float) (positionY + 70));
	}
	
	public void update() {
		Gdx.input.getX();
		Gdx.input.getY();
	}
	
	public void dispose() {
		hoverTexture.dispose();
		texture.dispose();
	}
}
