package com.mygdx.zombies.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class StateManager {

	private static State currentState;
	
	public static enum StateID {
		MAINMENU, CREDITSMENU, OPTIONSMENU, ENDSCREEN, BRIEFINGSCREEN,
		PLAYERSELECTMENU, STAGE1, STAGE2, STAGE3, TESTSTAGE1, TESTSTAGE2
	}

	public StateManager() {
		currentState = new MainMenu();
	}

	public void resize(int width, int height) {
		currentState.resize(width, height);
	}

	public static void loadState(State newState) {
		currentState.dispose();
		currentState = newState;
	}
	
	public static void loadState(StateID stateID) {
		loadState(stateID, -1);
	}
	
	public static void loadState(StateID stateID, int entryID) {
		State tempState = null;
		switch(stateID) {
			case MAINMENU:
				tempState = new MainMenu();
				break;
			case CREDITSMENU:
				tempState = new CreditsMenu();
				break;
			case OPTIONSMENU:
				tempState = new OptionsMenu();
				break;
			case ENDSCREEN:
				tempState = new EndScreen();
				break;
			case PLAYERSELECTMENU:
				tempState = new PlayerSelectMenu();
				break;
			case BRIEFINGSCREEN:
				tempState = new BriefingScreen();
				break;
			case TESTSTAGE1:
				tempState = new Level("teststage", entryID);
				break;
			case TESTSTAGE2:
				tempState = new Level("teststage2", entryID);
				break;
			case STAGE1:
				tempState = new Level("World_One", entryID);
				break;
			case STAGE2:
				tempState = new Level("World_Two", entryID);
				break;
			case STAGE3:
				tempState = new Level("World_Three", entryID);
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
		System.out.println("fps: " + Gdx.graphics.getFramesPerSecond());
	}

	public void dispose() {
		currentState.dispose();
	}
}
