package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Button {
	private SpriteBatch spriteBatch;
	private Texture texture;
	private int positionX;
	private int positionY;
	private BitmapFont font12;
	private String draw;
	private Texture hoverTexture;
	
	
	public Button(SpriteBatch spriteBatch, int x, int y, String draw) {
		this.spriteBatch = spriteBatch;
		texture = new Texture("button.jpg");
		hoverTexture = new Texture("hover_button.jpg");
		positionX = x;
		positionY = y;
		this.draw = draw;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("NESCyrillic.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 75;
		font12 = generator.generateFont(parameter); 
		generator.dispose(); 
	}
	public boolean isHover() {
		return new Rectangle(positionX, positionY, texture.getWidth(), texture.getHeight()).contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY());
			

	}
	public void render() {
		if (isHover() == true) {
			spriteBatch.draw(hoverTexture, positionX, positionY);
		}
		else {
		spriteBatch.draw(texture, positionX, positionY);
		}
		font12.draw(spriteBatch, draw, positionX + 65, positionY + 70);
	}
	public void update() {
		Gdx.input.getX();
		Gdx.input.getY();
	}

}
