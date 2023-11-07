package com.towerdefense.game;

public abstract class Tower {
    protected int hp;
    protected int rangeRadius;
    protected int damage;
    protected boolean isAreaDamage = false;
    protected int level = 1;

    public Tower(int hp, int damage, int rangeRadius) {
        this.hp = hp;
        this.damage = damage;
        this.rangeRadius = rangeRadius;
    }

    public int getLevel() {
        return this.level;
    }

    public int getHp() {
        return this.hp;
    }

    public int getDamage() {
        return this.damage;
    }

    public boolean isAreaDamage() {
        return this.isAreaDamage;
    }

    public void levelUp(int addDamage) {
        this.level++;
        this.damage += damage;
    }

    public int getRangeRadius() {
        return this.rangeRadius;
    }
}