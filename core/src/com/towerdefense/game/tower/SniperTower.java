package com.towerdefense.game.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.towerdefense.game.enemy.AEnemy;
import com.towerdefense.game.tower.projectiles.Bullet;

import java.util.ArrayList;
import java.util.List;

public class SniperTower extends ATower {
    protected final List<Bullet> bulletList;
    private float spawnTimer = 0;
    public SniperTower(int x, int y) {
        super(50, 500, x, y, "sniper.png");
        this.coolDown = 20;
        bulletList = new ArrayList<>();
    }
boolean isShooting;
    public void spawnProjectile(int x, int y) {
        spawnTimer += Gdx.graphics.getDeltaTime();

        float ATTACK_INTERVAL = (float) 1;
        if (spawnTimer >= ATTACK_INTERVAL) {
            isShooting=true;

            spawnTimer = 0;
        }
    }

    public void updateProjectile(AEnemy enemy) {
        if (!bulletList.isEmpty()) {
            for (Bullet bullet : bulletList) {
                bullet.setTargetCoords(enemy.getAxisX() + ((float) enemy.getImg().getRegionWidth() / 2), enemy.getAxisY() + ((float) enemy.getImg().getRegionHeight() / 2));
            }
        }
    }

    public void ProjectileHit(AEnemy enemy) {
//        if (!bulletList.isEmpty()) {
//            for (int i = 0; i < bulletList.size(); i++) {
//                Bullet bullet = bulletList.get(i);
//
//                if (bullet.hitbox.overlaps(enemy.hitbox()) && this.isInRange(enemy)) {
//                    bulletList.remove(i);
                    enemy.loseHp(50);
                }



    int targetX;
    int targetY;
    public void projectileMove()
    {
        isShooting=false;
//        for (Bullet bullet : bulletList) {
//            bullet.shootAt(targetX, targetY,20);
//            bullet.aim(targetX, targetY);
//        }
    }
    public void sniperLaser()
    {
        System.out.println(targetX+"+"+targetY);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (isShooting==true) shapeRenderer.setColor(Color.YELLOW);
        else shapeRenderer.setColor(Color.RED); // Set the color of the hitbox
        shapeRenderer.line(hitbox.x, hitbox.y+ hitbox().getHeight()-8, targetX, targetY);
        shapeRenderer.end();
    }
    public void projectileAim(AEnemy enemy) {
        System.out.println(targetX+"+"+targetY);
        targetX=enemy.getAxisX();
        targetY= enemy.getAxisY();
        if (!bulletList.isEmpty()) {
            for (Bullet bullet : bulletList) {
                bullet.shootAt(bullet.getTargetCoordsX(), bullet.getTargetCoordsY(), this.getDamage());
                bullet.aim(bullet.getTargetCoordsX(), bullet.getTargetCoordsY());
            }
        }
        sniperLaser();
    }

    public void drawProjectile(SpriteBatch batch) {
        for (Bullet bullet : bulletList) {
            batch.draw(bullet.drawRocket(), bullet.getPositionX(), bullet.getPositionY(), 9, 9, 21, 7, 2, 2, bullet.getRotation());
        }
    }

    public List<Bullet> getProjectileList() {
        return bulletList;
    }
}