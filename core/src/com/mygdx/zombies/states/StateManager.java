package com.mygdx.zombies.states;

public class StateManager {
	
	private State currentState;
	
	public StateManager() {
		//currentState = new Level("teststage");
		currentState = new MainMenu();
		//currentState = new CreditsMenu();
	}
	
	public void resize(int width, int height) {
		currentState.resize(width, height);
	}
	
	public void loadState(State newState) {
		currentState.dispose();
		currentState = newState;
	}
	
	public void gameLoop() {
		if(currentState.update() == 1) {
			loadState(new Level("teststage"));
		}
		if(currentState.update() == 2) {
			loadState(new CreditsMenu());
		}
		if(currentState.update() == 3) {
			loadState(new MainMenu());
		}
	}
	
	public void render() {
		currentState.render();
	}
	
	public void dispose() {
		currentState.dispose();

	}
}
