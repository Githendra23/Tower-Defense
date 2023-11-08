package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;

public abstract class AEnemy implements IEnemy {
    protected Texture img;
    protected int speed;
    protected int hp;
    protected int damage;
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

    public int getSpeed() {
        return this.speed;
    }

    public int getLevel() {
        return this.level;
    }

    public int getAxisX() {
        return coords.getAxisX();
    }

    public int getAxisY() {
        return coords.getAxisY();
    }

    public void setAxisX(int axisX) throws NoSuchGameException {
        try {
            coords.setAxisX(axisX, this.img);
        } catch (NoSuchGameException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setAxisY(int axisY) throws NoSuchGameException {
        try {
            coords.setAxisY(axisY, this.img);
        } catch (NoSuchGameException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean attack(Object object) {
        return isCloseToCastle;
    }

    public boolean isCloseToCastle() {
        return this.isCloseToCastle;
    }

    public void setCloseToCastle(boolean closeToCastle) {
        this.isCloseToCastle = closeToCastle;
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
