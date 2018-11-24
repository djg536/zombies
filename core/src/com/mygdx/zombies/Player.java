package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.zombies.states.Level;

import java.lang.Math;

public class Player {

    private String name;
    private int speed;
    private int health;
    private float points = 18110;
    private Sprite sprite;
    private int rotation;
    
    private int mouseX;
    private int mouseY;
    private float directionX;
    private float directionY;
    private float normalX;
    private float normalY;
    private double angle;
    private float nuAngle;
    
    private float time;
    private int timer;
    private int last;
    private Body body;
    
    private SpriteBatch spriteBatch;
      
    public Player(Level level, int x, int y, int h){
    	
    	spriteBatch = level.spriteBatch;    	
    	sprite = new Sprite(new Texture(Gdx.files.internal("block.png")));
    	health = h;
    	
    	body = level.box2dWorld.createBody(level.mob);
    	final PolygonShape polyShape = new PolygonShape();
    	polyShape.setAsBox(sprite.getWidth()/(Level.tileSize*2), sprite.getHeight()/(Level.tileSize*2));
    	FixtureDef fixtureDef = new FixtureDef() {{shape = polyShape; density = 1f; }};
    	body.createFixture(fixtureDef);
    	body.setTransform(x/Level.tileSize, y/Level.tileSize, 0);
    	
    	polyShape.dispose();
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
    
    private void look() {
    	
    	mouseX = Gdx.input.getX();
    	mouseY =  -Gdx.input.getY() + Gdx.graphics.getHeight();	
    		
    	directionX = mouseX - getPositionX();
    	directionY = mouseY - getPositionY();
    	
    	normalX = 0;
    	
    	if(mouseY > getPositionY()) {
    		normalY = getPositionY()+10;
    	}
    	else if(mouseY < getPositionY()) {
    		normalY = getPositionY()-10;
    	}
    	
    	double dotProduct = normalX*directionX + normalY*directionY;
    	double sizeA = (Math.sqrt(normalX*normalX + normalY*normalY));
    	double sizeB = (Math.sqrt(directionX*directionX + directionY*directionY));
    	
    	angle = Math.acos(dotProduct/(sizeA*sizeB));
    	  	
    	angle = Math.toDegrees(angle);
    	
    	if(mouseX > getPositionX()) {
    		angle = -angle;
    	}
    	   	
    	nuAngle = (float)angle;
    	sprite.setRotation(nuAngle);
    	
    	//System.out.println(positionX + ", " + positionY + " : " + mouseX + ", " + mouseY + " : " + nuAngle);
    }
    
    private void move(){
           
       if(Gdx.input.isKeyPressed(Keys.W)) {    	
        	//positionY+=10;  	
    	    body.setLinearVelocity(0f, 10f);
        } 
        
    	if(Gdx.input.isKeyPressed(Keys.S)) { 	
    		//positionY-=10;
    		body.setLinearVelocity(0f, -10f);
    	} 
    	
    	if(Gdx.input.isKeyPressed(Keys.A)) {       	
        	//positionX-=10;  
        	body.setLinearVelocity(-10f, 0f);
        } 
    	
    	if(Gdx.input.isKeyPressed(Keys.D)) {        	
        	//positionX+=10;	
    		body.setLinearVelocity(10f, 0f);
        } 
    }

    private void attack(){

    }

    public boolean update(){
    	//System.out.println(body.getPosition());
    	move();
    	look();
    	sprite.setPosition(getPositionX(), getPositionY());
    	sprite.setRotation(nuAngle);
    	points();
    	return health();
    }

    public void render(){
    	  	  	
    	sprite.draw(spriteBatch);	
    }

    public int getPositionX(){
        return (int) (body.getPosition().x * Level.tileSize);
    }

    public int getPositionY(){
        return (int) (body.getPosition().y * Level.tileSize);
    }

    public void setPowerUp(PowerUp powerUp){

    }
}
