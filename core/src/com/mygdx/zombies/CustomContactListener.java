package com.mygdx.zombies;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.zombies.items.PowerUp;
import com.mygdx.zombies.items.Projectile;
import com.mygdx.zombies.items.Weapon;

public class CustomContactListener implements ContactListener {
	
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
		else if (aType == InfoContainer.BodyID.PICKUP && bType == InfoContainer.BodyID.PLAYER) {
			PickUp powerUpPickUp = (PickUp)a.getObj();
			Player player = (Player)b.getObj();
			player.setPowerUp((PowerUp)powerUpPickUp.getContainedItem());
			powerUpPickUp.getInfo().flagForDeletion();
			System.out.println("Player has picked up item");
		}
		else if (aType == InfoContainer.BodyID.WEAPON && bType == InfoContainer.BodyID.PLAYER) {
			PickUp weaponPickUp = (PickUp)a.getObj();
			Player player = (Player)b.getObj();
			player.SetWeapon((Weapon)weaponPickUp.getContainedItem());
			weaponPickUp.getInfo().flagForDeletion();
			System.out.println("Player has picked up weapon");
		}
		else if (aType == InfoContainer.BodyID.ZOMBIE && bType == InfoContainer.BodyID.NPC) {
			NPC npc = (NPC)b.getObj();
			npc.setHealth(npc.getHealth()-1);
			System.out.println("NPC has contacted zombie");
		}
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}
