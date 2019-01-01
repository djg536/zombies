package com.mygdx.zombies.items;

public class PowerUp {
	
	private int speedBoost;
	private int healthBoost;
	private int stealthBoost;
	
	public PowerUp(int speedBoost, int healthBoost, int stealthBoost) {
		this.speedBoost = speedBoost+1;
		this.healthBoost = healthBoost;
		this.stealthBoost = stealthBoost;
	}

	public int getSpeedBoost() {
		return speedBoost;
	}
	
	public int getHealthBoost() {
		return healthBoost;
	}
	
	public int getStealthBoost() {
		return stealthBoost;
	}
}
