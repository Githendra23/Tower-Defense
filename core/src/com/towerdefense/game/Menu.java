package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Menu {
    protected Texture img;
    protected SpriteBatch batch;

    public Menu(Texture img) {
        this.img = img;
        this.batch = new SpriteBatch();
    }

    public void render(int x, int y) {
        batch.draw(this.img, x, y);
    }

    public int getAxisX() {
        return img.getWidth();
    }

    public int getAxisY() {
        return img.getHeight();
    }

    public Texture getImg() {
        return this.img;
    }
}
