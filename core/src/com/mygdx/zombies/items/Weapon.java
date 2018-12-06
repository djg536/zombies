package com.mygdx.zombies.items;

public interface Weapon {
	
	public abstract void use();
	
	public abstract void update(int x, int y, float rotation);
	
	public abstract void render();
}
