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
    
    private Player player;
    private int playerX;
    private int playerY;
    
    private Vector2 playerVector;
    private Vector2 zombieVector;
    private Vector2 directionVector;
    
    private int directionX;
    private int directionY;

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
    	
    	playerVector = new Vector2(player.getPositionX(), player.getPositionY());
    	zombieVector = new Vector2(this.getPositionX(), this.getPositionY());	
    	directionVector = new Vector2((playerVector.x - zombieVector.x), (playerVector.y - zombieVector.y));
    	
        body.applyLinearImpulse(new Vector2(directionVector.x/1000, directionVector.y/1000), body.getPosition(), true);
        
        //body.applyLinearImpulse(new Vector2(velocity.x, velocity.y), body.getPosition(), true);
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
