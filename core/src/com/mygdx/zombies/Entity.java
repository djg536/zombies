package com.mygdx.zombies;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Generic Entity class for handling safe flag deletion system
 */
public abstract class Entity {

	protected World box2dWorld;
	protected Body body;
	
	public void GenerateBodyRectangle(Vector2 dimens, World box2dWorld, InfoContainer.BodyID bodyID, FixtureDef fixtureDef) {
		this.box2dWorld = box2dWorld;
		body = box2dWorld.createBody(new BodyDef() {
			{
				type = BodyDef.BodyType.DynamicBody;
			}
		});
		final PolygonShape polyShape = new PolygonShape();
		polyShape.setAsBox(dimens.x, dimens.y);
		fixtureDef.shape = polyShape;
		body.createFixture(fixtureDef);
		body.setUserData(new InfoContainer(bodyID, this));
		polyShape.dispose();
	}
	
	public void GenerateBodyFromSprite(World box2dWorld, Sprite sprite, InfoContainer.BodyID bodyID, FixtureDef fixtureDef) {
		
		Vector2 dimens = new Vector2(sprite.getWidth() / 2 / Zombies.PhysicsDensity,
				sprite.getHeight() / 2 / Zombies.PhysicsDensity);
		GenerateBodyRectangle(dimens, box2dWorld, bodyID, fixtureDef);
	}
	
	public void dispose() {
		box2dWorld.destroyBody(body);
	};
	
	public InfoContainer getInfo() {
		return (InfoContainer)body.getUserData();
	}
	
	public static <T extends Entity> void removeDeletionFlagged(ArrayList<T> lst) {
		for(int i = 0; i< lst.size(); i++) {
			T entity = lst.get(i);
			if(entity.getInfo().isDeletionFlagged()) {
				entity.dispose();
				lst.remove(entity);
				i--;
			}
		}
	}
	
	public static <T extends Entity> void removeDeletionFlagged(T entity) {
			if(entity.getInfo().isDeletionFlagged()) {
				entity.dispose();
				entity = null;
			}	
	}
}
