package com.towerdefense.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.towerdefense.game.tower.ATower;

public abstract class Projectile {
    protected int positionX;
    protected int positionY;
    protected int sizeX;
    protected int sizeY;
    protected int targetX;
    protected int TargetY;
    protected Texture img;
    protected float rotation;
    protected ATower tower;
    protected Rectangle hitbox;
    private ShapeRenderer sr;

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public ATower getTower() {
        return tower;
    }

    public void setTower(ATower tower) {
        this.tower = tower;
    }

    public Projectile(int positionX, int positionY, int sizeX, int sizeY, Texture img)
    {
        this.positionX=positionX;
        this.positionY=positionY;
        this.sizeX=sizeX;
        this.sizeY=sizeY;
        this.img=img;
        this.hitbox = new Rectangle(positionX, positionY, this.img.getWidth(), this.img.getHeight());
        this.sr = new ShapeRenderer();

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
    public void displayHitbox() {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED); // Set the color of the hitbox
        sr.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        sr.end();
    }
}
