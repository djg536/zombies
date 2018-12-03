package com.mygdx.zombies.pickups;

import com.mygdx.zombies.Entity;

public class PickUp extends Entity {

	private boolean isWeapon;
	private boolean pickedUp;
	
	public PickUp(boolean isWeapon) {
		this.isWeapon = isWeapon;
		pickedUp = false;	
	}
	
	public void Collect() {
		pickedUp = true;
	}
}
