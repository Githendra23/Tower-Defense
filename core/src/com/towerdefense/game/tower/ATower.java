package com.towerdefense.game.tower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.towerdefense.game.Coordinate;
import com.towerdefense.game.NoSuchGameException;
import com.towerdefense.game.enemy.AEnemy;

public abstract class ATower implements ITower {
    protected int range;
    protected int damage;
    protected boolean isAreaDamage = false;
    protected int level = 1;
    protected int targetNumber;
    protected TextureRegion img;
    protected Coordinate coords;
    protected Rectangle hitbox;
    protected ShapeRenderer shapeRenderer;
    protected Circle rangeHitbox;

    public ATower(int damage, int range, int x, int y, String img) {
        this.damage = damage;
        this.range = range;
        this.img = new TextureRegion(new Texture(img));
        this.coords = new Coordinate();

        this.hitbox = new Rectangle(x, y, this.img.getRegionWidth(), this.img.getRegionHeight());
        this.rangeHitbox = new Circle(x + this.img.getRegionWidth() / 2f, y + this.img.getRegionHeight() / 2f, this.range);
        this.shapeRenderer = new ShapeRenderer();

        this.setCoords(x, y);
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
        this.coords.setAxisX(x);
        this.coords.setAxisY(y);

        this.hitbox.x = x;
        this.hitbox.y = y;
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
    public Circle hitRange() {
        return rangeHitbox;
    }

    public void displayHitbox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED); // Set the color of the hitbox
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.end();
    }

    public void displayRangeHitbox() {
        // Draw the border of the circle in red
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(rangeHitbox.x, rangeHitbox.y, rangeHitbox.radius);

        shapeRenderer.end();
    }

    public void attack(AEnemy enemy) {
        enemy.loseHp(this.damage);
    }

    public boolean isInRange(AEnemy enemy) {
        return Intersector.overlaps(this.hitRange(), enemy.hitbox());
    }
}