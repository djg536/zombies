package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {

    private String name;
    private int speed;
    private int health;
    private int points;
    private Sprite sprite;
    private int rotation;
    private int positionX;
    private int positionY;

    private SpriteBatch spriteBatch;

    public Player(SpriteBatch spriteBatch, int x, int y){
        this.spriteBatch = spriteBatch;
        sprite = new Sprite(new Texture(Gdx.files.internal("block.png")));
        this.positionX = x;
        this.positionY = y;
    }

    private void move(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            positionY += 10;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            positionY -= 10;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            positionX -= 10;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            positionX += 10;
        }

    }

    private void attack(){

    }

    public void update(){
        move();
    }

    public void render(){
        sprite.setPosition(positionX, positionY);
        sprite.draw(spriteBatch);
    }

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }


    public void setPowerUp(PowerUp powerUp){

    }
}
