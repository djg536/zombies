package com.mygdx.zombies.states;

public class StateManager {
	
	private State currentState;
	
	public StateManager() {
<<<<<<< HEAD
		currentState = new Stage("teststage");
=======
		currentState = new MainMenu();//new Stage("stage1.txt"); 
>>>>>>> 7e53b9bda19cbf468f43638a62d52c7569649c9a
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
