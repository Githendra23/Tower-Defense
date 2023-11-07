package com.towerdefense.game;

public abstract class Enemy {
    protected float speed;
    protected int hp;
    protected int damage;
    protected int sizeX;
    protected int sizeY;
    protected int level = 1;
    protected int positionX;
    protected int positionY;
    protected boolean isMelee = true;

    public Enemy(int hp, int damage, float speed) {
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
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

    public float getSpeed() {
        return this.speed;
    }

    public int getLevel() {
        return this.level;
    }

    public void setSize(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public boolean isMelee() {
        return this.isMelee;
    }

    public boolean attack(Object object) {
        return true;
    }

    public void levelUp(int damage) {
        this.level++;
        this.damage += damage;
    }

    public void loseHp(int hp) {
        this.hp -= this.hp - hp == 0 ? 0 : this.hp - hp;
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }
}
