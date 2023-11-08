package com.towerdefense.game;

public abstract class ATower implements ITower {
    protected int range;
    protected int damage;
    protected boolean isAreaDamage = false;
    protected int level = 1;
    protected int targetNumber;
    protected Coordinate coords;

    public ATower(int damage, int range) {
        this.damage = damage;
        this.range = range;
    }

    public int getLevel() {
        return this.level;
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

    public int getRange() {
        return this.range;
    }

    public void upgradeDamage() {

    }
    public void upgradeRange() {

    }
    public int getTargetNumber() {
        return this.targetNumber;
    }
    public void setDamage(int damage) {

    }

    public void setRange(int damage) {

    }
}