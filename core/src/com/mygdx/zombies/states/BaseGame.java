package com.mygdx.zombies.states;

import com.badlogic.gdx.Game;

public class BaseGame extends Game {

    public void create() {
        GameScreen game = new GameScreen(this);
        setScreen(game);
    }
}
