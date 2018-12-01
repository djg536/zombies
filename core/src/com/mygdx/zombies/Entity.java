package com.mygdx.zombies;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Generic Entity class for handling safe flag deletion system
 *
 */
public abstract class Entity {

	protected World box2dWorld;
	protected Body body;
	
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
