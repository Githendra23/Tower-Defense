package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;

public interface IEnemy {
    public int getHp();
    public int getSpeed();
    public int getDamage();

    public int getAxisX();

    public int getAxisY();

    public int getLevel();

    public boolean attack(Object object);

    public void levelUp(int damage);

    public void loseHp(int hp);

    public Texture getImg();
}
