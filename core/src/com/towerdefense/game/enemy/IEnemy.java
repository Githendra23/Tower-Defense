package com.towerdefense.game.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.towerdefense.game.Castle;

public interface IEnemy {
    public int getHp();
    public int getSpeed();
    public int getDamage();

    public int getAxisX();

    public int getAxisY();

    public int getLevel();

    public void attack(Castle castle);

    public void levelUp(int damage);

    public void loseHp(int hp);

    public TextureRegion getImg();

    public void dispose();
}