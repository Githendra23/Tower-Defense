package com.towerdefense.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sun.jvm.hotspot.gc.shared.Space;

import javax.swing.*;

public class TowerDefense extends ApplicationAdapter {
	private int coins = 0;
	private int frameCount;
	private SpriteBatch batch;
	private BitmapFont font;
	private Zombie zombie;
	private Giant giant;
	private Castle castle;

	private boolean isPaused = false;
	private Texture menuPause;

	private void pauseMenu() {
		float centerX = Gdx.graphics.getWidth() / 2f - (menuPause.getWidth() + 300) / 2f;
		float centerY = Gdx.graphics.getHeight() / 2f - (menuPause.getHeight() + 200) / 2f;

		batch.draw(new TextureRegion(menuPause), centerX, centerY, 730, 370);
	}

	@Override
	public void create () {

		batch = new SpriteBatch();
		castle = new Castle(2000);
		zombie = new Zombie();
		giant = new Giant();
		menuPause = new Texture("menu.png");

		Pixmap pixmap = new Pixmap(Gdx.files.internal("mouse.png")); // Make sure the path is correct
		int xHotspot = 15, yHotspot = 15;
		Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
		pixmap.dispose(); // We don't need the pixmap anymore
		Gdx.graphics.setCursor(cursor);

		font = new BitmapFont();
		font.setColor(1, 1, 1, 1); // Set the font color
	}

	float X = 200, Y = 200;
	@Override
	public void render () {
		frameCount++;

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			isPaused = !isPaused;
		}

		ScreenUtils.clear(0, 1, 0, 1);
		batch.begin();
		// display FPS
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);

		batch.draw(zombie.getImg(), 100, 100);
		batch.draw(giant.getImg(), X, Y);
		batch.draw(castle.getImg(), Gdx.graphics.getWidth() - 200, ((float) Gdx.graphics.getHeight() / 2) - 150);

		if (!isPaused) {
			// all movements should be inside this condition
			if (frameCount >= 24 ) {
				X += giant.getSpeed() + 3;
				frameCount = 0;
			}

		}
		else {
			pauseMenu();
		}

		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}