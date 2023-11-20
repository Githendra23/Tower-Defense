package com.towerdefense.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.towerdefense.game.tower.ATower;

public abstract class TowerButton extends Button {
    protected TextureRegion selectedImg;
    protected int towerPrice = 0;
    public TowerButton(int x, int y, String img, String selectedImg) {
        super(x, y, img);
        this.selectedImg = new TextureRegion(new Texture(selectedImg));
    }

    public boolean isOverlaping(ATower tower) {
        return this.hitbox.overlaps(tower.hitbox());
    }

    public void updateHitboxCoords(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public void displayHitbox(SpriteBatch batch) {
        TextureRegion drawImg = new TextureRegion(new Texture("white_pixel.png"));
        int x = (int) hitbox.x;
        int y = (int) hitbox.y;

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
    public TextureRegion getSelectedImg() {
        return this.selectedImg;
    }

    public abstract ATower getATower(int x, int y);

    public void dispose() {
        super.dispose();
        selectedImg.getTexture().dispose();
    }
}
