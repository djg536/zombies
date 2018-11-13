package com.mygdx.zombies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
	
	private SpriteBatch spriteBatch;
	private Texture texture;
	private int positionX;
	private int positionY;
	
	public Entity(SpriteBatch spriteBatch, Texture texture, int x, int y) {
		this.spriteBatch = spriteBatch;
		this.texture = texture;
		positionX = x;
		positionY = y;
	}
	
	public void render() {
		spriteBatch.draw(texture, positionX, positionY);
	}
}
