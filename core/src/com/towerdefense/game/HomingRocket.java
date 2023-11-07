package com.towerdefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class HomingRocket extends Projectile
{
    private int speedX=0;
    private int speedY=0;
    public HomingRocket(int positionX, int positionY) {
        super(positionX, positionY, 2,2, new Texture("badlogic.jpg"));
//        homing(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)));
    }

    public Texture drawRocket()
    {
        return img;
    }

    public void homing(float targetX, float targetY){
//        if (speedX<0)speedX++;
//        if (speedX>0)speedX--;
//        if (speedY<0)speedY++;
//        if (speedY>0)speedY--;
        if (positionX<targetX && speedX<100) speedX++;
        if (positionX>targetX && speedX>-100) speedX--;
        if (positionY<targetY&& speedY<100) speedY++;
        if(positionY>targetY&& speedY>-100) speedY--;
        positionX+=speedX/2;
        positionY+=speedY/2;

    }
}
