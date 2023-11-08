package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;

public class Castle {
    private int hp;
    private Texture img;

    public Castle(int hp, Texture img) {
        this.hp = hp;
        this.img = img;
    }

    public void loseHp(int hp) {
        this.hp -= this.hp - hp == 0 ? 0 : this.hp - hp;
    }
}