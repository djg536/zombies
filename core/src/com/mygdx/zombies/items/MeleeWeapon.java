package com.mygdx.zombies.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MeleeWeapon implements Weapon {
	
	private SpriteBatch spriteBatch;
	private Sprite sprite;
	
	public MeleeWeapon(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
		sprite = new Sprite(new Texture(Gdx.files.internal("sword.png")));
	}

	@Override
	public void render() {
		sprite.draw(spriteBatch);
	}

	@Override
	public void use() {
	}

	@Override
	public void update(int x, int y, float rotation) {
		//set position to front part of player sprite, sticking to player rotation and rotating with mouse.
		sprite.setPosition(x, y);
		sprite.setRotation(rotation);			
	}
}
