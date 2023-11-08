package com.towerdefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.swing.*;
import java.awt.*;

public abstract class Button {
    protected final SpriteBatch batch;
    protected Texture img;
    protected TextureRegion imgRegion;
    protected Coordinate coords;


    public Button(Texture img) {
        this.batch = new SpriteBatch();
        this.coords = new Coordinate();
        this.img = img;
    }

    public Button(Texture img, int sizeX, int sizeY) {
        batch = new SpriteBatch();
        this.coords = new Coordinate();

        imgRegion = new TextureRegion(img);
        imgRegion.setRegionWidth(sizeX);
        imgRegion.setRegionHeight(sizeY);
    }

    public Texture getTexture() {
        return img;
    }

    public boolean isMouseInside(int mouseX, int mouseY) {
        boolean isMouseX = mouseX >= coords.getAxisX() && mouseX <= coords.getAxisX() + img.getWidth();
        boolean isMouseY = mouseY >= coords.getAxisY() && mouseY <= coords.getAxisY() + img.getHeight();

        return isMouseX && isMouseY;
    }

    public int getAxisX() {
        return coords.getAxisX();
    }

    public int getAxisY() {
        return coords.getAxisY();
    }

    public void setCoords(int x, int y) {
        try {
            this.coords.setAxisX(x, img);
            this.coords.setAxisY(y, img);
        } catch (NoSuchGameException e) {
            System.out.println(e.getMessage());
        }
    }

    public abstract boolean clickEvent(boolean isJustPressed, int mouseX, int mouseY);
}
