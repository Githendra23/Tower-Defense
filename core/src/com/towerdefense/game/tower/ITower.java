package com.towerdefense.game.tower;

public interface ITower {
    public int getLevel();
    public int getTargetNumber();
    public int getDamage();
    public int getRange();
    public void levelUp(int addDamage);
    public void dispose();
}