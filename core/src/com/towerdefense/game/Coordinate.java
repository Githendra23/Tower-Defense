package com.towerdefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Coordinate {
    private int axisX;
    private int axisY;

    public int getAxisX() {
        return axisX;
    }

    public int getAxisY() {
        return axisY;
    }

    public void setAxisX(int axisX, TextureRegion img) throws NoSuchGameException {
        if (Gdx.graphics.getWidth() - img.getRegionWidth() < axisX) {
            throw new NoSuchGameException("AxisX higher than window size");
        }
        else if (axisX < 0) {
            throw new NoSuchGameException("AxisX lower than window size");
        }
        else {
          this.axisX = axisX;
        }
    }

    public void setAxisY(int axisY, TextureRegion img) throws NoSuchGameException {
        if (Gdx.graphics.getHeight() - img.getRegionHeight() < axisY) {
            throw new NoSuchGameException("AxisY higher than window size");
        }
        else if (axisY < 0) {
            throw new NoSuchGameException("AxisY lower than window size");
        }
        else {
            this.axisY = axisY;
        }
    }
}