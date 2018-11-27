package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.zombies.Zombie;

public class LevelContactListener implements ContactListener {

    boolean colliding = false;
    @Override
    public void beginContact(Contact contact) {

//      if(contact.getFixtureA().getBody().getUserData() == "body1" &&
//            contact.getFixtureB().getBody().getUserData() == "body2"
//        ((Zombie) contact.getFixtureB().getUserData()).reverseVelocity();
        System.out.println("Contact detected");
    }

    @Override
    public void endContact(Contact contact) {
        colliding = false;
        System.out.println("Contact removed");
    }

    @Override
    public void postSolve(Contact arg0, ContactImpulse arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void preSolve(Contact arg0, Manifold arg1) {
        // TODO Auto-generated method stub
    }

}
