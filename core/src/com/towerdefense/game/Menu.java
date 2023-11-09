package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Menu {
    protected TextureRegion img;
    protected Coordinate coords;

    public Menu(String img) {
        this.img = new TextureRegion(new Texture(img));
        coords = new Coordinate();
    }

    public void setCoords(int x, int y) {
        try {
            coords.setAxisX(x, this.img);
            coords.setAxisY(y, this.img);

        } catch (NoSuchGameException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getAxisX() {
        return coords.getAxisX();
    }

    public int getAxisY() {
        return coords.getAxisY();
    }

    public TextureRegion getImg() {
        return this.img;
    }
}
