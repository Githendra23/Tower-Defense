package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;

public class Castle extends Coordinate {
    private int hp;
    private Texture img;
    private Coordinate coords;

    public Castle(int hp) {
        this.coords = new Coordinate();
        this.img = new Texture("towerdefense.png");

        this.hp = hp;
    }

    public void loseHp(int hp) {
        this.hp -= this.hp - hp == 0 ? 0 : this.hp - hp;
    }

    public int getHp() {
        return this.hp;
    }

    public Texture getImg() {
        return this.img;
    }
}