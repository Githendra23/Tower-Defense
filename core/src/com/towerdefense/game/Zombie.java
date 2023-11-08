package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;

public class Zombie extends AEnemy {
    public Zombie() {
        super(100, 10, 1, new Texture("zombie.png"));
    }

    public Texture drawZombie() {
        return this.getImg();
    }
}
