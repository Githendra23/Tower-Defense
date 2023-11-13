package com.towerdefense.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.towerdefense.game.UI.*;
import com.towerdefense.game.enemy.AEnemy;
import com.towerdefense.game.enemy.Giant;
import com.towerdefense.game.enemy.Zombie;
import com.towerdefense.game.tower.ATower;
import com.towerdefense.game.tower.ArcherTower;
import com.towerdefense.game.tower.Cannon;

import java.math.BigInteger;

public class TowerDefense extends ApplicationAdapter {
	private int coins = 0;
	private BigInteger frameCount = BigInteger.ZERO;
	private SpriteBatch batch;
	private BitmapFont font;
//	private HomingRocket missile;
	private Bullet bullet;
	private Texture img;
	private final Array<Projectile> projectileArray = new Array<Projectile>();
	private final Array<Float> projectileTargetX= new Array<Float>();
	private final Array<Float> projectileTargetY= new Array<Float>();
	private final Array<ATower> towers=new Array<ATower>();
	private final Array<Float> towerCoordX= new Array<Float>();
	private final Array<Float> towerCoordY= new Array<Float>();
	private final Array<Integer> towerCooldown = new Array<Integer>();
	private final Array<AEnemy> enemies=new Array<AEnemy>();


	private Zombie zombie;
	private Giant giant;
	private Castle castle;

	private boolean isPaused = false;
	private Texture menuPause;
	private PauseMenu pausemenu;
	private CloseButton closeButton;
	private TowerButton towerButton;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private int tileHeight, tileWidth, layerHeight, layerWidth;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("turret.png");
//		missile= new HomingRocket(5,5);
		bullet= new Bullet(5,5);
		castle = new Castle(2000,1400,350);
		zombie = new Zombie(5,5);
		giant = new Giant(20,20);
		menuPause = new Texture("menu.png");

		// map
		map = new TmxMapLoader().load("map/map.tmx");

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

		// mouse cursor
		Pixmap pixmapMouse = new Pixmap(Gdx.files.internal("mouse.png")); // Make sure the path is correct
		int xHotspot = 15, yHotspot = 15;
		Cursor cursor = Gdx.graphics.newCursor(pixmapMouse, xHotspot, yHotspot);
		pixmapMouse.dispose();
		Gdx.graphics.setCursor(cursor);

		font = new BitmapFont();
		font.setColor(1, 1, 1, 1); // Set the font color (white in this example)
	}

	int X = 200, Y = 200;
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		mapRenderer.setView(camera);
		mapRenderer.render();
		for (ATower tower :towers)
		{
			tower.displayHitbox();
			tower.displayRangeHitbox();
		}
		for (AEnemy enemy: enemies)
		{
			enemy.displayHitbox();
		}
		batch.begin();
		batch.draw(img, (Gdx.input.getX()-120f), (float)-Gdx.input.getY() + (Gdx.graphics.getHeight()),35*2,37*2);
//		missile.homing(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
		bullet.shootAt(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)),20);
//		System.out.println(Gdx.input.isKeyPressed(Input.Keys.A));
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
		{
			addCannon(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());
		}
		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
		{
			addArcher(Gdx.input.getX(), -Gdx.input.getY());
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q))
		{
			spawnGiant(500, 500);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.A))
		{
			spawnBullet(10, 10, Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
		}



		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		frameCount = frameCount.add(BigInteger.ONE);

		// Render the map


//		batch.draw(missile.drawRocket(),missile.positionX,missile.positionY,9,9,307,137,1,1,missile.rotation);
//		batch.draw(bullet.drawRocket(),bullet.getPositionX(),bullet.getPositionY());

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			isPaused = !isPaused;
		}

		giant.setCoords(X, Y);
		System.out.println(giant.hitbox().overlaps(castle.hitbox()));


//		castle.displayHitbox();
//		giant.displayHitbox();

//		batch.begin();
		// display FPS
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);

		// display Coordinates of the mouse cursor
		font.draw(batch, "Mouse coords: " + mouseX + "X, " + mouseY + "Y", 10, Gdx.graphics.getHeight() - 30);

		// display mobs
		batch.draw(castle.getImg(), castle.getAxisX(), castle.getAxisY(),castle.getImg().getRegionWidth()*4,castle.getImg().getRegionHeight()*4);
		batch.draw(zombie.getImg(), zombie.getAxisX(), zombie.getAxisY());
		batch.draw(giant.getImg(), X, Y);


		batch.draw(towerButton.getTexture(), towerButton.getAxisX(), towerButton.getAxisY());

		if (!isPaused) {
			// all movements should be inside this condition
			if (frameCount.mod(new BigInteger("24")).equals(BigInteger.ZERO)) {
				X += giant.getSpeed() + 15;
				// frameCount = BigInteger.ZERO;
			}

			if (towerButton.isClicked(mouseX, mouseY)) {
				towerButton.setPressed(true);
			}

			if (towerButton.getIsSetPressed()) {
				batch.draw(towerButton.getSelectedImg(), mouseX - (towerButton.getTexture().getRegionWidth() / 2f), mouseY);
			}

			if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
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
		towers();
		enemies();
		projectiles();
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		map.dispose();
		mapRenderer.dispose();
	}
	void spawnRocket(int spawnX,int spawnY,float targetX, float targetY)
	{
		HomingRocket homingRocket=new HomingRocket(spawnX,spawnY);
		homingRocket.aim(targetX,targetY);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}
	void spawnRocket(float spawnX,float spawnY,float targetX, float targetY)
	{
		HomingRocket homingRocket=new HomingRocket((int)spawnX,(int)spawnY);
		homingRocket.aim(targetX,targetY);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}
	void spawnRocket(float spawnX,float spawnY,float targetX, float targetY, ATower origin)
	{
		HomingRocket homingRocket=new HomingRocket((int)spawnX,(int)spawnY);
		homingRocket.aim(targetX,targetY);
		homingRocket.setTower(origin);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}
	void spawnBullet(int spawnX,int spawnY,float targetX, float targetY)
	{
		Bullet bullet=new Bullet(spawnX,spawnY);
		bullet.aim(targetX,targetY);
		projectileArray.add(bullet);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}
	void spawnBullet(float spawnX,float spawnY,float targetX, float targetY, ATower origin)
	{
		Bullet homingRocket=new Bullet((int)spawnX,(int)spawnY);
		homingRocket.aim(targetX,targetY);
		bullet.setTower(origin);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}
	/*void spawnBullet2(float spawnX,float spawnY,float targetX, float targetY)
	{
		ShapeRenderer sr = new ShapeRenderer();
		sr.setColor(Color.YELLOW);
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.line(spawnX,spawnY,targetX,targetY);
		sr.end();

	}*/

	public void addCannon(int x,int y)
	{
		Cannon cannon =new Cannon(x,y);
		cannon.setCoords(x,y);
		towers.add(cannon);
		towerCoordX.add((float)x);
		towerCoordY.add((float)y);
		towerCooldown.add(20);
	}
	public void addArcher(int x, int y)
	{
		towers.add(new ArcherTower(x,y));
		towerCoordX.add((float) Gdx.input.getX()-120);
		towerCoordY.add((float)-Gdx.input.getY() + (Gdx.graphics.getHeight()));
		towerCooldown.add(20);
	}
	public void towers()
	{


		for (int i = 0; i<towers.size;i++)
		{

			ATower tower=towers.get(i);
			towerCooldown.set(i,towerCooldown.get(i)-1);
			if (tower instanceof Cannon)
			{

				Cannon cannon = (Cannon) tower;
				batch.draw(cannon.getImg(),towerCoordX.get(i),towerCoordY.get(i),39*2,35*2);





//				if (towerCooldown.get(i)<10) spawnBullet2(towerCoordX.get(i), towerCoordY.get(i), Gdx.input.getX() - 120, -Gdx.input.getY() + (Gdx.graphics.getHeight()));

				for (AEnemy enemy : enemies)
//					if (projectileArray.get(u).getTower()==towers.get(i))
				{
					System.out.println("test");
					System.out.println("cooldown: " +towerCooldown.get(i));
					System.out.println("range: "+ tower.isInRange(enemy));
					if (towerCooldown.get(i) <= 0 && tower.isInRange(enemy)) {
						spawnRocket(towerCoordX.get(i), towerCoordY.get(i) + 125, Gdx.input.getX(), -Gdx.input.getY() + (Gdx.graphics.getHeight()), tower);
						towerCooldown.set(i, 120);
					}
				}

				for(int u=0; u<projectileArray.size;u++)
				{
					for (AEnemy enemy : enemies)
//					if (projectileArray.get(u).getTower()==towers.get(i))
					{
						if (tower.isInRange(enemy)) {
//							System.out.println("shoot god dammit");
//							spawnRocket(towerCoordX.get(i), towerCoordY.get(i)+125, Gdx.input.getX(), -Gdx.input.getY() + (Gdx.graphics.getHeight()), tower);
							projectileTargetX.set(u, (float) enemy.getAxisX() + enemy.getImg().getRegionWidth() / 2);
							projectileTargetY.set(u, (float) enemy.getAxisY() + enemy.getImg().getRegionHeight() / 2);
						}
					}
				}

			}
			if (tower instanceof ArcherTower)
			{

				ArcherTower archer = (ArcherTower) tower;
				batch.draw(archer.getImg(),towerCoordX.get(i),towerCoordY.get(i));


				if (towerCooldown.get(i)<=0) {
					spawnBullet(towerCoordX.get(i), towerCoordY.get(i), Gdx.input.getX() - 120, -Gdx.input.getY() + (Gdx.graphics.getHeight()), tower);
					towerCooldown.set(i,120);
				}

				for(int u=0; u<projectileArray.size;u++)
				{
					if (projectileArray.get(u).getTower()==towers.get(i))
					{
						System.out.println("shoot again");
						projectileTargetX.set(u, (float)giant.getAxisX());
						projectileTargetY.set(u, (float)giant.getAxisY());
					}
				}

			}

		}
	}
	void projectiles()
	{
		for (int i = 0; i<projectileArray.size; i++)
		{
			Projectile projectiles = projectileArray.get(i);
			if (projectiles instanceof HomingRocket) {
				HomingRocket missile=(HomingRocket) projectiles;
				missile.homing(projectileTargetX.get(i),projectileTargetY.get(i));
				batch.draw(missile.drawShadow(), missile.positionX+20, missile.positionY-20, 9, 9, 21, 7, 2, 2, missile.rotation);
//				missile.displayHitbox();
				batch.draw(missile.drawRocket(), missile.positionX, missile.positionY, 9, 9, 21, 7, 2, 2, missile.rotation);

			}
			if(projectiles instanceof Bullet)
			{
				Bullet bullet= (Bullet) projectiles;
				bullet.shootAt(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)),20);
				batch.draw(bullet.drawRocket(),bullet.getPositionX(),bullet.getPositionY());
			}
			for (AEnemy enemy : enemies)
			{
				if (projectiles.hitbox.overlaps(enemy.hitbox())) {
					deleteProjectile(projectiles);
					enemy.loseHp(20);
				}
			}
		}
	}
	void enemies()
	{
		for (int i=0;i<enemies.size;i++)
		{
			AEnemy enemy=enemies.get(i);
			System.out.println(enemy.getHp());
			if (enemy instanceof Giant)
			{
				Giant giant = (Giant) enemy;
				if (frameCount.mod(new BigInteger("24")).equals(BigInteger.ZERO)) giant.move(1,0);
				batch.draw(giant.getImg(),giant.getAxisX(),giant.getAxisY());
			}
			if (enemy.isDead())
			{
				deleteEnemy(i);
			}
		}
	}
	void deleteProjectile(Projectile projectile)
	{
		for(int i=0;i<projectileArray.size;i++)
		{
			if (projectile==projectileArray.get(i))projectileArray.removeIndex(i);
		}
	}
	void deleteProjectile(int index)
	{
		projectileArray.removeIndex(index);
	}
	void deleteTower(ATower tower)
	{
		for(int i=0;i<towers.size;i++)
		{
			if (tower==towers.get(i))projectileArray.removeIndex(i);
		}
	}
	void deleteTower(int index)
	{
		towers.removeIndex(index);
	}

	void spawnGiant(int x,int y)
	{
		Giant giant= new Giant(x,y);

		enemies.add(giant);
	}
	void spawnZombie(int x, int y)
	{
		Zombie zombie= new Zombie(x,y);

		enemies.add(zombie);
	}

	void deleteEnemy(AEnemy enemy)
	{
		for(int i=0;i<enemies.size;i++)
		{
			if (enemy==enemies.get(i)) {
				projectileArray.removeIndex(i);
				projectileTargetY.removeIndex(i);
				projectileTargetX.removeIndex(i);
			}
		}
	}
	void deleteEnemy(int index)
	{
		enemies.removeIndex(index);
	}
}
