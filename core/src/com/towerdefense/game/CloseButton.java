package com.towerdefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class CloseButton extends Button {
    public CloseButton(int x, int y) {
        super("close_button.png");

        this.setCoords(x, y);
    }
}
