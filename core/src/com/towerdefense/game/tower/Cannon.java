package com.towerdefense.game.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.towerdefense.game.enemy.AEnemy;
import com.towerdefense.game.tower.projectiles.HomingRocket;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends ATower {
    private final List<HomingRocket> rocketList;
    private final float ATTACK_INTERVAL = (float) 1/3;
    private float spawnTimer = 0;
    public Cannon(int x, int y) {
        super(200, 300, x, y, "defense/rocket_turret/turret.png");
        this.coolDown = 20;
        rocketList = new ArrayList<>();
    }

    public void spawnProjectile(float spawnX, float spawnY, float targetX, float targetY, ATower tower) {
        spawnTimer += Gdx.graphics.getDeltaTime();

        if (spawnTimer >= ATTACK_INTERVAL) {
            HomingRocket homingRocket = new HomingRocket((int) spawnX, (int) spawnY);
            homingRocket.aim(targetX, targetY);
            homingRocket.setTower(tower);
            homingRocket.setLifetime(210);
            homingRocket.setDmg(20);
            homingRocket.setTargetCoords(targetX, targetY);
            rocketList.add(homingRocket);
        }
    }

    public void updateProjectile(AEnemy enemy) {

    }

    public void ProjectileHit(AEnemy enemy) {

    }

    public void bulletAim(SpriteBatch batch) {

    }
}
