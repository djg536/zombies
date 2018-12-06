package com.mygdx.zombies.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.zombies.Entity;
import com.mygdx.zombies.InfoContainer;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.Level;

public class Projectile extends Entity {

	private SpriteBatch spriteBatch;
	private Sprite sprite;

	public Projectile(Level level, int x, int y, float angle, String spritePath, float speed, Sound sound) {
		
		//Apply bullet spray
		angle += Math.random()*0.2f-0.1f;
		
		//Add sprite
		this.spriteBatch = level.worldBatch;
		sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
		sprite.setRotation((float)Math.toDegrees(angle));

		//Build box2d body
		FixtureDef fixtureDef = new FixtureDef() {
			{
				density = 200;
				friction = 1;
				restitution = 1;
				filter.categoryBits = Zombies.playerFilter;
				isSensor = true;
			}
		};
		GenerateBodyFromSprite(level.box2dWorld, sprite, InfoContainer.BodyID.PROJECTILE, fixtureDef);
		body.setTransform(x / Zombies.PhysicsDensity, y / Zombies.PhysicsDensity, angle);
		body.setBullet(true);
		body.setFixedRotation(true);

		//Apply movement
		body.applyLinearImpulse((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed,
				x / Zombies.PhysicsDensity, y / Zombies.PhysicsDensity, true);
	
		//Play sound effect
		sound.play();
	}

	public void render() {
		sprite.setPosition(body.getPosition().x * Zombies.PhysicsDensity - sprite.getWidth() / 2,
				body.getPosition().y * Zombies.PhysicsDensity - sprite.getWidth() / 2);
		sprite.draw(spriteBatch);
	}

	public void dispose() {
		box2dWorld.destroyBody(body);
	}
}
