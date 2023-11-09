package com.towerdefense.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.towerdefense.game.enemy.AEnemy;

public class Zombie extends AEnemy {
    public Zombie(int x, int y) {
        super(100, 10, 1, x, y, "zombie.png");
    }
}
