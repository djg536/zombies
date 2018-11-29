package com.mygdx.zombies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.zombies.states.Level;

import java.lang.Math;

public class Player {

    private String name;
    private int speed;
    private int health;
    private float points = 18110;
    private String pointDisplay = "18110.0";
    private Sprite sprite;
    private int rotation;
    
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
    
    private Sprite hud;
    private SpriteBatch spriteBatch;
    private SpriteBatch UIBatch;   
    
    public Player(Level level, int x, int y, int h) {
    	
    	spriteBatch = level.worldBatch;    	
    	UIBatch = level.UIBatch;
    	
    	sprite = new Sprite(new Texture(Gdx.files.internal("block.png")));
    	hud = new Sprite(new Texture(Gdx.files.internal("block.png")));
    	health = h;
    	
    	body = level.box2dWorld.createBody(level.mob);
    	final PolygonShape polyShape = new PolygonShape();
    	polyShape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);
    	FixtureDef fixtureDef = new FixtureDef() {{
    		shape = polyShape; density = 0.04f; friction = 0.5f; restitution = 0f; }};
    	body.createFixture(fixtureDef);
    	body.setTransform(x, y, 0);
    	body.setLinearDamping(4);
    	body.setFixedRotation(true);
    	polyShape.dispose();

    	body.setLinearDamping(4);
    }
    
    private void points() {
    	 
    	time += Gdx.graphics.getDeltaTime();
    	timer = Math.round(time);
    	   			
    	if(timer % 2 == 0 && timer != last) {	
    		points -= Math.round(Math.random()*10);
    		pointDisplay = Float.toString(points);
    		
    		last = timer;
    	}		
    }
    
    public int health() {
    	
    	if(Gdx.input.isKeyPressed(Keys.SPACE)) {
    		health -= 1;		
    		System.out.println(health);
    		
    		if(health <= 0) {
    			System.out.println("RESTART");
    			return 1;
    		} 
    	} 	
    	
    	return 0;
    }
    
    private void look(Vector3 mouseCoords) {
    	
    	int mouseX = (int) mouseCoords.x;
    	int mouseY = (int) mouseCoords.y;	
    		
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
    
    private void move() {
           
       if(Gdx.input.isKeyPressed(Keys.W)) {    	
    	    body.applyLinearImpulse(new Vector2(0, 10000), body.getPosition(), true);
        } 
        
    	if(Gdx.input.isKeyPressed(Keys.S)) { 	
    		body.applyLinearImpulse(new Vector2(0, -10000), body.getPosition(), true);
    	} 
    	
    	if(Gdx.input.isKeyPressed(Keys.A)) {       	
    		body.applyLinearImpulse(new Vector2(-10000, 0), body.getPosition(), true);
        } 
    	
    	if(Gdx.input.isKeyPressed(Keys.D)) {        	
    		body.applyLinearImpulse(new Vector2(10000, 0), body.getPosition(), true);
        } 
    }

    private void attack() {

    }

    public int update(Vector3 mouseCoords) {
    	move();
    	look(mouseCoords);
    	sprite.setPosition(getPositionX()-sprite.getWidth()/2, getPositionY()-sprite.getHeight()/2);
    	sprite.setRotation(nuAngle);
    	points();
    	return health();
    }

    public void render() {
    	  	  	
    	sprite.draw(spriteBatch);	
    }
    
    public void hudRender() {
    	
    	Zombies.pointsFont.draw(UIBatch, pointDisplay, 100, 590);
    	
    	for(int i = 0; i < health; i++)
    	{
    		hud.setPosition(100 + i*50, 620);
    		hud.draw(UIBatch);
    	}	
    }

    public int getPositionX() {
        return (int) body.getPosition().x;
    }

    public int getPositionY() {
        return (int) body.getPosition().y;
    }

    public void setPowerUp(PowerUp powerUp){
    }

    public void dispose() {
        spriteBatch.dispose();
        UIBatch.dispose();
    }
}
