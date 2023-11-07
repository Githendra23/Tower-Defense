package com.towerdefense.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.*;

public class TowerDefense extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private BitmapFont font;
	private Zombie zombie;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		zombie = new Zombie();

		Pixmap pixmap = new Pixmap(Gdx.files.internal("mouse.png")); // Make sure the path is correct
		int xHotspot = 15, yHotspot = 15;
		Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
		pixmap.dispose(); // We don't need the pixmap anymore
		Gdx.graphics.setCursor(cursor);

		font = new BitmapFont();
		font.setColor(1, 1, 1, 1); // Set the font color
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		// display FPS
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);
		batch.draw(img, Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));

		batch.draw(zombie.drawZombie(), 100, 100);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}