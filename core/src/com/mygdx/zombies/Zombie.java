package com.mygdx.zombies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class Zombie extends Sprite {
    private int speed;
    private int strength;
    private int health;
    private Sprite sprite;
    private int rotation;
    private int positionX;
    private int positionY;

    private SpriteBatch spriteBatch;

    public World world;
    public Body b2body;


    public Zombie(World world) {
//        this.spriteBatch = spriteBatch;
//        sprite = new Sprite(new Texture(Gdx.files.internal("zombie/zombie.png")));
//        this.positionX = x;
//        this.positionY = y;
        this.world = world;
        defineZombie();
    }

    public void defineZombie() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(200,200);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    protected void attack() {

    }

    protected void move() {
        positionX += 5;
    }

    public void update() {
        world.step(1/60f, 6,2);
        //move();
    }

    public void render() {
        sprite.setPosition(positionX, positionY);
        sprite.draw(spriteBatch);
    }

    public void dispose() {
        spriteBatch.dispose();
    }
}
