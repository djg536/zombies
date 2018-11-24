package com.mygdx.zombies.states;

public class StateManager {
	
	private State currentState;
	
	public StateManager() {
		//currentState = new Stage("teststage");
		currentState = new MainMenu();//new Stage("stage1.txt"); 
	}
	
	public void loadState(State newState) {
		currentState.dispose();
		currentState = newState;
	}
	
	public void gameLoop() {
		if(currentState.update()) {
			//currentState = 
			loadState(new Stage("teststage"));
		}
	}
	
	public void render() {
		currentState.render();
	}
	
	public void dispose() {
		currentState.dispose();
	}
}
