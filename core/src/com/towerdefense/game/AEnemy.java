package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;

public abstract class AEnemy implements IEnemy {
    protected Texture img;
    protected int speed;
    protected int hp;
    protected int damage;
    protected int sizeX;
    protected int sizeY;
    protected int level = 1;
    protected boolean isDead = false;
    protected boolean isCloseToCastle = false;
    protected Coordinate coords;

    public AEnemy(int hp, int damage, int speed, Texture img) {
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.img = img;

        this.coords = new Coordinate();
    }

    public int getDamage() {
        return this.damage;
    }

    public int getHp() {
        return this.hp;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getLevel() {
        return this.level;
    }

    public void setSize(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public boolean attack(Object object) {
        return isCloseToCastle;
    }

    public void levelUp(int damage) {
        this.level++;
        this.damage += damage;
    }

    public void loseHp(int hp) {
        if (!this.isDead) {
            this.hp -= this.hp - hp == 0 ? 0 : this.hp - hp;

            isDead = this.hp == 0;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public Texture getImg() {
        return img;
    }
}
