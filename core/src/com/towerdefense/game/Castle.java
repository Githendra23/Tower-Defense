package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Castle extends Coordinate {
    private int hp;
    private TextureRegion img;
    private Coordinate coords;

    public Castle(int hp) {
        this.coords = new Coordinate();
        this.img = new TextureRegion(new Texture("towerdefense.png"));

        this.hp = hp;
    }

    public void setCoords(int x, int y) {
        try {
            coords.setAxisX(x, this.img);
            coords.setAxisY(y, img);
        }
        catch (NoSuchGameException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getAxisX() {
        return coords.getAxisX();
    }

    public int getAxisY() {
        return coords.getAxisY();
    }

    public void loseHp(int hp) {
        this.hp -= this.hp - hp == 0 ? 0 : this.hp - hp;
    }

    public int getHp() {
        return this.hp;
    }

    public TextureRegion getImg() {
        return this.img;
    }
}