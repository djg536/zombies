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
	private BitmapFont font;
	private String text;
	private Texture hoverTexture;
	private BitmapFont titleFont;
	
	
	
	public Button(SpriteBatch spriteBatch, int x, int y, String draw) {
		this.spriteBatch = spriteBatch;
		texture = new Texture("button.jpg");
		hoverTexture = new Texture("hover_button.jpg");
		positionX = x;
		positionY = y;
		text = draw;
		
		font = Zombies.GenerateFont("NESCyrillic.ttf", 75);
		titleFont = Zombies.GenerateFont("Amatic-Bold.ttf", 150);	
	}
	
	public boolean isHover() {
		return new Rectangle(positionX,
							 positionY,
							 texture.getWidth(),
							 texture.getHeight())
				
							.contains(Gdx.input.getX()*(Gdx.graphics.getWidth()/Zombies.InitialWindowWidth),
									  Gdx.graphics.getHeight() - Gdx.input.getY()*(Gdx.graphics.getHeight()/Zombies.InitialWindowHeight));			
	}
	public void render() {
		titleFont.draw(spriteBatch, "Silence Of The Lamberts", 225, 650);
		if (isHover() == true) {
			spriteBatch.draw(hoverTexture, positionX, positionY);
		}
		else {
		spriteBatch.draw(texture, positionX, positionY);
		}
		font.draw(spriteBatch, text, (float) ((positionX + 140) - (text.length() * 17.5))  , (float) (positionY + 70));
	}
	public void update() {
		Gdx.input.getX();
		Gdx.input.getY();
	}

}
