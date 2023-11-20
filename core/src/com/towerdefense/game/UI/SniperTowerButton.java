package com.towerdefense.game.UI;

import com.towerdefense.game.tower.ATower;
import com.towerdefense.game.tower.RocketTurret;
import com.towerdefense.game.tower.SniperTower;

public class SniperTowerButton extends TowerButton {
    public SniperTowerButton(int x, int y) {
        super(x, y, "defense/sniper_tower/sniper.png", "defense/sniper_tower/sniper_transparent.png");

        SniperTower cannon = new SniperTower(0, 0);
        this.towerPrice = cannon.getPrice();
        cannon.dispose();
    }

    @Override
    public ATower getATower(int x, int y) {
        return new SniperTower(x, y);
    }
}
