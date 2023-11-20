package com.towerdefense.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.towerdefense.game.Coordinate;

public class Coin {
    private Coordinate coords;
    private Texture img;
    private Texture textureSheet;
    private Animation<TextureRegion> animation = null;
    private float elapsedTime = 0f;
    private int amount = 0;

    public Coin(int amount, int x, int y) {
        this.amount = amount;
        this.coords = new Coordinate();
        this.coords.setAxisX(x);
        this.coords.setAxisY(y);
        this.img = new Texture("coin.png");
        this.addAnimation("coin_sheet.png", this.img.getWidth());
    }

    public void updateAmount(int amount) {
        this.amount += amount;
    }

    private void addAnimation(String sheetImg, int tileWidth) {
        textureSheet = new Texture(sheetImg);
        // Define the regions in the texture for each frame of the animation
        TextureRegion[][] textureRegions = TextureRegion.split(textureSheet, tileWidth, textureSheet.getHeight()); // Adjust the size based on your frames

        // Flatten the 2D array into a 1D array for the Animation constructor
        TextureRegion[] animationFrames = textureRegions[0];

        // Create the animation with a frame duration of 0.25 seconds between frames
        animation = new Animation<>(0.1f, animationFrames);
    }

    public TextureRegion animation() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, true);

        return currentFrame;
    }

    public Texture getTexture() {
        return this.img;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getAxisX() {
        return coords.getAxisX();
    }

    public int getAxisY() {
        return coords.getAxisY();
    }

    public void dispose() {
        img.dispose();
        textureSheet.dispose();

        if (animation != null) {
            for (TextureRegion region : animation.getKeyFrames()) {
                region.getTexture().dispose();
            }
        }
    }
}
