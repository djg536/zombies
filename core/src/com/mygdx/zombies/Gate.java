package com.mygdx.zombies;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.zombies.states.StateManager.StateID;

public class Gate extends Entity {
	
	private StateID destination;
	private int entryID;
	
	public Gate(World world, Rectangle rect, StateID destination, int entryID) {
		this.destination = destination;
		this.entryID = entryID;
		
		FixtureDef fixtureDef = new FixtureDef() {
			{
				isSensor = true;
			}
		};
		
		GenerateBodyRectangle(new Vector2(rect.width / Zombies.PhysicsDensity, rect.height / Zombies.PhysicsDensity),
				world, InfoContainer.BodyID.GATE, fixtureDef);	
		body.setTransform(rect.x / Zombies.PhysicsDensity, rect.y / Zombies.PhysicsDensity, 0);
	}
	
	public StateID getDestination() {
		return destination;
	}
	
	public int getEntryID() {
		return entryID;
	}
}