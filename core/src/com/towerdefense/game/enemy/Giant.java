package com.towerdefense.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.towerdefense.game.enemy.AEnemy;

public class Giant extends AEnemy {
    public Giant(int x, int y) {
        super(1000, 200, 1, x, y, "giant.png");
    }
}