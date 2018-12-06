package com.mygdx.zombies.items;

public class PowerUp {
	
	private int speedBoost;
	private int healthBoost;
	
	public PowerUp(int speedBoost, int healthBoost) {
		this.speedBoost = speedBoost+1;
		this.healthBoost = healthBoost;
	}

	public int getSpeedBoost() {
		return speedBoost;
	}
	
	public int getHealthBoost() {
		return healthBoost;
	}
}
