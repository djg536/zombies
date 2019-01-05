package com.mygdx.zombies;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Gate extends Entity {
	
	private String destination;
	
	public Gate(World world, Rectangle rect, String destination) {
		this.destination = destination;
		
		FixtureDef fixtureDef = new FixtureDef() {
			{
				isSensor = true;
			}
		};
		
		GenerateBodyRectangle(new Vector2(rect.width / Zombies.PhysicsDensity, rect.height / Zombies.PhysicsDensity),
				world, InfoContainer.BodyID.GATE, fixtureDef);	
		body.setTransform(rect.x / Zombies.PhysicsDensity, rect.y / Zombies.PhysicsDensity, 0);
	}
	
	public String getDestination() {
		return destination;
	}
}
