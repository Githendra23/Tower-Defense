package com.towerdefense.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.towerdefense.game.screen.GameMenuScreen;

public class TowerDefense extends Game {
	private boolean isPaused = false;
	@Override
	public void create() {
		setScreen(new GameMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			isPaused = !isPaused;
		}

		if (isPaused) {
			super.pause();
		}
		else {
			super.resume();
		}
	}

	public void resumeGame() {
		isPaused = false;
	}

	@Override
	public void dispose() {

	}
}