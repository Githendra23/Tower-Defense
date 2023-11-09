package com.towerdefense.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.towerdefense.game.Coordinate;
import com.towerdefense.game.NoSuchGameException;

import javax.swing.*;
import java.awt.*;

public abstract class Button {
    protected TextureRegion img;
    protected TextureRegion selectedImg;
    protected Coordinate coords;
    protected boolean isSetPressed = false;


    public Button(String img) {
        this.coords = new Coordinate();
        this.img = new TextureRegion(new Texture(img));
    }

    public TextureRegion getTexture() {
        return img;
    }

    public boolean isMouseInside(int mouseX, int mouseY) {
        boolean isMouseX = mouseX >= coords.getAxisX() && mouseX <= coords.getAxisX() + img.getRegionWidth();
        boolean isMouseY = mouseY >= coords.getAxisY() && mouseY <= coords.getAxisY() + img.getRegionHeight();

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

    public boolean isClicked(int mouseX, int mouseY) {
        return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && this.isMouseInside(mouseX, mouseY);
    }

    public TextureRegion getSelectedImg() {
        return this.selectedImg;
    }

    public void setPressed(boolean is) {
        this.isSetPressed = is;
    }

    public boolean getIsSetPressed() {
        return this.isSetPressed;
    }
}
