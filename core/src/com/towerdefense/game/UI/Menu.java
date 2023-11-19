package com.towerdefense.game.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.towerdefense.game.Coordinate;
import com.towerdefense.game.NoSuchGameException;

public abstract class Menu {
    protected TextureRegion img;
    protected Coordinate coords;

    public Menu(String img) {
        this.img = new TextureRegion(new Texture(img));
        coords = new Coordinate();
    }

    public void setCoords(int x, int y) {
            coords.setAxisX(x);
            coords.setAxisY(y);
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

    public void dispose() {
        img.getTexture().dispose();
    }
}
