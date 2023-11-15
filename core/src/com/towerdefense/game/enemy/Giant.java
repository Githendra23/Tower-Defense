package com.towerdefense.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.towerdefense.game.enemy.AEnemy;

import java.util.List;

public class Giant extends AEnemy {
    public Giant(int x, int y, float[] vertices) {
        super(1000, 200, 15, vertices, x, y, "giant.png");
    }
}