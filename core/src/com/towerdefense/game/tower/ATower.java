package com.towerdefense.game.tower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.towerdefense.game.Coordinate;
import com.towerdefense.game.NoSuchGameException;

public abstract class ATower implements ITower {
    protected int range;
    protected int damage;
    protected boolean isAreaDamage = false;
    protected int level = 1;
    protected int targetNumber;
    protected TextureRegion img;
    protected Coordinate coords;
    private Rectangle hitbox;
    private ShapeRenderer shapeRenderer;

    public ATower(int damage, int range, int x, int y, String img) {
        this.damage = damage;
        this.range = range;
        this.img = new TextureRegion(new Texture(img));
        this.hitbox = new Rectangle(x, y, this.img.getRegionWidth(), this.img.getRegionHeight());
        this.shapeRenderer = new ShapeRenderer();
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

    public void setCoords(int x, int y) {
        try {
            this.coords.setAxisX(x, img);
            this.coords.setAxisY(y, img);

            this.hitbox.x = x;
            this.hitbox.y = y;

        } catch (NoSuchGameException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getAxisX() {
        return coords.getAxisX();
    }

    public int getAxisY() {
        return coords.getAxisY();
    }

    public int getRange() {
        return this.range;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }
    public void addRange(int range) {
        this.range += range;
    }
    public int getTargetNumber() {
        return this.targetNumber;
    }


    public TextureRegion getImg() {
        return this.img;
    }

    public Rectangle hitbox() {
        return this.hitbox;
    }

    public void displayHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED); // Set the color of the hitbox
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.end();
    }
}