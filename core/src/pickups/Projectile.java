package pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.zombies.Zombies;
import com.mygdx.zombies.states.Level;

public class Projectile {
	
	private SpriteBatch spriteBatch;
	private Sprite sprite;
	private Body body;
	
	public Projectile(Level level, int x, int y, double angle) {
		
		spriteBatch = level.worldBatch;
		sprite = new Sprite(new Texture(Gdx.files.internal("bullet.png")));
		
		body = level.box2dWorld.createBody(level.mob);
		final PolygonShape polyShape = new PolygonShape();
    	polyShape.setAsBox(sprite.getWidth()/2/Zombies.PhysicsDensity, sprite.getHeight()/2/Zombies.PhysicsDensity);
    	//Use the maskBits parameter here to set collision mask bits
    	FixtureDef fixtureDef = new FixtureDef() {{
    		shape = polyShape; density = 200f; friction = 0; restitution = 1f; filter.maskBits=1; }};
    	body.createFixture(fixtureDef);
    	
    	body.setTransform(x/Zombies.PhysicsDensity, y/Zombies.PhysicsDensity, 0);
    	polyShape.dispose();
    	body.setBullet(true);
    	
    	final float speed = 10;
    	body.applyLinearImpulse((float)Math.cos(angle)*speed, (float)Math.sin(angle)*speed,
    			x/Zombies.PhysicsDensity, y/Zombies.PhysicsDensity, true);
	}
	
	public void render() {
		sprite.setPosition(body.getPosition().x*Zombies.PhysicsDensity, body.getPosition().y*Zombies.PhysicsDensity);
		sprite.draw(spriteBatch);
	}
	
	public void dispose() {

	}
}
