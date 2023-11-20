package com.towerdefense.game.enemy;

public class Slime extends AEnemy {
    public Slime(int x, int y, float[] vertices) {
        super(50, 20, 15, vertices, x, y, "slime.png");

        // this.addAnimation();
        this.coins = 50;
    }
}
