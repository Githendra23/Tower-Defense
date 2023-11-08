package com.towerdefense.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.*;

public class TowerDefense extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private BitmapFont font;
//	private HomingRocket missile;
	private Bullet bullet;
	private final Array<Projectile> projectileArray = new Array<Projectile>();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
//		missile= new HomingRocket(5,5);
		bullet= new Bullet(5,5);

		Pixmap pixmap = new Pixmap(Gdx.files.internal("mouse.png")); // Make sure the path is correct
		int xHotspot = 15, yHotspot = 15;
		Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
		pixmap.dispose(); // We don't need the pixmap anymore
		Gdx.graphics.setCursor(cursor);

		font = new BitmapFont();
		font.setColor(1, 1, 1, 1); // Set the font color (white in this example)
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.3f, 0.5f, 0, 1);
		batch.begin();
		batch.draw(img, Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
//		missile.homing(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
		bullet.shootAt(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)),20);
		System.out.println(Gdx.input.isKeyPressed(Input.Keys.A));
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			spawnRocket(10, 10, Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.A))
		{
			spawnBullet(10, 10, Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
		}
		projectiles();

//		batch.draw(missile.drawRocket(),missile.positionX,missile.positionY,9,9,307,137,1,1,missile.rotation);
//		batch.draw(bullet.drawRocket(),bullet.getPositionX(),bullet.getPositionY());
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	void spawnRocket(int spawnX,int spawnY,float targetX, float targetY)
	{
		System.out.println("Spawn rocket");
		HomingRocket homingRocket=new HomingRocket(spawnX,spawnY);
		homingRocket.aim(targetX,targetY);
		projectileArray.add(homingRocket);
	}
	void spawnBullet(int spawnX,int spawnY,float targetX, float targetY)
	{
		System.out.println("Spawn bullet");
		Bullet homingRocket=new Bullet(spawnX,spawnY);
		bullet.aim(targetX,targetY);
		projectileArray.add(homingRocket);
	}
	void projectiles()
	{
		for (Projectile projectiles:projectileArray)
		{

			if (projectiles instanceof HomingRocket) {
				HomingRocket missile=(HomingRocket) projectiles;
				missile.homing(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
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
