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

    private SpriteBatch spriteBatch;

    private Level level;
    private Body body;
    private Vector2 velocity;

    public Zombie(Level level, int x, int y, int h) {
        spriteBatch = level.worldBatch;
        sprite = new Sprite(new Texture(Gdx.files.internal("zombie/zombie.png")));

        body = level.box2dWorld.createBody(level.mob);
        final PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);
        FixtureDef fixtureDef = new FixtureDef() {{
            shape = polyShape; density = 0.04f; friction = 0.5f; restitution = 0f; }};
        body.createFixture(fixtureDef);
        body.setTransform(x, y, 0);
        body.setLinearDamping(4);
        body.setUserData("zombie");
        velocity = new Vector2(10000,0);
        polyShape.dispose();
    }

    protected void attack() {

    }



    protected void move() {
        body.applyLinearImpulse(new Vector2(velocity.x, velocity.y), body.getPosition(), true);

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
        return (int) body.getPosition().x;
    }

    public int getPositionY(){
        return (int) body.getPosition().y;
    }

    public void render() {
        sprite.draw(spriteBatch);
    }
}
