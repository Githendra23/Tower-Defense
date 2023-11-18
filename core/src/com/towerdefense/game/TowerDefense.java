package com.towerdefense.game;


import com.badlogic.gdx.Game;
import com.towerdefense.game.screen.GameMenuScreen;

public class TowerDefense extends Game {
	@Override
	public void create() {
		setScreen(new GameMenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {

	}
}