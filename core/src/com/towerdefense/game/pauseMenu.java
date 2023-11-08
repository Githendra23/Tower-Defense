package com.towerdefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class pauseMenu extends Menu {
    private final Button closeButton;
    private boolean isPaused = false;

    public pauseMenu() {
        super(new Texture("menu.png"));

        this.closeButton = new CloseButton();
    }

    @Override
    public void render(int x, int y) {
        batch.draw(this.img, x, y);
        this.closeButton.render(100, 100);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

        }
    }
}
