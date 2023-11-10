package com.towerdefense.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.towerdefense.game.TowerDefense;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Tower Defense");
		config.setResizable(false);
		config.setWindowedMode(1600, 960);
		config.setWindowIcon("../assets/towerdefense_logo.png");
		new Lwjgl3Application(new TowerDefense(), config);
	}
}