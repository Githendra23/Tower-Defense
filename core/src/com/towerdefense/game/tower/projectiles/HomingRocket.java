package com.towerdefense.game.tower.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HomingRocket extends Projectile
{
    TextureRegion region;
    private float speedX=0;
    private float speedY=0;
    protected Texture shadow= new Texture("defense/rocket_turret/rocket_shadow.png");
    public HomingRocket(int positionX, int positionY) {
        super(positionX, positionY, 2,2, new Texture("defense/rocket_turret/rocket.png"));

        region=new TextureRegion(img);
//        homing(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
    }

    public TextureRegion drawRocket()
    {
        return region;
    }
    public TextureRegion drawShadow()
    {
        return new TextureRegion(shadow);
    }

    @Override
    public TextureRegion draw() {
        return drawRocket();
    }

    public void homing(float targetX, float targetY){
        if (speedX<0)speedX+=0.07f;
        if (speedX>0)speedX-=0.07f;
        if (speedY<0)speedY+=0.07f;
        if (speedY>0)speedY-=0.07f;
        if (positionX<targetX) speedX++;
        if (positionX>targetX) speedX--;
        if (positionY<targetY) speedY++;
        if(positionY>targetY) speedY--;
//        if (targetX-positionX>targetY-positionY) speedX=(targetX-positionX)/5;
//        if (targetX-positionX<targetY-positionY) speedY=(targetY-positionY)/5;
        positionX+=speedX;
        positionY+=speedY;
        aim(targetX,targetY);
        hitbox.x=positionX;
        hitbox.y=positionY;
    }

    @Override
    public void dispose() {
        super.dispose(); // Dispose of the resources in the superclass

        // Dispose of any additional resources used by HomingRocket
        shadow.dispose();
    }
}