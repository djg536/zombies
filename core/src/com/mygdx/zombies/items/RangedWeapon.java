package com.mygdx.zombies.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.zombies.Entity;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.Level;

public class RangedWeapon extends Entity implements Weapon {

	private short fireDelay;
	private short timerTicks;
	private Sprite sprite;
	private SpriteBatch spriteBatch;
	private Level level;
	
	public RangedWeapon(Level level) {
		
		this.level = level;
		spriteBatch = level.worldBatch;	
		sprite = new Sprite(new Texture(Gdx.files.internal("gun.png")));
		
		fireDelay = 35;
		timerTicks = 0;
	}
	
	@Override
	public void use() {
		if(timerTicks == 0) {
			timerTicks++;
			Vector2 pos = level.player.getHandsPosition();
			level.bulletsList.add(new Projectile(level, (int)pos.x + level.player.getPositionX(), (int)pos.y + level.player.getPositionY(),
					(float)(level.player.getAngleRadians() + Math.PI / 2), "bullet.png", 1.5f, Zombies.soundShoot));
		}
	}
	
	@Override
	public void update(int x, int y, float rotation) {
		//set position to front part of player sprite, sticking to player rotation and rotating with mouse.
		sprite.setPosition(x, y);		
		sprite.setRotation(rotation);	
		
		if(timerTicks > 0)
			timerTicks++;
		if(timerTicks >= fireDelay) {
			timerTicks = 0;
		}
	}

	@Override
	public void render() {
		sprite.draw(spriteBatch);
	}
}
