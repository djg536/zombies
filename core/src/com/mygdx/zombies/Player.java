package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.zombies.items.MeleeWeapon;
import com.mygdx.zombies.items.PowerUp;
import com.mygdx.zombies.items.Weapon;
import com.mygdx.zombies.states.Level;

import java.lang.Math;

public class Player extends Entity {

	private String name;
	private int speed;
	private int health;
	private float points = 18110;
	private String pointDisplay = "18110.0";
	private Sprite sprite;

	private double angleRads;
	private float angleDegrees;

	private float time;
	private int timer;
	private int last;

	private Sprite hud;
	private SpriteBatch spriteBatch;
	private SpriteBatch UIBatch;
	private byte swingStep;
	private byte swingDirection;
	
	private PowerUp powerUp;
	private Weapon weapon;

	public Player(Level level, int x, int y, int health) {
		spriteBatch = level.worldBatch;
		UIBatch = level.UIBatch;

		sprite = new Sprite(new Texture(Gdx.files.internal("block.png")));
		hud = new Sprite(new Texture(Gdx.files.internal("block.png")));
		this.health = health;
		
		FixtureDef fixtureDef = new FixtureDef() {
			{
				density = 40;
				friction = 0.5f;
				restitution = 0f;
				filter.maskBits = Zombies.projectileFilter;
				filter.categoryBits = Zombies.playerFilter;
			}
		};		
		GenerateBodyFromSprite(level.box2dWorld, sprite, InfoContainer.BodyID.PLAYER, fixtureDef);
		body.setTransform(x / Zombies.PhysicsDensity, y / Zombies.PhysicsDensity, 0);
		body.setLinearDamping(6);
		body.setFixedRotation(true);

		swingStep = 0;
		swingDirection = -1;	
	}
	
	public void SetWeapon(Weapon weapon) {
		Zombies.soundAmmo.play();
		this.weapon = weapon;
	}
	
	/**
	 * @return the relative rotation of the player's hands in degrees
	 */
	public float getHandsRotation() {
		return swingStep*5;
	}
	
	/**
	 * @return the relative position of the player's hands
	 */
	public Vector2 getHandsPosition() {						
		float x = (float)Math.toDegrees(Math.cos(angleRads + swingStep/10.f))*0.5f;
		float y = (float)Math.toDegrees(Math.sin(angleRads + swingStep/10.f))*0.5f;
		
		return new Vector2(x-sprite.getWidth()/2, y);
	}
	
	public int points() {
		time += Gdx.graphics.getDeltaTime()/10;
		timer = Math.round(time);
	
		if (timer % 2 == 0 && timer != last) {
			points -= Math.round(Math.random() * 10);
			pointDisplay = Integer.toString((int) points);
			
			last = timer;
		}
		
		return timer;
	}	

	public double getNoise() {
		double noise;
		
		if (body.getLinearVelocity().x == 0) {
			noise = Math.abs(body.getLinearVelocity().y);
		} else if (body.getLinearVelocity().y == 0) {
			noise = Math.abs(body.getLinearVelocity().x);
		} else if (Math.abs(body.getLinearVelocity().x) > 0 && Math.abs(body.getLinearVelocity().y) > 0) {
			noise = Math.sqrt((body.getLinearVelocity().x * body.getLinearVelocity().x)
					+ (body.getLinearVelocity().y * body.getLinearVelocity().y));
		} else {
			noise = 0;
		}
			
		noise = noise * 250;

		return noise;
	}

	public int health() {
		if (Gdx.input.isKeyPressed(Keys.SPACE))
			health -= 1;
		if (health <= 0) {
			System.out.println("RESTART");
			return 1;
		}
		return 0;
	}

	private void look(Vector3 mouseCoords) {
		
		angleRads = Zombies.angleBetweenRads(new Vector2(getPositionX(), getPositionY()),
				new Vector2(mouseCoords.x, mouseCoords.y)) + Math.PI/2;
		angleDegrees = (float) Math.toDegrees(angleRads);
		sprite.setRotation(angleDegrees);
	}

	private void move() {
		int speedBoost = powerUp==null ? 1 : powerUp.getSpeedBoost();
		Vector2 playerPosition = body.getPosition();
		
		if (Gdx.input.isKeyPressed(Keys.W))
			body.applyLinearImpulse(new Vector2(0, 1*speedBoost), playerPosition, true);
		else if (Gdx.input.isKeyPressed(Keys.S))
			body.applyLinearImpulse(new Vector2(0, -1*speedBoost), playerPosition, true);

		if (Gdx.input.isKeyPressed(Keys.A))
			body.applyLinearImpulse(new Vector2(-1*speedBoost, 0), playerPosition, true);
		else if (Gdx.input.isKeyPressed(Keys.D))
			body.applyLinearImpulse(new Vector2(1*speedBoost, 0), playerPosition, true);
	}
	
	/**
	 * @return true if hands are moving
	 */
	public boolean isSwinging() {
		return swingStep > 0 && swingStep < 30;
	}

	public int update(Vector3 mouseCoords, boolean inLights) {
		
		if (weapon != null) {
			Vector2 h = getHandsPosition();
			Vector2 pos = new Vector2(getPositionX() + h.x, getPositionY() + h.y);
			float rot = angleDegrees;
			if(weapon instanceof MeleeWeapon)
				swingUpdate();
			else
				swingStep=18;
			rot += getHandsRotation();
			weapon.update((int)(pos.x),
					(int)(pos.y), rot);
			if (Gdx.input.isButtonPressed(Buttons.LEFT))
				weapon.use();
		}
			
		
		move();
		look(mouseCoords);
		
		sprite.setPosition(getPositionX() - sprite.getWidth() / 2, getPositionY() - sprite.getHeight() / 2);
		points();
		return health();
	}
	
	/**
	 * Method to deal with hand movement update
	 */
	public void swingUpdate() {
		if(isSwinging())
			swingStep += swingDirection;
		else {
				if(Gdx.input.isButtonPressed(Buttons.LEFT)){
					Zombies.soundSwing.play();
					swingDirection *= -1;
					swingStep+=swingDirection;	
			}
		}		
	}

	public void render() {
		sprite.draw(spriteBatch);
		
		if(weapon != null) {
			weapon.render();
		}
	}

	public void hudRender() {
		Zombies.pointsFont.draw(UIBatch, pointDisplay, 100, 590);

		for (int i = 0; i < health; i++) {
			hud.setPosition(100 + i * 50, 620);
			hud.draw(UIBatch);
		}
	}

	public int getPositionX() {
		return (int) (body.getPosition().x * Zombies.PhysicsDensity);
	}

	public int getPositionY() {
		return (int) (body.getPosition().y * Zombies.PhysicsDensity);
	}

	public Body getBody() {
		return body;
	}

	public double getAngleRadians() {
		return angleRads;
	}
	
	public double getAngleDegrees() {
		return angleDegrees;
	}

	public void setPowerUp(PowerUp powerUp) {
		Zombies.soundPowerUp.play();
		this.powerUp = powerUp;
		health += powerUp.getHealthBoost();
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		if(health <= 0)						
			getInfo().flagForDeletion();
	}
}
