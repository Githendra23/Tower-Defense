package com.towerdefense.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.towerdefense.game.tower.ATower;

public class TowerButton extends Button {
    public TowerButton(int x, int y) {
        super(x, y, "tower/towerdefense.png", "tower/towerdefense_selected.png");
    }

    public boolean isOverlaping(ATower tower) {
        return this.hitbox.overlaps(tower.hitbox());
    }

    public void displayHitbox(int x, int y, SpriteBatch batch) {
        hitbox.x = x;
        hitbox.y = y;

        batch.setColor(Color.RED);

        // Draw the borders using lines
        batch.draw(new TextureRegion(new Texture("white_pixel.png")), x, y, this.selectedImg.getRegionWidth(), 1);
        batch.draw(new TextureRegion(new Texture("white_pixel.png")), x, y + this.selectedImg.getRegionHeight(), this.selectedImg.getRegionWidth(), 1);
        batch.draw(new TextureRegion(new Texture("white_pixel.png")), x, y, 1, this.selectedImg.getRegionHeight());
        batch.draw(new TextureRegion(new Texture("white_pixel.png")), x + this.selectedImg.getRegionWidth(), y, 1, this.selectedImg.getRegionHeight());

        // Reset the color to white
        batch.setColor(Color.WHITE);
    }
}
