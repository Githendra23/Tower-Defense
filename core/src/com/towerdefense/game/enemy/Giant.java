package com.towerdefense.game.enemy;

public class Giant extends AEnemy {
    public Giant(int x, int y, float[] vertices) {
        super(1000, 50, 15, vertices, x, y, "giant/giant.png");

        this.coins = 100;
    }
}