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
import com.badlogic.gdx.utils.Array;
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
		img = new Texture("towerdefensetr.png");
//		missile= new HomingRocket(5,5);
		bullet= new Bullet(5,5);
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
		font.setColor(1, 1, 1, 1); // Set the font color (white in this example)
	}

	float X = 200, Y = 200;
	@Override
	public void render () {
		ScreenUtils.clear(0.3f, 0.5f, 0, 1);
		batch.begin();
		batch.draw(img, (Gdx.input.getX()-120f), (float)-Gdx.input.getY() + (Gdx.graphics.getHeight()));
//		missile.homing(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
		bullet.shootAt(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)),20);
//		System.out.println(Gdx.input.isKeyPressed(Input.Keys.A));
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
		{
			addCannon();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			spawnRocket(10, 10, Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.A))
		{
			spawnBullet(10, 10, Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
		}
		projectiles();
		towers();

//		batch.draw(missile.drawRocket(),missile.positionX,missile.positionY,9,9,307,137,1,1,missile.rotation);
//		batch.draw(bullet.drawRocket(),bullet.getPositionX(),bullet.getPositionY());
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();
		frameCount++;

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			isPaused = !isPaused;
		}



		// display FPS
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);

		font.draw(batch, "Mouse coords: " + mouseX + "X, " + mouseY + "Y", 10, Gdx.graphics.getHeight() - 30);

		batch.draw(zombie.getImg(), 100, 100);
		batch.draw(giant.getImg(), X, Y);
		batch.draw(castle.getImg(), Gdx.graphics.getWidth() - 400, ((float) Gdx.graphics.getHeight() / 2) - 150,355,220);

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
	void spawnRocket(int spawnX,int spawnY,float targetX, float targetY)
	{
//		System.out.println("Spawn rocket");
		HomingRocket homingRocket=new HomingRocket(spawnX,spawnY);
		homingRocket.aim(targetX,targetY);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}
	void spawnRocket(float spawnX,float spawnY,float targetX, float targetY)
	{
//		System.out.println("Spawn rocket");
		HomingRocket homingRocket=new HomingRocket((int)spawnX,(int)spawnY);
		homingRocket.aim(targetX,targetY);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}
	void spawnRocket(float spawnX,float spawnY,float targetX, float targetY, ATower origin)
	{
//		System.out.println("Spawn rocket");
		HomingRocket homingRocket=new HomingRocket((int)spawnX,(int)spawnY);
		homingRocket.aim(targetX,targetY);
		homingRocket.setTower(origin);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}
	void spawnBullet(int spawnX,int spawnY,float targetX, float targetY)
	{
//		System.out.println("Spawn bullet");
		Bullet homingRocket=new Bullet(spawnX,spawnY);
		bullet.aim(targetX,targetY);
		projectileArray.add(homingRocket);
		projectileTargetX.add(targetX);
		projectileTargetY.add(targetY);
	}

	public void addCannon()
	{
		towers.add(new Cannon());
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
				batch.draw(cannon.img,towerCoordX.get(i),towerCoordY.get(i));

				if (towerCooldown.get(i)<=0) {
					spawnRocket(towerCoordX.get(i), towerCoordY.get(i), Gdx.input.getX() - 120, -Gdx.input.getY() + (Gdx.graphics.getHeight()), tower);
					towerCooldown.set(i,120);
				}

				for(int u=0; u<projectileArray.size;u++)
				{
					if (projectileArray.get(u).getTower()==towers.get(i))
					{
						projectileTargetX.set(u, (float) Gdx.input.getX());
						projectileTargetY.set(u, -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
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
//			System.out.println(projectiles+ " "+ i);
			if (projectiles instanceof HomingRocket) {
				HomingRocket missile=(HomingRocket) projectiles;
				missile.homing(projectileTargetX.get(i),projectileTargetY.get(i));
				batch.draw(missile.drawRocket(), missile.positionX, missile.positionY, 9, 9, 307, 137, 1, 1, missile.rotation);
			}
			if(projectiles instanceof Bullet)
			{
				Bullet bullet= (Bullet) projectiles;
				bullet.shootAt(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)),20);
				batch.draw(bullet.drawRocket(),bullet.getPositionX(),bullet.getPositionY());
			}
		}
	}
}
