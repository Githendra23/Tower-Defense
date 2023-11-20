package com.towerdefense.game.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.towerdefense.game.enemy.AEnemy;
import com.towerdefense.game.tower.projectiles.Bullet;

import java.util.ArrayList;
import java.util.List;

public class GunTurret extends ATower {
    protected final List<Bullet> bulletList;
    private final float ATTACK_INTERVAL = (float) 1;
    private float spawnTimer = 0;
    public GunTurret(int x, int y) {
        super(50, 200, x, y, "defense/gun_turret/gun_turret.png");
        this.coolDown = 20;
        bulletList = new ArrayList<>();

        this.price = 50;
        this.addAnimation("defense/gun_turret/gun_turret_sheet.png", this.img.getRegionWidth());
    }

    public void spawnProjectile(int x, int y) {
        if (spawnTimer >= ATTACK_INTERVAL) {
            Bullet bullet = new Bullet(this.getAxisX(), this.getAxisY());
            bullet.aim(x, y);
            bullet.setTower(this);
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

                if (bullet.hitbox.overlaps(enemy.hitbox()) && this.isInRange(enemy)) {
                    bulletList.remove(i);
                    enemy.loseHp(bullet.getDmg());
                }
            }
        }
    }

    int targetX;
    int targetY;
    public void projectileMove()
    {
        spawnTimer += Gdx.graphics.getDeltaTime();
        for (Bullet bullet : bulletList) {
            bullet.shootAt(targetX, targetY,20);
            bullet.aim(targetX, targetY);
        }
    }
    public void projectileAim(AEnemy enemy) {
        targetX=enemy.getAxisX()+enemy.getImg().getRegionWidth()/2;
        targetY= enemy.getAxisY()+enemy.getImg().getRegionHeight()/2;
        if (!bulletList.isEmpty()) {
            for (Bullet bullet : bulletList) {
                bullet.shootAt(bullet.getTargetCoordsX(), bullet.getTargetCoordsY(), this.getDamage());
                bullet.aim(bullet.getTargetCoordsX(), bullet.getTargetCoordsY());
            }
        }
    }

    public void drawProjectile(SpriteBatch batch) {
        for (Bullet bullet : bulletList) {
            batch.draw(bullet.drawRocket(), bullet.getPositionX(), bullet.getPositionY(), 9, 9, 21, 7, 2, 2, bullet.getRotation());
        }
    }

    public List<Bullet> getProjectileList() {
        return bulletList;
    }

    @Override
    public void dispose() {
        super.dispose();

        for (Bullet bullet : bulletList) {
            bullet.dispose();
        }
    }
}