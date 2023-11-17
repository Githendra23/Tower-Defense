package com.towerdefense.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.towerdefense.game.tower.ATower;

public abstract class TowerButton extends Button {
    protected int towerPrice = 0;
    public TowerButton(int x, int y, String img, String selectedImg) {
        super(x, y, img, selectedImg);
    }

    public boolean isOverlaping(ATower tower) {
        return this.hitbox.overlaps(tower.hitbox());
    }
    TextureRegion drawImg = new TextureRegion(new Texture("white_pixel.png"));
    public void displayHitbox(int x, int y, SpriteBatch batch) {

        hitbox.x = x;
        hitbox.y = y;

        batch.setColor(Color.RED);

        // Draw the borders using lines
        batch.draw(drawImg, x, y, this.selectedImg.getRegionWidth(), 1);
        batch.draw(drawImg, x, y + this.selectedImg.getRegionHeight(), this.selectedImg.getRegionWidth(), 1);
        batch.draw(drawImg, x, y, 1, this.selectedImg.getRegionHeight());
        batch.draw(drawImg, x + this.selectedImg.getRegionWidth(), y, 1, this.selectedImg.getRegionHeight());

        // Reset the color to white
        batch.setColor(Color.WHITE);
    }

    public int getTowerPrice() {
        return this.towerPrice;
    }

    public abstract ATower getATower(int x, int y);
}
