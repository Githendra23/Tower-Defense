package com.towerdefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.swing.*;
import java.awt.*;

public abstract class Button {
    protected final SpriteBatch batch;
    protected Texture img;
    protected boolean isAlreadyPressed = false;


    public Button(Texture img, int x, int y) {
        this.batch = new SpriteBatch();
        this.img = img;
    }

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

    public abstract void clickEvent();
}
