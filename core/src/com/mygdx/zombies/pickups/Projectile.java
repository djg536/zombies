package com.mygdx.zombies.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.zombies.Entity;
import com.mygdx.zombies.InfoContainer;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.Level;

public class Projectile extends Entity {

	private SpriteBatch spriteBatch;
	private Sprite sprite;

	public Projectile(Level level, int x, int y, float angle) {
		
		spriteBatch = level.worldBatch;
		sprite = new Sprite(new Texture(Gdx.files.internal("bullet.png")));
		sprite.setRotation((float)Math.toDegrees(angle));

		box2dWorld = level.box2dWorld;
		body = box2dWorld.createBody(level.mob);
		final PolygonShape polyShape = new PolygonShape();
		polyShape.setAsBox(sprite.getWidth() / 2 / Zombies.PhysicsDensity,
				sprite.getHeight() / 2 / Zombies.PhysicsDensity);

		FixtureDef fixtureDef = new FixtureDef() {
			{
				shape = polyShape;
				density = 200f;
				friction = 0;
				restitution = 1f;
				filter.categoryBits = Zombies.playerFilter;
			}
		};

		body.createFixture(fixtureDef);
		body.setUserData(new InfoContainer(InfoContainer.BodyID.PROJECTILE, this));

		body.setTransform(x / Zombies.PhysicsDensity, y / Zombies.PhysicsDensity, angle);
		polyShape.dispose();
		body.setBullet(true);
		body.setFixedRotation(true);

		final float speed = 1.5f;
		body.applyLinearImpulse((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed,
				x / Zombies.PhysicsDensity, y / Zombies.PhysicsDensity, true);
	
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/gun.wav"));
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
