package com.mygdx.zombies;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.zombies.pickups.Projectile;
import com.mygdx.zombies.states.LevelContactListener;

public class CustomContactListener extends LevelContactListener {
	
	public CustomContactListener() {
		super();
	}
	
	@Override
	public void beginContact(Contact contact) {
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
		InfoContainer a = (InfoContainer)bodyA.getUserData();
		InfoContainer b = (InfoContainer)bodyB.getUserData();
			
		//If if a is null, swap.
		//There should never be a situation where a and b are null,
		//since only walls are null and they do not collide with each other.
		if(a == null)
		{
			a = b;
			b = null;
		}
		
		InfoContainer.BodyID aType = a.getType();
		InfoContainer.BodyID bType = b != null ? b.getType() : null;

		if (aType == InfoContainer.BodyID.ZOMBIE && bType == null) {
			Zombie zombie = (Zombie)a.getObj();
			zombie.reverseVelocity();
			System.out.println("Collision between zombie and wall");
		}
		else if (aType == InfoContainer.BodyID.ZOMBIE && bType == InfoContainer.BodyID.PROJECTILE) {
			Zombie zombie = (Zombie)a.getObj();
			zombie.setHealth(zombie.getHealth()-1);
			System.out.println("Zombie has been damaged");
		}
		else if (aType == InfoContainer.BodyID.PLAYER && bType == InfoContainer.BodyID.ZOMBIE) {
			Player player = (Player)a.getObj();
			if (player.isSwinging()) {
				player.setHealth(player.getHealth()-1);
				Zombie zombie = (Zombie)b.getObj();
				zombie.setHealth(zombie.getHealth()-1);
			}
			else
				player.setHealth(player.getHealth()-2);			
			System.out.println("Player has contacted zombie");
		}
		else if (aType == InfoContainer.BodyID.PROJECTILE && bType == null) {
			Projectile projectile = (Projectile)a.getObj();
			projectile.getInfo().flagForDeletion();
			System.out.println("Bullet has hit wall");
		}
	}
}
