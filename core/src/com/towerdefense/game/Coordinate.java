package com.towerdefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Coordinate {
    private float axisX;
    private float axisY;

    public int getAxisX() {
        return (int) axisX;
    }

    public int getAxisY() {
        return (int) axisY;
    }

    public float getFAxisX() {
        return this.axisX;
    }

    public float getFAxisY() {
        return this.axisY;
    }

    public void setAxisX(int axisX) {
        this.axisX = axisX;
    }

    public void setAxisY(int axisY) {
        this.axisY = axisY;
    }

    public void setFAxisX(float axisX) {
        this.axisX = axisX;
    }

    public void setFAxisY(float axisY) {
        this.axisY = axisY;
    }
}