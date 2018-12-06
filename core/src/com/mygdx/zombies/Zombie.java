package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.zombies.states.Level;

public class Zombie extends Entity {
	private int speed;
	private int strength;
	private int health;

	private Sprite sprite;
	private int rotation;
	private int positionX;
	private int positionY;

	private int playerX;
	private int playerY;
	private int distanceX;
	private int distanceY;
	
	private int directionX;
	private int directionY;
	private int normalX;
	private int normalY;
	private double angleRads;
	private float totAngle;
	private float wanderAngle;
	private float zpAngle;
	
	private boolean change;

	private double randomX;
	private double randomY;
	
	private int last;

	private double distance;

	private Player player;

	private SpriteBatch spriteBatch;

	private Level level;
	private Vector2 velocity;

	public Zombie(Level level, int x, int y, int h, Player player) {
		
		//Add sprite
		spriteBatch = level.worldBatch;
		sprite = new Sprite(new Texture(Gdx.files.internal("zombie/zombie.png")));

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
		velocity = new Vector2(1, 0);
		
		health = 5;
		this.player = player;
	}

	protected void attack() {

	}

	protected void move() {
		
		// Distance between zombie and player
		
		positionX = this.getPositionX();
		positionY = this.getPositionY();

		playerX = player.getPositionX();
		playerY = player.getPositionY();

		distanceX = Math.abs(positionX - playerX);
		distanceY = Math.abs(positionY - playerY);

		if (playerX != positionX && playerY != positionY) {
			distance = Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
		} else if (distanceX == 0) {
			distance = distanceY;
		} else if (distanceY == 0) {
			distance = distanceX;
		}
			
		// Angle between player and zombie
		
		directionX = playerX - positionX;
		directionY = playerY - positionY;

		normalX = 0;

		double dotProduct = normalX * directionX + normalY * directionY;
		double sizeA = (Math.sqrt(normalX * normalX + normalY * normalY));
		double sizeB = (Math.sqrt(directionX * directionX + directionY * directionY));

		angleRads = Math.acos(dotProduct / (sizeA * sizeB));

		if (playerX > positionX) {
			angleRads = -angleRads;
		}

		double angle = Math.toDegrees(angleRads);

		zpAngle = (float) angle;
		
		if (player.getNoise() > distance || this.sight() == true) {
			
			if (playerX > positionX) {
				body.applyLinearImpulse(new Vector2(0.5f, 0), body.getPosition(), true);
			} else if (playerX < positionX) {
				body.applyLinearImpulse(new Vector2(-0.5f, 0), body.getPosition(), true);
			}
			
			if (playerY > positionY) {
				body.applyLinearImpulse(new Vector2(0, 0.5f), body.getPosition(), true);			
				normalY = positionY + 10;
			} else if (playerY < positionY) {
				body.applyLinearImpulse(new Vector2(0, -0.5f), body.getPosition(), true);	
				normalY = positionY - 10;
			}
			
			sprite.setRotation(zpAngle);
			
			totAngle = zpAngle; 
			
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
		
		if(distance < 200 && ((zpAngle <= totAngle + 45) || (zpAngle >= totAngle - 45))) {
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

	public void update() {
		move();
		sprite.setPosition(getPositionX() - sprite.getWidth() / 2, getPositionY() - sprite.getHeight() / 2);
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
		if(health <= 0) {						
			getInfo().flagForDeletion();
		}
	}
}
