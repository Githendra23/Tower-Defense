package com.towerdefense.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HomingRocket extends Projectile
{
    TextureRegion region;
    private float speedX=0;
    private float speedY=0;
    public HomingRocket(int positionX, int positionY) {
        super(positionX, positionY, 2,2, new Texture("rocket.png"));
        region=new TextureRegion(img);
//        homing(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
    }

    public TextureRegion drawRocket()
    {
        return region;
    }

    @Override
    public TextureRegion draw() {
        return drawRocket();
    }

    public void homing(float targetX, float targetY){
        if (speedX<0)speedX+=0.05f;
        if (speedX>0)speedX-=0.05f;
        if (speedY<0)speedY+=0.05f;
        if (speedY>0)speedY-=0.05f;
        if (positionX<targetX) speedX++;
        if (positionX>targetX) speedX--;
        if (positionY<targetY) speedY++;
        if(positionY>targetY) speedY--;
//        if (targetX-positionX>targetY-positionY) speedX=(targetX-positionX)/5;
//        if (targetX-positionX<targetY-positionY) speedY=(targetY-positionY)/5;
        positionX+=speedX;
        positionY+=speedY;
        aim(targetX,targetY);
    }
}
