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
    protected boolean isAlreadyPressed = false;


    public Button(Texture img) {
        this.batch = new SpriteBatch();
        this.img = img;
    }

    // protected TextureRegion imgRegion;
    //
    // public Button(Texture img, int sizeX, int sizeY) {
    //     batch = new SpriteBatch();
    //     imgRegion = new TextureRegion(img);
    //     imgRegion.setRegionWidth(sizeX);
    //     imgRegion.setRegionHeight(sizeY);
    // }

    public Texture getTexture() {
        return img;
    }

    public void render(int x, int y) {
        batch.draw(this.img, x, y);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !isAlreadyPressed) {
            this.clickEvent();
        }

        isAlreadyPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }

    public int getAxisX() {
        return img.getWidth();
    }

    public int getAxisY() {
        return img.getHeight();
    }

    public abstract void clickEvent();
}
