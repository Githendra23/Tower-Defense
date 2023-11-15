package com.towerdefense.game.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.towerdefense.game.enemy.AEnemy;
import com.towerdefense.game.tower.projectiles.Bullet;

import java.util.ArrayList;
import java.util.List;

public class ArcherTower extends ATower {
    private final List<Bullet> bulletList;
    private final float ATTACK_INTERVAL = (float) 1/3;
    private float spawnTimer = 0;
    public ArcherTower(int x, int y) {
        super(50, 200, x, y, "gun.png");
        this.coolDown = 20;
        bulletList = new ArrayList<>();
    }

    public void spawnProjectile(float x, float y, ATower tower) {
        spawnTimer += Gdx.graphics.getDeltaTime();

        if (spawnTimer >= ATTACK_INTERVAL) {
            Bullet bullet = new Bullet((int) x, (int) x);
            bullet.aim(x, y);
            bullet.setTower(tower);
            bullet.setLifetime(210);
            bullet.setDmg(5);
            bullet.setTargetCoords(x, y);
            bulletList.add(bullet);

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
        if (!bulletList.isEmpty()) {
            for (int i = 0; i < bulletList.size(); i++) {
                Bullet bullet = bulletList.get(i);

                if (bullet.hitbox.overlaps(enemy.hitbox())) {
                    bulletList.remove(i);
                    enemy.loseHp(bullet.getDmg());
                }
            }
        }
    }

    public void bulletAim(SpriteBatch batch) {
        if (!bulletList.isEmpty()) {
            for (Bullet bullet : bulletList) {
                bullet.shootAt(bullet.getTargetCoordsX(), bullet.getTargetCoordsY(), this.getDamage());
                bullet.aim(bullet.getTargetCoordsX(), bullet.getTargetCoordsY());
                batch.draw(bullet.drawRocket(), bullet.getPositionX(), bullet.getPositionY(), 9, 9, 21, 7, 2, 2, bullet.getRotation());
            }
        }
    }
}