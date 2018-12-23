package com.mygdx.zombies;

/**
 * Class for holding data used in collision handling and flag deletion
 *
 */
public class InfoContainer {
	
	// Body IDs for collision identification
	public static enum BodyID {
		ZOMBIE, PLAYER, LEVEL, PROJECTILE, PICKUP, WEAPON, NPC
	}
	
	private BodyID type;
	
	private Object obj;
	
	private boolean deletionFlagged;
	
	public InfoContainer(BodyID type, Object obj) {
		this.type = type;
		this.obj = obj;
		deletionFlagged = false;
	}

	public BodyID getType() {
		return type;
	}

	public Object getObj() {
		return obj;
	}
	
	public void flagForDeletion() {
		deletionFlagged = true;
	}

	public boolean isDeletionFlagged() {
		return deletionFlagged;
	}
}
