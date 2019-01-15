package com.mygdx.zombies.states;

public class StateManager {

	private State currentState;
	
	public static enum StateID {
		MAINMENU, CREDITSMENU, OPTIONSMENU, ENDSCREEN, PLAYERSELECTMENU, STAGE1, STAGE2, STAGE3, TESTSTAGE1, TESTSTAGE2
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
	
	public void loadState(StateID stateID, String playerPath) {
		loadState(stateID, -1, playerPath);
	}
	
	public void loadState(StateID stateID, int entryID, String playerPath) {
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
			case PLAYERSELECTMENU:
				tempState = new PlayerSelectMenu(this);
				break;
			case TESTSTAGE1:
				tempState = new Level(this, "teststage", entryID, playerPath);
				break;
			case TESTSTAGE2:
				tempState = new Level(this, "teststage2", entryID, playerPath);
				break;
			case STAGE1:
				tempState = new Level(this, "World_One", entryID, playerPath);
				break;
			case STAGE2:
				tempState = new Level(this, "World_Two", entryID, playerPath);
				break;
			case STAGE3:
				tempState = new Level(this, "World_Three", entryID, playerPath);
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
