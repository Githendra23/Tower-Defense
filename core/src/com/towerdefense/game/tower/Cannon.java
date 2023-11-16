package com.towerdefense.game.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.towerdefense.game.enemy.AEnemy;
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

    @Override
    public void spawnProjectile(int x, int y) {
        if (rocketList.isEmpty()) {
            spawnTimer += Gdx.graphics.getDeltaTime();

            if (spawnTimer >= ATTACK_INTERVAL) {
                HomingRocket homingRocket = new HomingRocket(this.getAxisX(), this.getAxisY());
                homingRocket.aim(x, y);
                homingRocket.setTower(this);
                homingRocket.setLifetime(210);
                homingRocket.setDmg(20);
                homingRocket.setTargetCoords(x, y);
                rocketList.add(homingRocket);

                spawnTimer = 0;
            }
        }
    }

    public void updateProjectile(AEnemy enemy) {
        if (this.enemy == null || this.enemy.isDead() || !this.isInRange(this.enemy)) {
            if (this.isInRange(enemy)) {
                this.enemy = enemy;
            } else {
                this.enemy = null;
            }
            return;
        }

        if (!rocketList.isEmpty()) {
            for (HomingRocket rocket : rocketList) {
                rocket.setTargetCoords(this.enemy.getAxisX() + ((float) this.enemy.getImg().getRegionWidth() / 2), this.enemy.getAxisY() + ((float) this.enemy.getImg().getRegionHeight() / 2));
            }
        }
    }

    public void ProjectileHit(AEnemy enemy) {
        if (this.enemy == null || this.enemy.isDead() || !this.isInRange(this.enemy)) {
            if (this.isInRange(enemy)) {
                this.enemy = enemy;
            } else {
                this.enemy = null;
            }
            return;
        }

        if (!rocketList.isEmpty()) {
            for (int i = 0; i < rocketList.size(); i++) {
                HomingRocket rocket = rocketList.get(i);

                if (rocket.hitbox.overlaps(this.enemy.hitbox())) {
                    rocketList.remove(rocket);
                    this.enemy.loseHp(this.damage);
                }
            }
        }
        else {

        }
    }

    public void projectileAim(AEnemy enemy) {
        if (this.enemy == null || this.enemy.isDead() || !this.isInRange(this.enemy)) {
            if (this.isInRange(enemy)) {
                this.enemy = enemy;
            } else {
                this.enemy = null;
            }
            return;
        }

        for (HomingRocket rocket : rocketList) {
            rocket.homing(this.enemy.getAxisX(), this.enemy.getAxisY());
            rocket.aim(this.enemy.getAxisX(), this.enemy.getAxisY());
        }
    }

    public void drawProjectile(SpriteBatch batch) {
        for (HomingRocket rocket : rocketList) {
            if (this.enemy == null || this.isInRange(this.enemy)) {
                batch.draw(rocket.drawShadow(), rocket.getPositionX() - 20, rocket.getPositionY() - 20, 9, 9, 21, 7, 2, 2, rocket.getRotation());
                batch.draw(rocket.drawRocket(), rocket.getPositionX(), rocket.getPositionY(), 9, 9, 21, 7, 2, 2, rocket.getRotation());
            }
        }
    }

    public List<HomingRocket> getProjectileList() {
        return rocketList;
    }
}
