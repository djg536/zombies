package com.mygdx.zombies.states;

public class StateManager {
	
	private State currentState;
	
	public StateManager() {
		currentState = new Level("teststage");
		//currentState = new MainMenu();
	}
	
	public void resize(int width, int height) {
		currentState.resize(width, height);
	}
	
	public void loadState(State newState) {
		currentState.dispose();
		currentState = newState;
	}
	
	public void gameLoop() {
		if(currentState.update()) {
			loadState(new Level("teststage"));
		}
	}
	
	public void render() {
		currentState.render();
	}
	
	public void dispose() {
		currentState.dispose();
	}
}
