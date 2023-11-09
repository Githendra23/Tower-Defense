package com.towerdefense.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TowerButton extends Button {
    public TowerButton(int x, int y) {
        super("tower/towerdefense.png");
        this.setCoords(x, y);

        this.selectedImg = new TextureRegion(new Texture("tower/towerdefense_selected.png"));
    }
}
