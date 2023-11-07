package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;

import javax.swing.text.Position;

public abstract class Projectile {
    protected int positionX;
    protected int positionY;
    protected int sizeX;
    protected int sizeY;
    protected int targetX;
    protected int TargetY;
    protected Texture img;

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
    public Projectile(int positionX, int positionY,int sizeX, int sizeY, Texture img)
    {
        this.positionX=positionX;
        this.positionY=positionY;
        this.sizeX=sizeX;
        this.sizeY=sizeY;
        this.img=img;

    }
}
