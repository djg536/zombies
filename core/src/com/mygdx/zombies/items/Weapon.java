package com.mygdx.zombies.items;

/**
 * Abstract weapon interface for both ranged and melee weapons
 */
public interface Weapon {
	
	public abstract void use();
	
	/**
	 * Method to update weapon transformation
	 * @param x - the x position to move to
	 * @param y - the y position to move to
	 * @param rotation - the rotation to set
	 */
	public abstract void update(int x, int y, float rotation);
	
	/**
	 * Draw the weapon to the screen
	 */
	public abstract void render();
	
	/**
	 * Remove weapon clearing up memory
	 */
	public abstract void dispose();
}
