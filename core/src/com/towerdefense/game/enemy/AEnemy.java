package com.towerdefense.game.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.towerdefense.game.Castle;
import com.towerdefense.game.Coordinate;
import com.towerdefense.game.NoSuchGameException;

import java.util.Arrays;
import java.util.List;

public abstract class AEnemy implements IEnemy {
    private float attackTimer;
    private static float ATTACK_INTERVAL = 1.0f; // 1 second interval
    private final int RIGHT = 1, LEFT = -1, UP = 1, DOWN = -1, STAY = 0;
    protected TextureRegion img;
    private Texture textureSheet, deadtextureSheet;
    private Animation<TextureRegion> animation = null, deadAnimation = null;
    private float elapsedTime = 0f, deadElapsedTime = 0f;
    protected int speed;
    protected int hp;
    protected int damage;
    protected int level = 1;
    protected boolean isDead = false;
    protected boolean isMoving = true;
    protected Coordinate coords;
    protected float[] vertices; // The vertices array is in the format [x1, y1, x2, y2, x3, y3, ...]
    protected Rectangle hitbox;
    private ShapeRenderer shapeRenderer;
    private int points = 0;
    protected int coins = 0;
    private final int distanceBetweenPoint;

    public AEnemy(int hp, int damage, int speed, float[] vertices, int x, int y, String img) {
        this.hp = hp;
        this.damage = damage;
        this.speed = speed;
        this.img = new TextureRegion(new Texture("enemy/" + img));
        this.hitbox = new Rectangle(x, y, this.img.getRegionWidth(), this.img.getRegionHeight());
        this.shapeRenderer = new ShapeRenderer();
        this.vertices = vertices;
        this.distanceBetweenPoint = y - (int) vertices[1];
        this.attackTimer = 0;

        this.coords = new Coordinate();
        this.setCoords(x, y);
    }

    /*
     * public AEnemy(int hp, int damage, int speed, String img, int height, int
     * width) {
     * this.hp = hp;
     * this.damage = damage;
     * this.speed = speed;
     * 
     * this.img = new TextureRegion(new Texture("enemy/" + img));
     * this.img.setRegionWidth(width);
     * this.img.setRegionHeight(height);
     * 
     * this.coords = new Coordinate();
     * }
     */

    public int getDamage() {
        return this.damage;
    }
    public void addAnimation(String sheetImg, int tileWidth) {
        textureSheet = new Texture(sheetImg);
        // Define the regions in the texture for each frame of the animation
        TextureRegion[][] textureRegions = TextureRegion.split(textureSheet, tileWidth, textureSheet.getHeight()); // Adjust the size based on your frames

        // Flatten the 2D array into a 1D array for the Animation constructor
        TextureRegion[] animationFrames = textureRegions[0];

        // Create the animation with a frame duration of 0.25 seconds between frames
        animation = new Animation<>(0.1f, animationFrames);
    }

    public void addDeadAnimation(String sheetImg, int tileWidth) {
        deadtextureSheet = new Texture(sheetImg);

        // Define the regions in the texture for each frame of the animation
        TextureRegion[][] textureRegions = TextureRegion.split(deadtextureSheet, tileWidth, deadtextureSheet.getHeight()); // Adjust the size based on your frames

        // Flatten the 2D array into a 1D array for the Animation constructor
        TextureRegion[] animationFrames = textureRegions[0];

        // Create the animation with a frame duration of 0.25 seconds between frames
        deadAnimation = new Animation<>(0.1f, animationFrames);
    }

    public TextureRegion animation() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, true);

        return currentFrame;
    }

    public TextureRegion deadAnimation() {
        deadElapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = deadAnimation.getKeyFrame(deadElapsedTime, true);

        return currentFrame;
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
        if (isMoving) {
            coords.setAxisX(x);
            coords.setAxisY(y);

            this.hitbox.x = x;
            this.hitbox.y = y;
        }
    }

    public boolean isMoving() {
        return this.isMoving;
    }

    public void levelUp(int damage) {
        this.level++;
        this.damage += damage;
    }

    public void loseHp(int damage) {
        if (!this.isDead) {
            this.hp = Math.max(this.hp - damage, 0);

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

    public void move() {
        if (isMoving) {
            int x2 = (int) vertices[points + 2];
            int y2 = (int) vertices[points + 3];

            float angle = (float) Math.atan2(y2 - this.getAxisY(), x2 - this.getAxisX());
            int deltaX = (int) (this.speed * Math.cos(angle));
            int deltaY = (int) (this.speed * Math.sin(angle));

            // Move the object
            this.setCoords(this.getAxisX() + deltaX, this.getAxisY() + deltaY);

            // Check if the object has reached the next point
            if (Math.abs(this.getAxisX() - x2) <= speed && Math.abs(this.getAxisY() - y2) <= speed) {
                points += 2;

                // Check if there is a next point
                if (points + 2 >= vertices.length) {
                    isMoving = false;
                }
            }
        }
    }

    public boolean isInRange(Castle castle) {
        return this.hitbox.overlaps(castle.hitbox());
    }

    public void attack(Castle castle) {
        if (isInRange(castle)) {
            this.isMoving = !this.hitbox.overlaps(castle.hitbox());
            attackTimer += Gdx.graphics.getDeltaTime();

            // Check if it's time to attack
            if (attackTimer >= ATTACK_INTERVAL) {
                castle.loseHp(this.damage);
                attackTimer = 0;
            }
        }
    }

    public int getCoins() {
        return this.coins;
    }

    /*public void dispose() {
        enemyTexture.dispose();
    }*/

    public float[] getVertices() {
        return this.vertices;
    }

    public void setAttackDelay(float delay) {
        ATTACK_INTERVAL = delay;
    }

    public void dispose() {
        img.getTexture().dispose();
        shapeRenderer.dispose();

        if (textureSheet != null) {
            textureSheet.dispose();
        }

        if (animation != null) {
            for (TextureRegion region : animation.getKeyFrames()) {
                region.getTexture().dispose();
            }
        }
    }
}