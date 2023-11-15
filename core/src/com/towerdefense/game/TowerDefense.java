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
import projectiles.Bullet;
import projectiles.HomingRocket;
import projectiles.Projectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TowerDefense extends ApplicationAdapter {
	private int coins = 0;
	private double frameCount = 0;
	private SpriteBatch batch;
	private BitmapFont font;
	// private HomingRocket missile;
	private Bullet bullet;
	private Texture img;
	private final Array<Projectile> projectileArray = new Array<Projectile>();
	private final Array<Float> projectileTargetX = new Array<Float>();
	private final Array<Float> projectileTargetY = new Array<Float>();
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
		randomRectangleObject = map.getLayers().get("startPoint").getObjects().getByType(RectangleMapObject.class)
				.first();
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

		bullet.shootAt(Gdx.input.getX() - (((float) img.getHeight()) / 2),
				-Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)), 20);
		// System.out.println(Gdx.input.isKeyPressed(Input.Keys.A));

		if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			spawnBullet(10, 10, Gdx.input.getX() - (((float) img.getHeight()) / 2),
					-Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
		}

		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);
		frameCount++;
		// display FPS
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);

		// display Coordinates of the mouse cursor
		font.draw(batch, "Mouse coords: " + mouseX + "X, " + mouseY + "Y", 10, Gdx.graphics.getHeight() - 30);

		// display mobs
		batch.draw(castle.getImg(), castle.getAxisX(), castle.getAxisY());
		batch.draw(zombie.getImg(), zombie.getAxisX(), zombie.getAxisY());
		// batch.draw(archerTower.getImg(), archerTower.getAxisX(), archerTower.getAxisY());

		for (AEnemy enemy : enemyList) {
			if (enemyList.size() > 0) {
				batch.draw(enemy.getImg(), enemy.getAxisX(), enemy.getAxisY());
			}
		}

		towers();
		enemies();
		projectiles();

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
					if (count < 2) {
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
				// System.out.println(castle.getHp());
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
								towerList.add(new ArcherTower((int) (mouseX - (towerButton.getTexture().getRegionWidth() / 2f)), mouseY));

								/*if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
									addCannon(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
								}
								if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
									addArcher((Gdx.input.getX()), Gdx.graphics.getHeight() - Gdx.input.getY());
								}*/
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

	void spawnRocket(int spawnX, int spawnY, float targetX, float targetY) {
		HomingRocket homingRocket = new HomingRocket(spawnX, spawnY);
		homingRocket.aim(targetX, targetY);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}

	void spawnRocket(float spawnX, float spawnY, float targetX, float targetY) {
		HomingRocket homingRocket = new HomingRocket((int) spawnX, (int) spawnY);
		homingRocket.aim(targetX, targetY);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}

	void spawnRocket(float spawnX, float spawnY, float targetX, float targetY, ATower origin) {
		HomingRocket homingRocket = new HomingRocket((int) spawnX, (int) spawnY);
		homingRocket.aim(targetX, targetY);
		homingRocket.setTower(origin);
		homingRocket.setLifetime(210);
		homingRocket.setDmg(20);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}

	void spawnUpgradedRocket(float spawnX, float spawnY, float targetX, float targetY, ATower origin, int level) {
		HomingRocket homingRocket = new HomingRocket((int) spawnX, (int) spawnY);
		homingRocket.aim(targetX, targetY);
		homingRocket.setTower(origin);
		homingRocket.setLifetime(210 * level);
		homingRocket.setDmg(20 * level);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}

	void spawnBullet(int spawnX, int spawnY, float targetX, float targetY) {
		Bullet bullet = new Bullet(spawnX, spawnY);
		bullet.aim(targetX, targetY);
		bullet.setLifetime(210);
		bullet.setDmg(5);
		projectileArray.add(bullet);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}

	void spawnBullet(float spawnX, float spawnY, float targetX, float targetY, ATower origin) {
		Bullet bullet = new Bullet((int) spawnX, (int) spawnY);
		bullet.aim(targetX, targetY);
		bullet.setTower(origin);
		bullet.setLifetime(210);
		bullet.setDmg(5);
		projectileArray.add(bullet);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}
	/*
	 * void spawnBullet2(float spawnX,float spawnY,float targetX, float targetY)
	 * {
	 * ShapeRenderer sr = new ShapeRenderer();
	 * sr.setColor(Color.YELLOW);
	 * sr.begin(ShapeRenderer.ShapeType.Line);
	 * sr.line(spawnX,spawnY,targetX,targetY);
	 * sr.end();
	 * 
	 * }
	 */

	public void addCannon(int x, int y) {
		Cannon cannon = new Cannon(x, y);
		cannon.setCoords(x, y);
		towers.add(cannon);
		towerCoordX.add((float) x);
		towerCoordY.add((float) y);
		towerCooldown.add(20);
	}

	public void addArcher(int x, int y) {
		ArcherTower archer = new ArcherTower(x, y);
		archer.setCoords(x, y);
		towers.add(archer);
		towerCoordX.add((float) x);
		towerCoordY.add((float) y);
		towerCooldown.add(20);
	}

	public void towers() {
		for (int i = 0; i < towers.size; i++) {

			ATower tower = towers.get(i);
			towerCooldown.set(i, towerCooldown.get(i) - 1);
			if (tower instanceof Cannon) {

				Cannon cannon = (Cannon) tower;
				batch.draw(cannon.getImg(), towerCoordX.get(i), towerCoordY.get(i), 39 * 2, 35 * 2);

				// if (towerCooldown.get(i)<10) spawnBullet2(towerCoordX.get(i),
				// towerCoordY.get(i), Gdx.input.getX() - 120, -Gdx.input.getY() +
				// (Gdx.graphics.getHeight()));

				for (AEnemy enemy : enemyList)
				// if (projectileArray.get(u).getTower()==towers.get(i))
				{

					// System.out.println("test");
					// System.out.println("cooldown: " +towerCooldown.get(i));
					// System.out.println("range: "+ tower.isInRange(enemy));

					if (towerCooldown.get(i) <= 0 && tower.isInRange(enemy)) {
						if (tower.getLevel() <= 1)
							spawnRocket(towerCoordX.get(i) - 20, towerCoordY.get(i) + 50, Gdx.input.getX(),
									-Gdx.input.getY() + (Gdx.graphics.getHeight()), tower);
						else
							spawnUpgradedRocket(towerCoordX.get(i) - 20, towerCoordY.get(i) + 50, Gdx.input.getX(),
									-Gdx.input.getY() + (Gdx.graphics.getHeight()), tower, tower.getLevel());
						if (120 - 5 * tower.getLevel() > 5)
							towerCooldown.set(i, 120 - 5 * tower.getLevel());
						else
							towerCooldown.set(i, 5);
					}
					// if (tower.isInRange(enemy))isinrange=true;
					// System.out.println(isinrange);
					// if (!isinrange) {
					//
					// for (int x = 0; x < projectileArray.size; x++) {
					// if (projectileArray.get(x).getTower() == tower) deleteProjectile(x);
					// }
					// }
				}

				for (int u = 0; u < projectileArray.size; u++) {
					for (int x = enemyList.toArray().length - 1; x >= 0; x--)

					// if (projectileArray.get(u).getTower()==towers.get(i))
					{
						AEnemy enemy = enemyList.get(x);
						if (tower.isInRange(enemy)) {
							// System.out.println("shoot god dammit");
							// spawnRocket(towerCoordX.get(i), towerCoordY.get(i)+125, Gdx.input.getX(),
							// -Gdx.input.getY() + (Gdx.graphics.getHeight()), tower);
							projectileTargetX.set(u, (float) enemy.getAxisX() + enemy.getImg().getRegionWidth() / 2);
							projectileTargetY.set(u, (float) enemy.getAxisY() + enemy.getImg().getRegionHeight() / 2);
						}
					}
				}

			}
			if (tower instanceof ArcherTower) {

				ArcherTower archer = (ArcherTower) tower;
				batch.draw(archer.getImg(), towerCoordX.get(i), towerCoordY.get(i),
						archer.getImg().getRegionWidth() * 2, archer.getImg().getRegionHeight() * 2);

				// if (towerCooldown.get(i)<=0) {
				// spawnBullet(towerCoordX.get(i), towerCoordY.get(i), Gdx.input.getX() - 120,
				// -Gdx.input.getY() + (Gdx.graphics.getHeight()), tower);
				// towerCooldown.set(i,90-2*tower.getLevel());
				// }

				for (AEnemy enemy : enemyList) {
					if (towerCooldown.get(i) <= 0 && tower.isInRange(enemy)) {
						if (tower.getLevel() <= 1)
							spawnBullet(towerCoordX.get(i) - 10, towerCoordY.get(i) + 20, Gdx.input.getX(),
									-Gdx.input.getY() + (Gdx.graphics.getHeight()), tower);
						if (120 - 5 * tower.getLevel() > 5)
							towerCooldown.set(i, 50 - 5 * tower.getLevel());
						else
							towerCooldown.set(i, 5);
					}
				}
				for (int u = 0; u < projectileArray.size; u++) {
					for (int x = enemyList.size() - 1; x >= 0; x--)

					// if (projectileArray.get(u).getTower()==towers.get(i))
					{
						AEnemy enemy = enemyList.get(x);
						if (tower.isInRange(enemy)) {
							// System.out.println("shoot god dammit");
							// spawnRocket(towerCoordX.get(i), towerCoordY.get(i)+125, Gdx.input.getX(),
							// -Gdx.input.getY() + (Gdx.graphics.getHeight()), tower);
							projectileTargetX.set(u, (float) enemy.getAxisX() + enemy.getImg().getRegionWidth() / 2);
							projectileTargetY.set(u, (float) enemy.getAxisY() + enemy.getImg().getRegionHeight() / 2);
						}
					}
				}

			}

		}
	}

	void projectiles() {
		for (int i = 0; i < projectileArray.size; i++) {
			Projectile projectiles = projectileArray.get(i);

			if (projectiles instanceof HomingRocket) {
				HomingRocket missile = (HomingRocket) projectiles;
				missile.homing(projectileTargetX.get(i), projectileTargetY.get(i));
				batch.draw(missile.drawShadow(), missile.getPositionX() + 20, missile.getPositionY() - 20, 9, 9, 21, 7,
						2, 2, missile.getRotation());
				// missile.displayHitbox();
				batch.draw(missile.drawRocket(), missile.getPositionX(), missile.getPositionY(), 9, 9, 21, 7, 2, 2,
						missile.getRotation());
				// System.out.println("missile lifetime: "+missile.getLifetime());

			}
			if (projectiles instanceof Bullet) {
				Bullet bullet = (Bullet) projectiles;
				bullet.shootAt(projectileTargetX.get(i), projectileTargetY.get(i), 50);
				bullet.aim(projectileTargetX.get(i), projectileTargetY.get(i));
				batch.draw(bullet.drawRocket(), bullet.getPositionX(), bullet.getPositionY(), 9, 9, 21, 7, 2, 2,
						bullet.getRotation());
			}
			for (AEnemy enemy : enemyList) {
				if (projectiles.hitbox.overlaps(enemy.hitbox())) {
					deleteProjectile(projectiles);
					enemy.loseHp(projectiles.getDmg());
				}
			}

			projectiles.setLifetime(projectiles.getLifetime() - 1);
			// System.out.println("lifetime: "+projectiles.getLifetime());
			 if(projectiles.getLifetime()<0 || enemyList.size() <=0)
			 {
			// System.out.println("removed");
			 deleteProjectile(projectiles);
			 }
		}
	}

	void enemies() {
		for (int i = 0; i < enemyList.size(); i++) {
			AEnemy enemy = enemyList.get(i);
			System.out.println("health: " + enemy.getHp());
			// System.out.println(enemy.getHp());
			if (enemy instanceof Giant) {
				Giant giant = (Giant) enemy;
				if (frameCount % 24 == 0)
					giant.move();
				batch.draw(giant.getImg(), giant.getAxisX(), giant.getAxisY());
			}
			if (enemy.isDead()) {
				deleteEnemy(i);
			}
		}
	}

	void deleteProjectile(Projectile projectile) {
		for (int i = 0; i < projectileArray.size; i++) {
			if (projectile == projectileArray.get(i))
				projectileArray.removeIndex(i);
		}
	}

	void deleteProjectile(int index) {
		projectileArray.removeIndex(index);
	}

	void deleteTower(ATower tower) {
		for (int i = 0; i < towers.size; i++) {
			if (tower == towers.get(i))
				projectileArray.removeIndex(i);
		}
	}

	void deleteTower(int index) {
		towers.removeIndex(index);
	}

	void deleteEnemy(AEnemy enemy) {
		for (int i = 0; i < enemyList.size(); i++) {
			if (enemy == enemyList.get(i)) {
				projectileArray.removeIndex(i);
				projectileTargetY.removeIndex(i);
				projectileTargetX.removeIndex(i);
			}
		}
	}

	void deleteEnemy(int index) {
		enemyList.remove(index);
	}
}
