package com.mygdx.zombies.states;

public class StateManager {

	private State currentState;
	
	public static enum StateID {
		MAINMENU, CREDITSMENU, OPTIONSMENU, ENDSCREEN, STAGE1
	}

	public StateManager() {
		currentState = new MainMenu(this);
	}

	public void resize(int width, int height) {
		currentState.resize(width, height);
	}

	public void loadState(State newState) {
		currentState.dispose();
		currentState = newState;
	}
	
	public void loadState(StateID stateID) {
		State tempState = null;
		switch(stateID) {
			case MAINMENU:
				tempState = new MainMenu(this);
				break;
			case CREDITSMENU:
				tempState = new CreditsMenu(this);
				break;
			case OPTIONSMENU:
				tempState = new OptionsMenu(this);
				break;
			case ENDSCREEN:
				tempState = new EndScreen(this);
				break;
			case STAGE1:
				tempState = new Level(this, "teststage");
				break;
			default:
				System.err.println("Error: Unrecognised gate destination");
				break;
		}
		
		if(tempState != null)
			loadState(tempState);
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
