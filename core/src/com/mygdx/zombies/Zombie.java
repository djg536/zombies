package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.zombies.states.Level;
import com.mygdx.zombies.states.LevelContactListener;

public class Zombie {
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
    
    private double randomX = 0.3;
    private double randomY = 0.3;
    
    private double distance;
    
    private Player player;
   
    private SpriteBatch spriteBatch;

    private Level level;
    private Body body;
    private Vector2 velocity;

    public Zombie(Level level, int x, int y, int h, Player player) {
        spriteBatch = level.worldBatch;
        sprite = new Sprite(new Texture(Gdx.files.internal("zombie/zombie.png")));

        body = level.box2dWorld.createBody(level.mob);
        final PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(sprite.getWidth()/2/Zombies.PhysicsDensity, sprite.getHeight()/2/Zombies.PhysicsDensity);
        FixtureDef fixtureDef = new FixtureDef() {{
            shape = polyShape; density = 40; friction = 0.5f; restitution = 1f; }};
        body.createFixture(fixtureDef);
        body.setTransform(x/Zombies.PhysicsDensity, y/Zombies.PhysicsDensity, 0);
        body.setLinearDamping(4);
        body.setUserData("zombie");
        velocity = new Vector2(1,0);
        polyShape.dispose();
        
        this.player = player;
    }

    protected void attack() {

    }
    
    protected void move() {
    	
    	positionX = this.getPositionX();
    	positionY = this.getPositionY();
    	
    	playerX = player.getPositionX();
    	playerY = player.getPositionY();
    	
    	distanceX = Math.abs(positionX - playerX);
    	distanceY = Math.abs(positionY - playerY);
    	
    	if(playerX != positionX && playerY != positionY) {
    		distance = Math.sqrt((distanceX*distanceX)+(distanceY*distanceY));
    	}
    	else if(distanceX == 0) {
    		distance = distanceY;
    	}
    	else if(distanceY == 0) {
    		distance = distanceX;
    	}
    	
    	distance = distance*10;
    	
    	if(player.getNoise() > distance) {
    		
    		System.out.println(player.getNoise() + ", " + distance);
    	
	    	if(playerX > positionX) {		
	    		body.applyLinearImpulse(new Vector2((float) 0.5, 0), body.getPosition(), true);
	    	}
	    	else if(playerX < positionX) {
	    		body.applyLinearImpulse(new Vector2((float) -0.5, 0), body.getPosition(), true);
	    	}
	    	
	    	if(playerY > positionY) {
	    		body.applyLinearImpulse(new Vector2(0, (float) 0.5), body.getPosition(), true);
	    	}
	    	else if(playerY < positionY) {
	    		body.applyLinearImpulse(new Vector2(0, (float) -0.5), body.getPosition(), true);
	    	}
    	}
    	else {  		
    		if(player.points() % 6 == 0){
        		
        		double signX = Math.random();
        		double signY = Math.random();
        		
        		if(signX >= 0.5) {
        			randomX = -randomX;
        		}
        		if(signY >= 0.5) {
        			randomY = -randomY;
        		}
    		}
    		
    		System.out.println(randomX + ", " + randomY);
    		body.applyLinearImpulse(new Vector2((float) randomX, (float) randomY), body.getPosition(), true);
    	}
    }

    public void reverseVelocity() {
        velocity.x = -velocity.x;
        velocity.y = -velocity.y;
    }

    public void update() {
        move();
        sprite.setPosition(getPositionX()-sprite.getWidth()/2, getPositionY()-sprite.getHeight()/2);
    }

    public int getPositionX(){
        return (int) (body.getPosition().x*Zombies.PhysicsDensity);
    }

    public int getPositionY(){
        return (int) (body.getPosition().y*Zombies.PhysicsDensity);
    }

    public void render() {
        sprite.draw(spriteBatch);
    }

    public void dispose() {
        spriteBatch.dispose();
    }
}
