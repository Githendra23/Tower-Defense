package com.towerdefense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Coordinate {
    private int axisX;
    private int axisY;

    public int getAxisX() {
        return axisX;
    }

    public int getAxisY() {
        return axisY;
    }

    public void setAxisX(int axisX, Texture img) throws NoSuchGameException {
        if (Gdx.graphics.getWidth() - img.getWidth() < axisX) {
            throw new NoSuchGameException("AxisX higher than window size");
        }
        else if (axisX < 0) {
            throw new NoSuchGameException("AxisX lower than window size");
        }
        else {
          this.axisX = axisX;
        }
    }

    public void setAxisY(int axisY, Texture img) throws NoSuchGameException {
        if (Gdx.graphics.getHeight() - img.getHeight() < axisY) {
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