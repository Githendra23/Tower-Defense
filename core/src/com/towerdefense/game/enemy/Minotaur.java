package com.towerdefense.game.enemy;

public class Minotaur extends AEnemy {
    public Minotaur(int x, int y, float[] vertices) {
        super(300, 100, 2, vertices, x, y, "minotaur/minotaur.png");

        this.addAnimation("enemy/minotaur/minotaur_sheet.png", 96);
        this.coins = 400;
    }

    @Override
    public void setCoords(int x, int y) {
        if (isMoving) {
            coords.setAxisX(x);
            coords.setAxisY(y);

            this.hitbox.x = x + 28;
            this.hitbox.y = y;
        }
    }
}
