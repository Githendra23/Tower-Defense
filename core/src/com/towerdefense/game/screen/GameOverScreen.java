package com.towerdefense.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.towerdefense.game.TowerDefense;

public class GameOverScreen implements Screen {
    private Texture background;
    private Texture gameover;
    private SpriteBatch batch;
    private final TowerDefense game;

    public GameOverScreen(TowerDefense game) {
        this.game = game;
    }

    @Override
    public void show() {
        background = new Texture("background_img.png");
        gameover = new Texture("gameover.png");
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        int centerWidth = Gdx.graphics.getWidth() / 2;
        int centerHeight = Gdx.graphics.getHeight() / 2;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 1600, 960);
        batch.draw(gameover, centerWidth - ((float) gameover.getWidth() / 2), centerHeight + 50); // Adjust the Y-coordinate to move it higher
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

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        gameover.dispose();
    }
}
