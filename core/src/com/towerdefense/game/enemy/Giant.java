package com.towerdefense.game.enemy;

public class Giant extends AEnemy {
    public Giant(int x, int y, float[] vertices) {
        super(1000, 50, 2, vertices, x, y, "giant/giant.png");

        this.coins = 100;
        this.addAnimation("enemy/giant/giant_sheet.png", 64);
    }

    @Override
    public void setCoords(int x, int y) {
        if (isMoving) {
            coords.setAxisX(x);
            coords.setAxisY(y);

            this.hitbox.x = x + 20;
            this.hitbox.y = y;
        }
    }
}