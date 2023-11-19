package com.towerdefense.game.UI;

import com.towerdefense.game.tower.ATower;
import com.towerdefense.game.tower.GunTurret;

public class GunTurretButton extends TowerButton {
    public GunTurretButton(int x, int y) {
        super(x, y, "defense/gun_turret/gun_turret.png", "defense/gun_turret/gun_turret_transparent.png");
    }

    @Override
    public ATower getATower(int x, int y) {
        return new GunTurret(x, y);
    }
}
