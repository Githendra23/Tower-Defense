package com.towerdefense.game.enemy;

public class Slime extends AEnemy {
    public Slime(int x, int y, float[] vertices) {
        super(50, 20, 3, vertices, x, y, "slime/slime.png");

        this.addAnimation("enemy/slime/D_Walk.png", 36);
        this.coins = 50;
    }

    @Override
    public void setCoords(int x, int y) {
        if (isMoving) {
            coords.setAxisX(x);
            coords.setAxisY(y);

            this.hitbox.x = x + 4;
            this.hitbox.y = y;
        }
    }
}
