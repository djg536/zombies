package com.mygdx.zombies.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.zombies.InfoContainer;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.Level;

public class PowerUp extends PickUp {
	
	private int speedBoost;
	private int healthBoost;
	private Sprite sprite;
	private SpriteBatch spriteBatch;
	
	public PowerUp(int speedBoost, int healthBoost, String spritePath, Level level) {
		super(false);
		this.speedBoost = speedBoost+1;
		this.healthBoost = healthBoost;
		sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
		sprite.setPosition(100, 200);
		this.spriteBatch = level.worldBatch;
		
		this.box2dWorld = level.box2dWorld;
		body = box2dWorld.createBody(level.mob);
		final PolygonShape polyShape = new PolygonShape();
		polyShape.setAsBox(sprite.getWidth() / 2 / Zombies.PhysicsDensity,
				sprite.getHeight() / 2 / Zombies.PhysicsDensity);
		FixtureDef fixtureDef = new FixtureDef() {
			{
				shape = polyShape;
				density = 40;
				friction = 0.5f;
				restitution = 1f;
			}
		};
		body.createFixture(fixtureDef);
		body.setUserData(new InfoContainer(InfoContainer.BodyID.PICKUP, this));
		body.setTransform(100 / Zombies.PhysicsDensity, 200 / Zombies.PhysicsDensity, 0);
		polyShape.dispose();
	}

	public int getSpeedBoost() {
		return speedBoost;
	}
	
	public int getHealthBoost() {
		return healthBoost;
	}
	
	public void render() {
		sprite.draw(spriteBatch);
	}
}
