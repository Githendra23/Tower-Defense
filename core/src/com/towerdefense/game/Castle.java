package com.towerdefense.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Castle extends Coordinate {
    private int hp;
    private TextureRegion img;
    private Coordinate coords;
    private Rectangle hitbox;
    private ShapeRenderer shapeRenderer;
    private boolean isDestroyed;

    public Castle(int hp, int x, int y) {
        this.coords = new Coordinate();
        this.hitbox = new Rectangle();
        this.shapeRenderer = new ShapeRenderer();
        this.img = new TextureRegion(new Texture("castle1.png"));
        this.hitbox = new Rectangle(x, y, this.img.getRegionWidth(), this.img.getRegionHeight());

        this.setCoords(x, y);
        this.hp = hp;
    }

    public void setCoords(int x, int y) {
        coords.setAxisX(x);
        coords.setAxisY(y);

        this.hitbox.x = x;
        this.hitbox.y = y;
    }

    public int getAxisX() {
        return coords.getAxisX();
    }

    public int getAxisY() {
        return coords.getAxisY();
    }

    public void loseHp(int damage) {
        if (!isDestroyed) {
            this.hp = Math.max(this.hp - damage, 0);
            isDestroyed = this.hp == 0;
        }
    }

    public int getHp() {
        return this.hp;
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

    public boolean isDestroyed() {
        return this.hp == 0;
    }

    public void dispose() {
        img.getTexture().dispose();
        shapeRenderer.dispose();
    }
}