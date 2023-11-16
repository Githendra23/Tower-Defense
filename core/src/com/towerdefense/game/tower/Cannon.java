package com.towerdefense.game.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.towerdefense.game.enemy.AEnemy;
import com.towerdefense.game.tower.projectiles.Bullet;
import com.towerdefense.game.tower.projectiles.HomingRocket;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends ATower {
    private final List<HomingRocket> rocketList;
    private final float ATTACK_INTERVAL = (float) 1;
    private AEnemy enemy = null;
    private float spawnTimer = 0;
    public Cannon(int x, int y) {
        super(200, 300, x, y, "defense/rocket_turret/turret.png");
        this.coolDown = 20;
        rocketList = new ArrayList<>();
    }

    public void spawnProjectile(float targetX, float targetY) {
        if (rocketList.isEmpty()) {
            spawnTimer += Gdx.graphics.getDeltaTime();

            if (spawnTimer >= ATTACK_INTERVAL) {
                HomingRocket homingRocket = new HomingRocket(this.getAxisX(), this.getAxisY());
                homingRocket.aim(targetX, targetY);
                homingRocket.setTower(this);
                homingRocket.setLifetime(210);
                homingRocket.setDmg(20);
                homingRocket.setTargetCoords(targetX, targetY);
                rocketList.add(homingRocket);

                spawnTimer = 0;
            }
        }
    }

    public void updateProjectile(AEnemy enemy) {
        if (!rocketList.isEmpty()) {
            for (HomingRocket rocket : rocketList) {
                rocket.setTargetCoords(enemy.getAxisX() + ((float) enemy.getImg().getRegionWidth() / 2), enemy.getAxisY() + ((float) enemy.getImg().getRegionHeight() / 2));
            }
        }
    }

    public void ProjectileHit(AEnemy enemy) {
        if (enemy == null || enemy.isDead()) {
            this.enemy = null;
            return;
        }

        if (this.enemy == null || this.enemy.isDead()) {
            if (this.isInRange(enemy)) {
                this.enemy = enemy;
            } else {
                this.enemy = null;
            }
            return;
        }

        if(!rocketList.isEmpty()) {
            for (int i = 0; i < rocketList.size(); i++) {
                HomingRocket rocket = rocketList.get(i);

                if (rocket.hitbox.overlaps(this.enemy.hitbox())) {
                    rocketList.remove(rocket);
                    this.enemy.loseHp(this.damage);
                }
            }
        }

    }

    public void bulletAim(SpriteBatch batch) {
        for (HomingRocket rocket : rocketList)
        {
            rocket.homing(rocket.getTargetCoordsX(), rocket.getTargetCoordsY());
            rocket.aim(rocket.getTargetCoordsX(), rocket.getTargetCoordsY());
            batch.draw(rocket.drawShadow(),rocket.getPositionX()-20, rocket.getPositionY()-20, 9, 9, 21, 7, 2, 2, rocket.getRotation());
            batch.draw(rocket.drawRocket(),rocket.getPositionX(), rocket.getPositionY(), 9, 9, 21, 7, 2, 2, rocket.getRotation());
        }
    }

    public List<HomingRocket> getProjectileList() {
        return rocketList;
    }
}
