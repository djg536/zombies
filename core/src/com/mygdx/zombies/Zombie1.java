package com.mygdx.zombies;

import com.mygdx.zombies.states.Level;

public class Zombie1 extends Enemy {
	
	public Zombie1(Level level, int x, int y) {
		super(level, x, y, "zombie/zombie1.png", 10, 1);
	}
}
