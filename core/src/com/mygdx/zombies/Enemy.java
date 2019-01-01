package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.zombies.states.Level;

public class Enemy extends Entity {
	
	private float speed;
	private int strength;
	private int health;
	protected Sprite sprite;
	protected double angleRads;
	private float totAngle;
	private float wanderAngle;
	protected double angleDegrees;
	private double randomX;
	private double randomY;	
	private int last;
	private double distance;
	private Player player;
	private SpriteBatch spriteBatch;	
	boolean inLights;
	private int noiseTimer;

	public Enemy(Level level, int x, int y, int h, String spritePath) {
		
		//Add sprite
		spriteBatch = level.worldBatch;
		sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));

		//Add box2d body
		FixtureDef fixtureDef = new FixtureDef() {
			{
				density = 40;
				friction = 0.5f;
				restitution = 1f;
			}
		};
		GenerateBodyFromSprite(level.box2dWorld, sprite, InfoContainer.BodyID.ZOMBIE, fixtureDef);
		body.setTransform(x / Zombies.PhysicsDensity, y / Zombies.PhysicsDensity, 0);
		body.setLinearDamping(4);
		body.setFixedRotation(true);
		
		health = h;
		speed = 0.5f;
		this.player = level.getPlayer();
		noiseTimer = 300;
	}

	protected void move() {
		
		angleRads = Zombies.angleBetweenRads(new Vector2(getPositionX(), getPositionY()),
									     new Vector2(player.getPositionX(), player.getPositionY()));
		distance = Zombies.distanceBetween(new Vector2(getPositionX(), getPositionY()),
									new Vector2(player.getPositionX(), player.getPositionY()));

		angleDegrees = Math.toDegrees(angleRads);
		
		if (player.getNoise() > distance || this.sight() == true) {
			
			body.applyLinearImpulse(new Vector2((float) Math.cos(angleRads) * -speed, (float) Math.sin(angleRads) * -speed),
					body.getPosition(), true);
			
			
			sprite.setRotation((float) angleDegrees);
			
			totAngle = (float) angleDegrees; 
			
		} else {
			if ((player.points() % 4 == 0 || player.points() == 0) && player.points() != last) {
				
				// Generates random number that correlates to one of NINE movement states
				// N, NE, E, SE, S, SW, W, NW, Stationary
				// 
				// Messy: To be revised
				
				double rand = Math.random();
				
				if(rand > 0 && rand < 0.1) {
					randomX = 0.2f;
					randomY = 0.2f;
					wanderAngle = -45;
				}
				if(rand > 0.1 && rand < 0.2) {
					randomX = 0.2f;
					randomY = 0;
					wanderAngle = -90;
				}
				if(rand > 0.2 && rand < 0.3) {
					randomX = 0.2f;
					randomY = -0.2f;
					wanderAngle = -135;
				}
				if(rand > 0.3 && rand < 0.4) {
					randomX = 0;
					randomY = -0.2f;
					wanderAngle = -180;
				}
				if(rand > 0.4 && rand < 0.5) {
					randomX = -0.2f;
					randomY = -0.2f;
					wanderAngle = -225;
				}
				if(rand > 0.5 && rand < 0.6) {
					randomX = -0.2f;
					randomY = 0;
					wanderAngle = -270;
				}
				if(rand > 0.6 && rand < 0.7) {
					randomX = -0.2f;
					randomY = 0.2f;
					wanderAngle = -315;
				}
				if(rand > 0.7 && rand < 0.8) {
					randomX = 0;
					randomY = 0.2f;
					wanderAngle = 0;
				}
				if(rand > 0.8) {
					randomX = 0;
					randomY = 0;
				}
						
				last = player.points();
			}
		
			body.applyLinearImpulse(new Vector2((float) randomX, (float) randomY), body.getPosition(), true);
			sprite.setRotation(wanderAngle);
			
			totAngle = wanderAngle;
		}		
	}
	
	private boolean sight() {
		
		// Returns true if the player is within distance < 200 of the zombie
		// and and player is within 90 degrees of the zombie's angle
		
		if(((angleDegrees <= totAngle + 45) || (angleDegrees >= totAngle - 45)) && ((distance < 200) || (inLights == true && distance < 5000))) {
			return true;
		}
		else {
			return false;
		}	
	}

	public void reverseVelocity() {	
		randomX = -randomX;
		randomY = -randomY;
		
		wanderAngle = wanderAngle - 180;
		
	}
	
	//Method to update zombie sound effects timer
	public void noiseStep() {
		noiseTimer--;
		if(noiseTimer <= 0) {
			noiseTimer = Zombies.random.nextInt(2000) + 1000;
			Zombies.soundArrayZombie[1+Zombies.random.nextInt(Zombies.soundArrayZombie.length-1)]
					.play(distance < 500 ? 500-(float)distance : 0);
		}
	}

	public void update(boolean inLights) {
		this.inLights = inLights;
		move();
		sprite.setPosition(getPositionX() - sprite.getWidth() / 2, getPositionY() - sprite.getHeight() / 2);
		noiseStep();
	}

	public int getPositionX() {
		return (int) (body.getPosition().x * Zombies.PhysicsDensity);
	}

	public int getPositionY() {
		return (int) (body.getPosition().y * Zombies.PhysicsDensity);
	}

	public void render() {
		sprite.draw(spriteBatch);
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
