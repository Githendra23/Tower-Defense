package com.towerdefense.game.UI;

import com.towerdefense.game.tower.ATower;
import com.towerdefense.game.tower.GunTurret;
import com.towerdefense.game.tower.RocketTurret;

public class GunTurretButton extends TowerButton {
    public GunTurretButton(int x, int y) {
        super(x, y, "defense/gun_turret/gun_turret.png", "defense/gun_turret/gun_turret_transparent.png");

        GunTurret cannon = new GunTurret(0, 0);
        this.towerPrice = cannon.getPrice();
        cannon.dispose();
    }

    @Override
    public ATower getATower(int x, int y) {
        return new GunTurret(x, y);
    }
}
