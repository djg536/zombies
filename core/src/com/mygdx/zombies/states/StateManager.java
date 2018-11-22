package com.mygdx.zombies.states;

public class StateManager {
	
	private State currentState;
	
	public StateManager() {
		currentState = new Level("teststage");
		//currentState = new MainMenu();//new Level("stage1.txt");
	}
	
	public void loadState(State newState) {
		currentState.dispose();
		currentState = newState;
	}
	
	public void gameLoop() {
		currentState.update();
	}
	
	public void render() {
		currentState.render();
	}
	
	public void dispose() {
		currentState.dispose();
	}
}
