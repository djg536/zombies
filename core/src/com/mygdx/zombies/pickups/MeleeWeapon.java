package com.mygdx.zombies.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.zombies.Entity;
import com.mygdx.zombies.InfoContainer;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.Level;

public class MeleeWeapon extends PickUp {
	
	private SpriteBatch spriteBatch;
	private Sprite sprite;
	
	public MeleeWeapon(Level level) {
		super(true);
		spriteBatch = level.worldBatch;
		sprite = new Sprite(new Texture(Gdx.files.internal("sword.png")));
	}
	
	public void render(int x, int y, float rotation) {
		//set position to front part of player sprite, sticking to player rotation and rotating with mouse.
		sprite.setPosition(x, y);
		sprite.setRotation(rotation);
		
		sprite.draw(spriteBatch);
	}
	
	public void dispose() {
		
	}
}
