package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Button {
	private SpriteBatch spriteBatch;
	private Texture texture;
	private int positionX;
	private int positionY;
	private BitmapFont font12;
	private String draw;
	private boolean isHover;
	
	
	public Button(SpriteBatch spriteBatch, int x, int y, String draw) {
		this.spriteBatch = spriteBatch;
		texture = new Texture("button.jpg");
		positionX = x;
		positionY = y;
		this.draw = draw;
		isHover = false;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("NESCyrillic.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 75;
		font12 = generator.generateFont(parameter); 
		generator.dispose(); 
	}
	public void render() {
		spriteBatch.draw(texture, positionX, positionY);
		font12.draw(spriteBatch, draw, positionX + 65, positionY + 70);
	}
	public void update() {
		Gdx.input.getX();
		Gdx.input.getY();
	}
	public void ClickListener(int button) {
		
	}
}
