package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.zombies.states.Level;

public class PickUp extends Entity {
	
	private SpriteBatch spriteBatch;
	private Sprite sprite;
	private Object containedItem;

	public PickUp(Level level, int x, int y, String pickUpSpritePath, Object containedItem, InfoContainer.BodyID type) {
		spriteBatch = level.worldBatch;
		sprite = new Sprite(new Texture(Gdx.files.internal(pickUpSpritePath)));
		sprite.setPosition(x, y);
	
		this.containedItem = containedItem;
		
		FixtureDef fixtureDef = new FixtureDef() {
			{
				isSensor = true;
			}
		};
		GenerateBodyFromSprite(level.box2dWorld, sprite, type, fixtureDef);
		
		body.setTransform((x+sprite.getWidth()/2) / Zombies.PhysicsDensity,
				(y+sprite.getHeight()/2) / Zombies.PhysicsDensity, 0);
	}	
	
	public Object getContainedItem() {
		return containedItem;
	}
	
	public void render() {
		sprite.draw(spriteBatch);
	}
}
