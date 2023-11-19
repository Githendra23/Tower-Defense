package com.towerdefense.game.UI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface IButton {
    public boolean isMouseInside(int mouseX, int mouseY);
    public TextureRegion getTexture();
    public int getAxisX();
    public int getAxisY();
    public void setCoords(int x, int y);
    public boolean isClicked(int mouseX, int mouseY);
    public void setPressed(boolean isSetPressed);
    public boolean getIsSetPressed();
    public void dispose();
}