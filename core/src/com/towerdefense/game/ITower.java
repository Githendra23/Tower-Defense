package com.towerdefense.game;

public interface ITower {
    public void upgradeDamage();
    public void upgradeRange();
    public int getLevel();
    public int getTargetNumber();
    public void setDamage(int damage);
    public int getDamage();
    public void setRange(int damage);
    public int getRange();
    public void levelUp(int addDamage);
}