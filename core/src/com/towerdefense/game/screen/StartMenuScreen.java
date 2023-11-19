package com.towerdefense.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.towerdefense.game.TowerDefense;
import com.towerdefense.game.UI.StartButton;

public class StartMenuScreen implements Screen {
    private final TowerDefense game;
    private final SpriteBatch batch;
    private final Texture background;
    private StartButton startButton;
    private int mouseX, mouseY;

    public StartMenuScreen(TowerDefense game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.background = new Texture("background_img.png");
    }

    @Override
    public void show() {
        int centerWidth = Gdx.graphics.getWidth() / 2;
        int centerHeight = Gdx.graphics.getHeight() / 2;

        startButton = new StartButton(0, 0);
        startButton.setCoords(centerWidth - (startButton.getTexture().getRegionWidth() / 2), centerHeight - (startButton.getTexture().getRegionHeight() / 2));
    }

    @Override
    public void render(float delta) {
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 1600, 960);
        batch.draw(startButton.getTexture(), startButton.getAxisX(), startButton.getAxisY());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        if (startButton.isClicked(mouseX, mouseY)) {
            game.startGame();
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        batch.dispose();
        startButton.dispose();
    }
}
