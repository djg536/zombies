package com.mygdx.zombies;

import com.mygdx.zombies.states.Level;

public class Zombie extends Enemy {
	
	public Zombie(Level level, int x, int y, int h) {
		super(level, x, y, h, "zombie/zombie.png");
	}

}
