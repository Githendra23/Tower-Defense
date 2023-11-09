package com.towerdefense.game;

public interface ITower {
    public int getLevel();
    public int getTargetNumber();
    public int getDamage();
    public int getRange();
    public void levelUp(int addDamage);
}