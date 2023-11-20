package com.towerdefense.game.UI;

import com.towerdefense.game.tower.ATower;
import com.towerdefense.game.tower.RocketTurret;

public class RocketTurretButton extends TowerButton {
    public RocketTurretButton(int x, int y) {
        super(x, y, "defense/rocket_turret/turret.png", "defense/rocket_turret/rocket_turret_transparent.png");

        RocketTurret cannon = new RocketTurret(0, 0);
        this.towerPrice = cannon.getPrice();
        cannon.dispose();
    }

    @Override
    public ATower getATower(int x, int y) {
        return new RocketTurret(x, y);
    }
}
