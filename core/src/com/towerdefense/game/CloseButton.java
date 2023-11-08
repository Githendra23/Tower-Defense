package com.towerdefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class CloseButton extends Button {
    private boolean isPressed = false;
    public CloseButton() {
        super(new Texture("close_button.png"));
    }

    @Override
    public void clickEvent() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            isPressed = !isPressed;
        }
    }

    public boolean isPressed() {
        return this.isPressed;
    }

    @Override
    public void render(int x, int y) {
        batch.draw(this.img, x, y);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            this.clickEvent();
        }
    }
}
