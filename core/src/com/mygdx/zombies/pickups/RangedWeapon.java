package com.mygdx.zombies.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.zombies.states.Level;

public class RangedWeapon extends PickUp {

	private short fireDelay;
	private short timerTicks;
	private Sprite sprite;
	private SpriteBatch spriteBatch;
	
	public RangedWeapon(Level level) {
		super(true);
		
		spriteBatch = level.worldBatch;
		
		sprite = new Sprite(new Texture(Gdx.files.internal("pickups/pistol.png")));
		sprite.setPosition(200, 200);
		
		fireDelay = 35;
		timerTicks = 0;
	}
	
	public boolean pullTrigger() {
		if(timerTicks == 0) {
			timerTicks++;
			return true;
		}
		return false;
	}
	
	public void update() {
		if(timerTicks > 0)
			timerTicks++;
		if(timerTicks >= fireDelay) {
			timerTicks = 0;
		}
	}
	
	public void render() {
		sprite.draw(spriteBatch);
	}
}
