package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.zombies.states.Level;

public class Boss1 extends Enemy {
	
	private Sprite armLeft;
	private Sprite armRight;
	private SpriteBatch spriteBatch;
	private int attackStep;
	private int minionSpawnStep;
	private Level level;

	public Boss1(Level level, int x, int y) {	
		super(level, x, y, "zombie/boss1_head.png", 50, 1);	
		
		this.level = level;
		spriteBatch = level.worldBatch;
		armLeft = new Sprite(new Texture(Gdx.files.internal("zombie/boss1_armLeft.png")));
		armRight = new Sprite(new Texture(Gdx.files.internal("zombie/boss1_armRight.png")));
		
		attackStep = 0;
		minionSpawnStep = 0;
	}
	
	@Override
	public void update(boolean inLights) {
		super.update(inLights);
		
		attackStep++;
		if(attackStep > 100)
			attackStep = 0;
		
		minionSpawnStep++;
		if(minionSpawnStep > 300) {
			minionSpawnStep = 0;			
			Zombie1 minion = new Zombie1(level, getPositionX(), getPositionY());
			level.getEnemiesList().add(minion);
		}
		
		
		Vector2 leftHandPos = getLeftHandPosition();
		Vector2 rightHandPos = getRightHandPosition();
		
		armLeft.setPosition(getPositionX() + leftHandPos.x, getPositionY() + leftHandPos.y);
		armLeft.setRotation((float) angleDegrees);
		armRight.setPosition(getPositionX() + rightHandPos.x, getPositionY() + rightHandPos.y);
		armRight.setRotation((float) angleDegrees);
	}
	
	public Vector2 getLeftHandPosition() {	
		double leftHandAngle = angleRads - 0.2f;
		float x = (float)Math.toDegrees(Math.cos(leftHandAngle))*-attackStep/60;
		float y = (float)Math.toDegrees(Math.sin(leftHandAngle))*-attackStep/60;
		
		return new Vector2(x-sprite.getWidth()/2, y);
	}
	
	public Vector2 getRightHandPosition() {	
		double rightHandAngle = angleRads + 0.2f;
		float x = (float)Math.toDegrees(Math.cos(rightHandAngle))*+(attackStep-60)/60;
		float y = (float)Math.toDegrees(Math.sin(rightHandAngle))*+(attackStep-60)/60;
		
		return new Vector2(x-sprite.getWidth()/2, y);
	}
		
	@Override
	public void render() {
		super.render();
		armLeft.draw(spriteBatch);
		armRight.draw(spriteBatch);
	}
}
