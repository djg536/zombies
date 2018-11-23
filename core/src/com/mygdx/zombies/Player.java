package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.zombies.states.Stage;
import com.mygdx.zombies.states.State;
import com.mygdx.zombies.states.StateManager;

import java.lang.Math;

public class Player {

    private String name;
    private int speed;
    private int health;
    private float points = 18110;
    private Sprite sprite;
    private int rotation;
    private int positionX;
    private int positionY;
    
    private int mouseX;
    private int mouseY;
    private int directionX;
    private int directionY;
    private int normalX;
    private int normalY;
    private double angle;
    private float nuAngle;
    
    private float time;
    private int timer;
    private int last;
    
    private SpriteBatch spriteBatch;
      
    public Player(SpriteBatch spriteBatch, int x, int y, int h){
    	
    	this.spriteBatch = spriteBatch;
    	sprite = new Sprite(new Texture(Gdx.files.internal("block.png")));
    	positionX = x;
    	positionY = y;
    	health = h;
    }
    
    private void points() {
    	 
    	time += Gdx.graphics.getDeltaTime();
    	timer = Math.round(time);
    	   			
    	if(timer % 2 == 0 && timer != last) {	
    		points -= Math.round(Math.random()*10);
    		last = timer;
    	}
    	
    	//System.out.println(points);  		
    }
    
    public boolean health() {
    	
    	if(Gdx.input.isKeyPressed(Keys.SPACE)) {
    		health -= 1;	
    		
    		System.out.println(health);
    	} 	
    	if(health == 0) {
			System.out.println("RESTART");
			return true;
		} 	
    	else {
    		return false;
    	}
    }
    
    private void move(){
    
    	mouseX = Gdx.input.getX();
    	mouseY =  -(Gdx.input.getY() - Gdx.graphics.getHeight());	
    		
    	directionX = mouseX - positionX;
    	directionY = mouseY - positionY;
    	
    	normalX = 0;
    	
    	if(mouseY > positionY) {
    		normalY = positionY+10;
    	}
    	else if(mouseY < positionY) {
    		normalY = positionY-10;
    	}
    	
    	double dotproduct = ((normalX*directionX)+(normalY*directionY));
    	double sizeA = (Math.sqrt((normalX*normalX)+(normalY*normalY)));
    	double sizeB = (Math.sqrt((directionX*directionX)+(directionY*directionY)));
    	
    	angle = Math.acos((dotproduct)/((sizeA)*(sizeB)));
    	  	
    	angle = Math.toDegrees(angle);
    	
    	if(mouseX > positionX) {
    		angle = -angle;
    	}
    	
    	nuAngle = (float)angle;
    	  	
    	if(Gdx.input.isKeyPressed(Keys.W)) {
        	
        	positionY+=10;  	
        } 
        
    	if(Gdx.input.isKeyPressed(Keys.S)) {
    	
    		positionY-=10;
    	} 
    	
    	if(Gdx.input.isKeyPressed(Keys.A)) {
        	
        	positionX-=10;  	
        } 
    	
    	if(Gdx.input.isKeyPressed(Keys.D)) {
        	
        	positionX+=10;	
        } 
    
    }

    private void attack(){

    }

    public boolean update(){
    	
    	move();
    	points();
    	if(health() == true) {
    		return true;
    	}
    	else return false;
    }

    public void render(){
    	  	
    	sprite.setPosition(positionX, positionY);	
    	sprite.setRotation(nuAngle);
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
