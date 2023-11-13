package com.towerdefense.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.towerdefense.game.UI.*;
import com.towerdefense.game.enemy.AEnemy;
import com.towerdefense.game.enemy.Giant;
import com.towerdefense.game.enemy.Zombie;
import com.towerdefense.game.tower.ATower;
import com.towerdefense.game.tower.ArcherTower;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TowerDefense extends ApplicationAdapter {
	private int coins = 0;
	private double frameCount = 0;
	private SpriteBatch batch;
	private BitmapFont font;
	private AEnemy zombie, giant;
	private Castle castle;
	private ATower archerTower;
	private List<ATower> towerList;

	private boolean isPaused = false;
	private Texture menuPause;
	private PauseMenu pausemenu;
	private Button closeButton;
	private TowerButton towerButton;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private MapObjects objects;
	private int tileHeight, tileWidth, layerHeight, layerWidth;

	private final int RIGHT = 1, LEFT = -1, UP = 1, DOWN = -1, STAY = 0;
	private List<Polyline> blockedZones = new ArrayList<>();
	private boolean isTowerPlaceable = false;

	int X = 200, Y = 200;
	@Override
	public void create() {
		batch = new SpriteBatch();

		// list
		towerList = new ArrayList<>();

		// map
		map = new TmxMapLoader().load("map/map.tmx");

		objects = map.getLayers().get("nonTowerZone").getObjects();

		tileWidth = map.getProperties().get("tilewidth", Integer.class);
		tileHeight = map.getProperties().get("tileheight", Integer.class);

		// For Layer
		layerWidth = map.getProperties().get("width", Integer.class);
		layerHeight = map.getProperties().get("height", Integer.class);

		mapRenderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); // Center the camera
		camera.update();

		// items, mobs etc...
		castle = new Castle(2000, 1400, 350);
		zombie = new Zombie(100, 100);
		giant = new Giant(X, Y);
		pausemenu = new PauseMenu();
		closeButton = new CloseButton(500, 500);
		towerButton = new TowerButton(1000, 200);
		archerTower = new ArcherTower(1200, 200);

		// mouse cursor
		Pixmap pixmapMouse = new Pixmap(Gdx.files.internal("mouse.png")); // Make sure the path is correct
		int xHotspot = 15, yHotspot = 15;
		Cursor cursor = Gdx.graphics.newCursor(pixmapMouse, xHotspot, yHotspot);
		pixmapMouse.dispose();
		Gdx.graphics.setCursor(cursor);

		font = new BitmapFont();
		font.setColor(1, 1, 1, 1); // Set the font color
	}

	@Override
	public void render() {
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		frameCount++;

		// Render the map
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mapRenderer.setView(camera);
		mapRenderer.render();

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			isPaused = !isPaused;
		}

		// System.out.println(giant.hitbox().overlaps(castle.hitbox()));
		// System.out.println(archerTower.isInRange(giant));

		castle.displayHitbox();
		giant.displayHitbox();

		for (ATower tower : towerList) {
			tower.displayHitbox();
			tower.displayRangeHitbox();
		}

		batch.begin();
		// display FPS
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);

		// display Coordinates of the mouse cursor
		font.draw(batch, "Mouse coords: " + mouseX + "X, " + mouseY + "Y", 10, Gdx.graphics.getHeight() - 30);

		isTowerPlaceable = canPlaceTower(mouseX - (towerButton.getTexture().getRegionWidth() / 2), mouseY + towerButton.getTexture().getRegionHeight()) && canPlaceTower(mouseX - (towerButton.getTexture().getRegionWidth() / 2), mouseY);
		System.out.println(isTowerPlaceable);

		// display mobs
		batch.draw(castle.getImg(), castle.getAxisX(), castle.getAxisY());
		batch.draw(zombie.getImg(), zombie.getAxisX(), zombie.getAxisY());
		batch.draw(giant.getImg(), giant.getAxisX(), giant.getAxisY());
		batch.draw(archerTower.getImg(), archerTower.getAxisX(), archerTower.getAxisY());

		batch.draw(towerButton.getTexture(), towerButton.getAxisX(), towerButton.getAxisY());

		// pause condition
		if (!isPaused) {
			// all movements should be inside this condition
			if (frameCount % 24 == 0) {
				giant.move(RIGHT, STAY);
				// frameCount = BigInteger.ZERO;
			}

			if (towerButton.getIsSetPressed()) {
				batch.draw(towerButton.getSelectedImg(), mouseX - (towerButton.getTexture().getRegionWidth() / 2f), mouseY);
				towerButton.displayHitbox(mouseX - (towerButton.getSelectedImg().getRegionWidth() / 2), mouseY, batch);

				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

					int count = 0;
					for (ATower tower : towerList) {
						if (!(towerButton.isOverlaping(tower))) {
							count++;
						}
					}

					if (count == towerList.size()) {
						if (isTowerPlaceable) {
							towerList.add(new ArcherTower((int) (mouseX - (towerButton.getTexture().getRegionWidth() / 2f)), mouseY));
						}
					}
				}
			}

			if (towerButton.isClicked(mouseX, mouseY)) {
				towerButton.setPressed(true);
			}

			if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
				towerButton.setPressed(false);
			}
		}
		else {
			float centerX = Gdx.graphics.getWidth() / 2f - (pausemenu.getAxisX()) / 2f;
			float centerY = Gdx.graphics.getHeight() / 2f - (pausemenu.getAxisY()) / 2f;

			batch.draw(pausemenu.getImg(),centerX, centerY, 730, 370);
			batch.draw(closeButton.getTexture(), 500, 500);

			if (closeButton.isClicked(mouseX, mouseY)) {
				System.out.println("done");
			}
		}

		for (ATower tower : towerList) {
			batch.draw(tower.getImg(), tower.getAxisX(), tower.getAxisY());
		}

		batch.end();
	}

	private boolean isMouseInsideObject(float mouseX, float mouseY) {
		boolean isInside = false;

		for (MapObject object : objects) {
			if (object instanceof RectangleMapObject) {
				Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
				if (rectangle.contains(mouseX, mouseY)) {
					isInside = true;
					break;  // No need to check other objects if the mouse is inside one
				}
			} else if (object instanceof PolygonMapObject) {
				Polygon polygon = ((PolygonMapObject) object).getPolygon();
				float[] vertices = polygon.getTransformedVertices();
				if (polygonContains(vertices, mouseX, mouseY)) {
					isInside = true;
					break;  // No need to check other objects if the mouse is inside one
				}
			}
			// Handle other object types if needed
		}

		return isInside;
	}

	private boolean canPlaceTower(float mouseX, float mouseY) {
		return !isMouseInsideObject(mouseX, mouseY);
	}

	private boolean polygonContains(float[] vertices, float x, float y) {
		int intersects = 0;
		int numVertices = vertices.length / 2;

		for (int i = 0, j = numVertices - 1; i < numVertices; j = i++) {
			float xi = vertices[i * 2];
			float yi = vertices[i * 2 + 1];
			float xj = vertices[j * 2];
			float yj = vertices[j * 2 + 1];

			boolean intersect = ((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
			if (intersect) {
				intersects++;
			}
		}

		// If the number of intersects is odd, the point is inside the polygon
		return intersects % 2 == 1;
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		map.dispose();
		mapRenderer.dispose();
	}
}