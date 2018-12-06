package com.mygdx.zombies.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.zombies.states.Level;

public class RangedWeapon implements Weapon {

	private int shootDelay;
	private int timerTicks;
	private Sprite sprite;
	private SpriteBatch spriteBatch;
	private Level level;
	private String bulletSpritePath;
	private Sound shootSound;
	private float bulletSpeed;
	
	public RangedWeapon(Level level, int shootDelay, String bulletSpritePath, float bulletSpeed, Sound shootSound) {
		
		this.level = level;
		spriteBatch = level.worldBatch;	
		sprite = new Sprite(new Texture(Gdx.files.internal("gun.png")));

		this.shootDelay = shootDelay;
		this.bulletSpritePath = bulletSpritePath;
		this.shootSound = shootSound;
		this.bulletSpeed = bulletSpeed;
		
		timerTicks = 0;
	}
	
	@Override
	public void use() {
		if(timerTicks == 0) {
			timerTicks++;
			Vector2 pos = level.player.getHandsPosition();
			level.bulletsList.add(new Projectile(level, (int)pos.x + level.player.getPositionX(), (int)pos.y + level.player.getPositionY(),
					(float)(level.player.getAngleRadians() + Math.PI / 2), bulletSpritePath, bulletSpeed, shootSound));
		}
	}
	
	@Override
	public void update(int x, int y, float rotation) {
		//set position to front part of player sprite, sticking to player rotation and rotating with mouse.
		sprite.setPosition(x, y);		
		sprite.setRotation(rotation);	
		
		if(timerTicks > 0)
			timerTicks++;
		if(timerTicks >= shootDelay) {
			timerTicks = 0;
		}
	}

	@Override
	public void render() {
		sprite.draw(spriteBatch);
	}
}
