package com.towerdefense.game.enemy;

public class Zombie extends AEnemy {
    public Zombie(int x, int y, float[] vertices) {
        super(100, 10, 5, vertices, x, y, "zombie/zombie.png");

        this.coins = 25;
    }
}