package com.towerdefense.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.towerdefense.game.screen.GameScreen;
import com.towerdefense.game.screen.GameOverScreen;
import com.towerdefense.game.screen.StartMenuScreen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.towerdefense.game.UI.*;
import com.towerdefense.game.enemy.AEnemy;
import com.towerdefense.game.enemy.Giant;
import com.towerdefense.game.enemy.Zombie;
import com.towerdefense.game.tower.ATower;
import com.towerdefense.game.tower.SniperTower;
import com.towerdefense.game.tower.projectiles.Bullet;
import com.towerdefense.game.tower.projectiles.Projectile;

public class TowerDefense extends Game {
	private boolean isPaused = false;
	private StartMenuScreen startMenuScreen;
	private GameScreen gameMenuScreen;
	private GameOverScreen gameOverScreen;
	private boolean isPlay = false;
	@Override
	public void create() {
		// mouse cursor
		Pixmap pixmapMouse = new Pixmap(Gdx.files.internal("UI/mouse.png"));
		int xHotspot = 15, yHotspot = 15;
		Cursor cursor = Gdx.graphics.newCursor(pixmapMouse, xHotspot, yHotspot);
		pixmapMouse.dispose();
		Gdx.graphics.setCursor(cursor);

		startMenuScreen = new StartMenuScreen(this);
		gameMenuScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);

		setScreen(startMenuScreen);
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
	public void startGame() {
		isPlay = true;
		setScreen(gameMenuScreen);
	}

	public void gameOver() {
		isPlay = false;
		setScreen(gameOverScreen);
	}

	public void closeGame() {
		this.dispose();
		Gdx.app.exit();
	}

	@Override
	public void dispose() {
		startMenuScreen.dispose();
		gameMenuScreen.dispose();
		gameOverScreen.dispose();
	}
}