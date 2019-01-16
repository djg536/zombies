package com.mygdx.zombies.items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.zombies.states.Level;

public class RangedWeapon implements Weapon {

	private int shootDelay;
	private int timerTicks;
	private Level level;
	private String bulletSpritePath;
	private Sound shootSound;
	private float bulletSpeed;
	public static boolean firing = false;
	
	public RangedWeapon(Level level, int shootDelay, String bulletSpritePath, float bulletSpeed, Sound shootSound) {
		
		this.level = level;		
		this.shootDelay = shootDelay;
		this.bulletSpritePath = bulletSpritePath;
		this.shootSound = shootSound;
		this.bulletSpeed = bulletSpeed;
		
		timerTicks = 0;
	}
	
	public static boolean isFiring() {
		return firing;
	}
	
	@Override
	public void use() {
		if(timerTicks == 0) {
			timerTicks++;
			Vector2 pos = level.player.getHandsPosition();
			level.bulletsList.add(new Projectile(level, (int)pos.x + level.player.getPositionX(), (int)pos.y + level.player.getPositionY(),
					(float)(level.player.getAngleRadians() + Math.PI/2), bulletSpritePath, bulletSpeed, shootSound));
			firing = true;
		}
	}
	
	@Override
	public void update(int x, int y, float rotation) {
		
		if(timerTicks > 0)
			timerTicks++;
		if(timerTicks >= shootDelay) {
			timerTicks = 0;
			firing = false;
		}
	}

	@Override
	public void render() {
	}
}
