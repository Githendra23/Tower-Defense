package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;

public class Cannon extends ATower {
    protected Texture img = new Texture("turret.png");
    public Cannon() {
        super(200, 200);
    }
}
