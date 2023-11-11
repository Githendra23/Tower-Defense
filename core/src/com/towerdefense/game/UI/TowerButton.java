package com.towerdefense.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TowerButton extends Button {
    public TowerButton(int x, int y) {
        super(x, y, "tower/towerdefense.png", "tower/towerdefense_selected.png");
    }
}
