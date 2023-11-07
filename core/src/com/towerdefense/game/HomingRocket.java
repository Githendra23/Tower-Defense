package com.towerdefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class HomingRocket extends Projectile
{
    private float speedX=0;
    private float speedY=0;
    public HomingRocket(int positionX, int positionY) {
        super(positionX, positionY, 2,2, new Texture("badlogic.jpg"));
//        homing(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
    }

    public Texture drawRocket()
    {
        return img;
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

    }
}
