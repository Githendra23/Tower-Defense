package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public abstract class Projectile {
    protected int positionX;
    protected int positionY;
    protected int sizeX;
    protected int sizeY;
    protected int targetX;
    protected int TargetY;
    protected Texture img;
    protected float rotation;

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
    public TextureRegion draw()
    {
        return null;
    }
    public float aim(float targetX,float targetY)
    {
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(targetY - positionY, targetX - positionX);
        rotation=angle;
        return angle;
    }
}
