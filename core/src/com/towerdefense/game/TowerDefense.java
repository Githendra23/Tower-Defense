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
import com.towerdefense.game.tower.ArcherTower;
import com.towerdefense.game.tower.Cannon;
import com.towerdefense.game.tower.projectiles.Bullet;
import com.towerdefense.game.tower.projectiles.HomingRocket;
import com.towerdefense.game.tower.projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;

public class TowerDefense extends ApplicationAdapter {
	private int coins = 0;
	private double frameCount = 0;
	private SpriteBatch batch;
	private BitmapFont font;
	private Bullet bullet;
	private Texture img;
	private final Array<Projectile> projectileArray = new Array<Projectile>();
	private final Array<ATower> towers = new Array<ATower>();
	private final Array<Float> towerCoordX = new Array<Float>();
	private final Array<Float> towerCoordY = new Array<Float>();
	private final Array<Integer> towerCooldown = new Array<Integer>();
	private final Array<AEnemy> enemies = new Array<AEnemy>();
	private List<TowerButton> towerButtonList;

	private AEnemy zombie, giant;
	private Castle castle;
	private ATower archerTower;
	private List<ATower> towerList;
	private List<AEnemy> enemyList;
	private boolean isPaused = false;
	private Texture menuPause;
	private PauseMenu pausemenu;
	private Button closeButton;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private MapObjects noTowerZoneObject, enemyPathObject;
	private boolean isTowerPlaceable = false;
	private RectangleMapObject randomRectangleObject;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("turret.png");
		// missile= new HomingRocket(5,5);
		bullet = new Bullet(5, 5);
		castle = new Castle(2000, 1400, 350);
		menuPause = new Texture("menu.png");

		// list
		towerList = new ArrayList<>();
		enemyList = new ArrayList<>();
		towerButtonList = new ArrayList<>();

		towerButtonList.add(new RocketTurretButton(1480, 20));

		// map
		map = new TmxMapLoader().load("map/map.tmx");

		noTowerZoneObject = map.getLayers().get("nonTowerZone").getObjects();
		randomRectangleObject = map.getLayers().get("startPoint").getObjects().getByType(RectangleMapObject.class).first();
		enemyPathObject = map.getLayers().get("enemyPathLayer").getObjects();

		// For Layer
		mapRenderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); // Center the camera
		camera.update();

		// items, mobs etc...
		castle = new Castle(2000, 1400, 350);
		zombie = new Zombie(100, 100, enemyPath());
		giant = new Giant(X, Y, enemyPath());
		pausemenu = new PauseMenu();
		closeButton = new CloseButton(500, 500);

		// mouse cursor
		Pixmap pixmapMouse = new Pixmap(Gdx.files.internal("mouse.png")); // Make sure the path is correct
		int xHotspot = 15, yHotspot = 15;
		Cursor cursor = Gdx.graphics.newCursor(pixmapMouse, xHotspot, yHotspot);
		pixmapMouse.dispose();
		Gdx.graphics.setCursor(cursor);

		font = new BitmapFont();
		font.setColor(1, 1, 1, 1); // Set the font color (white in this example)
	}

	int count = 1;

	int X = 200, Y = 200;

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

		for (ATower tower : towers) {
			tower.displayHitbox();
			tower.displayRangeHitbox();
		}
		for (AEnemy enemy : enemyList) {
			enemy.displayHitbox();
		}
		for (Projectile projectile : projectileArray) {
			projectile.displayHitbox();
		}

		// Render the map
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			isPaused = !isPaused;
		}

		castle.displayHitbox();

		for (ATower tower : towerList) {
			tower.displayHitbox();
			tower.displayRangeHitbox();
		}

		for (AEnemy enemy : enemyList) {
			if (enemyList.size() > 0) {
				enemy.displayHitbox();
			}
		}

		batch.begin();

		bullet.shootAt(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)), 20);
		// System.out.println(Gdx.input.isKeyPressed(Input.Keys.A));

		/*if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			spawnBullet(10, 10, Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
		}*/

		// display FPS
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);

		// display Coordinates of the mouse cursor
		font.draw(batch, "Mouse coords: " + mouseX + "X, " + mouseY + "Y", 10, Gdx.graphics.getHeight() - 30);

		// display mobs
		batch.draw(castle.getImg(), castle.getAxisX(), castle.getAxisY());
		batch.draw(zombie.getImg(), zombie.getAxisX(), zombie.getAxisY());

		for (AEnemy enemy : enemyList) {
			if (enemyList.size() > 0) {
				batch.draw(enemy.getImg(), enemy.getAxisX(), enemy.getAxisY());
			}
		}

		for (ATower tower : towerList) {
			batch.draw(tower.getImg(), tower.getAxisX(), tower.getAxisY());
		}

		for (TowerButton tower : towerButtonList) {
			batch.draw(tower.getTexture(), tower.getAxisX(), tower.getAxisY());
		}

		// pause condition
		if (!isPaused) {
			// all movements should be inside this condition
			if (frameCount % 1 == 0) {

				if (frameCount % 120 == 0) {
					if (true) {
						spawnNewEnemy("Zombie");
					}
					count++;
				}

				if (enemyList.size() > 0) {
					for (AEnemy enemy : enemyList) {
						enemy.attack(castle);
						enemy.move();
					}
				}
			}

			// remove enemy when hp == 0
			for (int i = 0; i < enemyList.size(); i++) {
				AEnemy enemy = enemyList.get(i);

				if (enemy.isDead()) {
					enemyList.remove(i);
				}
			}

			for (TowerButton towerButton : towerButtonList) {
				isTowerPlaceable = canPlaceTower(mouseX - (towerButton.getTexture().getRegionWidth() / 2), mouseY + towerButton.getTexture().getRegionHeight()) && canPlaceTower(mouseX - (towerButton.getTexture().getRegionWidth() / 2), mouseY);

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
								towerList.add(towerButton.getATower((int) (mouseX - (towerButton.getTexture().getRegionWidth() / 2f)), mouseY));
							}
						}
					}
				}

				if (towerButton.getIsSetPressed() == true && Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
					towerButton.setPressed(false);
				}

				if (towerButton.isClicked(mouseX, mouseY)) {
					towerButton.setPressed(true);
				}
			}

			towers();
			//projectiles();

		} else {
			float centerX = Gdx.graphics.getWidth() / 2f - (pausemenu.getAxisX()) / 2f;
			float centerY = Gdx.graphics.getHeight() / 2f - (pausemenu.getAxisY()) / 2f;

			batch.draw(pausemenu.getImg(), centerX, centerY, 730, 370);
			batch.draw(closeButton.getTexture(), 500, 500);

			if (closeButton.isClicked(mouseX, mouseY)) {
				// System.out.println("done");
			}
		}

		enemyPath();
		batch.end();
	}

	private boolean isMouseInsideObject(float mouseX, float mouseY) {
		boolean isInside = false;

		for (MapObject object : noTowerZoneObject) {
			if (object instanceof RectangleMapObject) {
				Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
				if (rectangle.contains(mouseX, mouseY)) {
					isInside = true;
					break;
				}
			} else if (object instanceof PolygonMapObject) {
				Polygon polygon = ((PolygonMapObject) object).getPolygon();
				float[] vertices = polygon.getTransformedVertices();
				if (polygonContains(vertices, mouseX, mouseY)) {
					isInside = true;
					break;
				}
			}
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

	private void spawnNewEnemy(String enemyName) {
		Rectangle randomRectangle = randomRectangleObject.getRectangle();
		float minX = randomRectangle.x;
		float minY = randomRectangle.y;
		float maxX = randomRectangle.x + randomRectangle.width;
		float maxY = randomRectangle.y + randomRectangle.height;

		int randomX = (int) MathUtils.random(minX, maxX);
		int randomY = (int) MathUtils.random(minY, maxY);

		switch (enemyName) {
			case "Giant":
				Giant giant = new Giant(randomX, randomY, enemyPath());
				giant.setCoords(randomX - (giant.getImg().getRegionWidth() / 2), randomY);
				enemyList.add(giant);
				break;

			case "Zombie":
				Zombie zombie = new Zombie(randomX, randomY, enemyPath());
				zombie.setCoords(randomX - (zombie.getImg().getRegionWidth() / 2), randomY);
				enemyList.add(zombie);
				break;
		}
	}

	private float[] enemyPath() {
		float[] vertices = null;

		for (MapObject object : enemyPathObject) {
			if (object instanceof PolylineMapObject) {
				Polyline polyline = ((PolylineMapObject) object).getPolyline();

				vertices = polyline.getTransformedVertices();
				// The vertices array is in the format [x1, y1, x2, y2, x3, y3, ...]
			}
		}
		// System.out.println(Arrays.toString(vertices));
		return vertices;
	}

	@Override
	public void dispose() {
		batch.dispose();
		map.dispose();
		mapRenderer.dispose();
	}

	/*void spawnRocket(float spawnX, float spawnY, float targetX, float targetY, ATower origin) {
		HomingRocket homingRocket = new HomingRocket((int) spawnX, (int) spawnY);
		homingRocket.aim(targetX, targetY);
		homingRocket.setTower(origin);
		homingRocket.setLifetime(210);
		homingRocket.setDmg(20);
		projectileArray.add(homingRocket);
		homingRocket.setTargetCoords(targetX, targetY);
	}*/

	/*void spawnUpgradedRocket(float spawnX, float spawnY, float targetX, float targetY, ATower origin, int level) {
		HomingRocket homingRocket = new HomingRocket((int) spawnX, (int) spawnY);
		homingRocket.aim(targetX, targetY);
		homingRocket.setTower(origin);
		homingRocket.setLifetime(210 * level);
		homingRocket.setDmg(20 * level);
		projectileArray.add(homingRocket);
		homingRocket.setTargetCoords(targetX, targetY);
	}*/

	/*void spawnBullet(int spawnX, int spawnY, float targetX, float targetY) {
		Bullet bullet = new Bullet(spawnX, spawnY);
		bullet.aim(targetX, targetY);
		bullet.setLifetime(210);
		bullet.setDmg(5);
		projectileArray.add(bullet);
		bullet.setTargetCoords(targetX, targetY);
	}*/

	public void towers()
	{
		for (int i =0; i< towerList.size();i++)
		{
			ATower tower = towerList.get(i);

			if (tower instanceof Cannon)
			{
				Cannon cannon = (Cannon) tower;
				for (AEnemy enemy : enemyList)
				{
					if(cannon.isInRange(enemy))cannon.spawnProjectile(cannon.getAxisX(), cannon.getAxisY(),enemy.getAxisX(),enemy.getAxisY(),tower);
					cannon.updateProjectile(enemy);
					cannon.ProjectileHit(enemy);
					cannon.bulletAim(batch);
				}
			}
			if (tower instanceof ArcherTower)
			{
				ArcherTower archer = (ArcherTower) tower;
				for (AEnemy enemy : enemyList)
				{
					if(archer.isInRange(enemy)) archer.spawnProjectile(archer.getAxisX(), archer.getAxisY(),tower);
					archer.updateProjectile(enemy);
					archer.ProjectileHit(enemy);
					archer.bulletAim(batch);
				}
			}
		}
	}

	void projectiles() {
		for (int i = 0; i < projectileArray.size; i++) {
			Projectile projectiles = projectileArray.get(i);

			if (projectiles instanceof HomingRocket) {
				HomingRocket missile = (HomingRocket) projectiles;
				missile.homing(projectiles.getTargetCoordsX(), projectiles.getTargetCoordsY());
				batch.draw(missile.drawShadow(), missile.getPositionX() + 20, missile.getPositionY() - 20, 9, 9, 21, 7, 2, 2, missile.getRotation());
				batch.draw(missile.drawRocket(), missile.getPositionX(), missile.getPositionY(), 9, 9, 21, 7, 2, 2, missile.getRotation());
			}

			/*if (projectiles instanceof Bullet) {
				Bullet bullet = (Bullet) projectiles;
				bullet.shootAt(projectiles.getTargetCoordsX(), projectiles.getTargetCoordsY(), 50);
				bullet.aim(projectiles.getTargetCoordsX(), projectiles.getTargetCoordsY());
				batch.draw(bullet.drawRocket(), bullet.getPositionX(), bullet.getPositionY(), 9, 9, 21, 7, 2, 2, bullet.getRotation());
			}*/

			/*for (AEnemy enemy : enemyList) {
				if (projectiles.hitbox.overlaps(enemy.hitbox())) {
					deleteProjectile(projectiles);
					enemy.loseHp(projectiles.getDmg());
				}
			}*/

			projectiles.setLifetime(projectiles.getLifetime() - 1);
			if (projectiles.getLifetime() < 0 || enemyList.size() <= 0) {
				deleteProjectile(projectiles);
			}
		}
	}

	void deleteProjectile(Projectile projectile) {
		for (int i = 0; i < projectileArray.size; i++) {
			if (projectile == projectileArray.get(i))
				projectileArray.removeIndex(i);
		}
	}

	void deleteTower(ATower tower) {
		for (int i = 0; i < towerList.size(); i++) {
			if (tower == towerList.get(i))
				projectileArray.removeIndex(i);
		}
	}

	void deleteTower(int index) {
		towerList.remove(index);
	}
}
