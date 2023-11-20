package com.towerdefense.game.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Texture textureSheet;
    private Animation<TextureRegion> animation = null;
    private float elapsedTime = 0f;
    protected int level = 1;
    protected int price = 0;
    protected int targetNumber;
    protected TextureRegion img;
    protected Coordinate coords;
    protected Rectangle hitbox;
    protected ShapeRenderer shapeRenderer;
    protected Circle rangeHitbox;
    protected int coolDown = 0;

    public ATower(int damage, int range, int x, int y, String img) {
        this.damage = damage;
        this.range = range;
        this.img = new TextureRegion(new Texture(img));
        this.coords = new Coordinate();

        this.hitbox = new Rectangle(x, y, this.img.getRegionWidth(), this.img.getRegionHeight());
        this.rangeHitbox = new Circle(x + this.img.getRegionWidth() / 2f, y + this.img.getRegionHeight() / 2f,
                this.range);
        this.shapeRenderer = new ShapeRenderer();

        this.setCoords(x, y);
    }

    public void setLevel(int level) {
        this.level = level;
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

    public void addAnimation(String sheetImg, int tileWidth) {
        textureSheet = new Texture(sheetImg);
        // Define the regions in the texture for each frame of the animation
        TextureRegion[][] textureRegions = TextureRegion.split(textureSheet, tileWidth, textureSheet.getHeight()); // Adjust the size based on your frames

        // Flatten the 2D array into a 1D array for the Animation constructor
        TextureRegion[] animationFrames = textureRegions[0];

        // Create the animation with a frame duration of 0.25 seconds between frames
        animation = new Animation<>(0.1f, animationFrames);
    }

    public TextureRegion animation() throws NoSuchGameException {
        if (animation == null) {
            throw new NoSuchGameException("ATower - no animations added");
        }

        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, false);

        return currentFrame;
    }

    public void attack(AEnemy enemy) {
        enemy.loseHp(this.damage);
    }

    public boolean isInRange(AEnemy enemy) {
        return Intersector.overlaps(this.hitRange(), enemy.hitbox());
    }

    public int getCoolDown() {
        return coolDown;
    }

    public int getPrice() {
        return this.price;
    }

    public abstract void projectileAim(AEnemy enemy);
    public abstract void ProjectileHit(AEnemy enemy);

    public abstract void updateProjectile(AEnemy enemy);
    public abstract void spawnProjectile(int x, int y);
    public abstract void drawProjectile(SpriteBatch batch);
    public abstract void projectileMove();

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