package com.towerdefense.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.towerdefense.game.Coordinate;
import com.badlogic.gdx.math.Rectangle;

public abstract class Button implements IButton {
    protected TextureRegion img;
    protected Coordinate coords;
    protected boolean isSetPressed = false;
    protected Rectangle hitbox;
    protected ShapeRenderer shapeRenderer;


    public Button(int x, int y, String img) {
        this.coords = new Coordinate();
        this.img = new TextureRegion(new Texture(img));
        this.hitbox = new Rectangle(x, y, this.img.getRegionWidth(), this.img.getRegionHeight());
        this.shapeRenderer = new ShapeRenderer();
        this.setCoords(x, y);
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
            this.coords.setAxisX(x);
            this.coords.setAxisY(y);
    }

    public boolean isClicked(int mouseX, int mouseY) {
        return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && this.isMouseInside(mouseX, mouseY);
    }

    public void setPressed(boolean is) {
        this.isSetPressed = is;
    }

    public boolean getIsSetPressed() {
        return this.isSetPressed;
    }

    public void dispose() {
        img.getTexture().dispose();
        shapeRenderer.dispose();
    }
}
