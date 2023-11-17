package com.towerdefense.game.UI;

import com.towerdefense.game.tower.ATower;
import com.towerdefense.game.tower.ArcherTower;
import com.towerdefense.game.tower.Cannon;

public class RocketTurretButton extends TowerButton {
    public RocketTurretButton(int x, int y) {
        super(x, y, "defense/rocket_turret/turret.png", "defense/rocket_turret/rocket_turret_transparent.png");

        this.towerPrice = 20;
    }

    @Override
    public ATower getATower(int x, int y) {
        return new Cannon(x, y);
    }
}
