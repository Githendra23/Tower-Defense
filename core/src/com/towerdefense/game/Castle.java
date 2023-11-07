package com.towerdefense.game;

public class Castle {
    private int hp;

    public Castle(int hp) {
        this.hp = hp;
    }

    public void loseHp(int hp) {
        this.hp -= this.hp - hp == 0 ? 0 : this.hp - hp;
    }
}