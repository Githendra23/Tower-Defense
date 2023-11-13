package com.towerdefense.game.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.towerdefense.game.Castle;
import com.towerdefense.game.Coordinate;
import com.towerdefense.game.NoSuchGameException;

public abstract class AEnemy implements IEnemy {
    protected TextureRegion img;
    protected int speed;
    protected int hp;
    protected int damage;
    protected int level = 1;
    protected boolean isDead = false;
    protected boolean isCloseToCastle = false;
    protected Coordinate coords;
    private Rectangle hitbox;
    private ShapeRenderer shapeRenderer;

    public AEnemy(int hp, int damage, int speed, int x, int y, String img) {
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.img = new TextureRegion(new Texture("enemy/" + img));
        this.hitbox = new Rectangle(x, y, this.img.getRegionWidth(), this.img.getRegionHeight());
        this.shapeRenderer = new ShapeRenderer();

        this.coords = new Coordinate();
        this.setCoords(x, y);
    }

    /*public AEnemy(int hp, int damage, int speed, String img, int height, int width) {
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;

        this.img = new TextureRegion(new Texture("enemy/" + img));
        this.img.setRegionWidth(width);
        this.img.setRegionHeight(height);

        this.coords = new Coordinate();
    }*/

    public int getDamage() {
        return this.damage;
    }

    public int getHp() {
        return this.hp;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getLevel() {
        return this.level;
    }

    public int getAxisX() {
        return coords.getAxisX();
    }

    public int getAxisY() {
        return coords.getAxisY();
    }

    public void setCoords(int x, int y) {
        coords.setAxisX(x);
        coords.setAxisY(y);

        this.hitbox.x = x;
        this.hitbox.y = y;
    }

    public boolean isCloseToCastle() {
        return this.isCloseToCastle;
    }

    public void setCloseToCastle(boolean closeToCastle) {
        this.isCloseToCastle = closeToCastle;
    }

    public void levelUp(int damage) {
        this.level++;
        this.damage += damage;
    }

    public void loseHp(int hp) {
        if (!this.isDead) {
            this.hp -= Math.max(this.hp - hp, 0);

            isDead = this.hp == 0;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public TextureRegion getImg() {
        return img;
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

    public void move(int horizontalDirection, int verticalDirection) {
        setCoords(this.coords.getAxisX() + (this.speed * horizontalDirection), this.coords.getAxisY() + (this.speed * verticalDirection));
    }

    public boolean isInRange(Castle castle) {
        return this.hitbox.overlaps(castle.hitbox());
    }

    public void attack(Castle castle) {
         castle.loseHp(this.damage);
    }
}