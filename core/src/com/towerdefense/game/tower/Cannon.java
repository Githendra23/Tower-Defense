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
    private float spawnTimer = 0;
    public Cannon(int x, int y) {
        super(200, 300, x, y, "defense/rocket_turret/turret.png");
        this.coolDown = 20;
        rocketList = new ArrayList<>();
    }

    public void spawnProjectile(float spawnX, float spawnY, float targetX, float targetY, ATower tower) {
        if (rocketList.size() == 0) {
            spawnTimer += Gdx.graphics.getDeltaTime();

            if (spawnTimer >= ATTACK_INTERVAL) {
                HomingRocket homingRocket = new HomingRocket((int) spawnX, (int) spawnY);
                homingRocket.aim(targetX, targetY);
                homingRocket.setTower(tower);
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
        if(!rocketList.isEmpty()) {
            for (int i=0; i<rocketList.size();i++) {
                HomingRocket rocket= rocketList.get(i);
                if (rocket.hitbox.overlaps(enemy.hitbox()) && this.isInRange(enemy)) {
                    rocketList.remove(rocket);
                    enemy.loseHp(damage);
                }
            }
        }

    }

    public void bulletAim(SpriteBatch batch) {
        for (HomingRocket rocket: rocketList)
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
