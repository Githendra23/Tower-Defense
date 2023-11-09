package com.towerdefense.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.towerdefense.game.enemy.AEnemy;

public class Zombie extends AEnemy {
    public Zombie() {
        super(100, 10, 1, new Texture("zombie.png"));
    }
}
