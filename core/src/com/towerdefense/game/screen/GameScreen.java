package com.towerdefense.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.towerdefense.game.Castle;
import com.towerdefense.game.NoSuchGameException;
import com.towerdefense.game.TowerDefense;
import com.towerdefense.game.UI.*;
import com.towerdefense.game.enemy.*;
import com.towerdefense.game.tower.ATower;
import com.towerdefense.game.tower.SniperTower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {
    private final TowerDefense game;
    private Coin coins;
    private double frameCount = 0;
    private SpriteBatch batch;
    private BitmapFont font;
    private final Array<ATower> towers = new Array<ATower>();
    private List<TowerButton> towerButtonList;

    private Castle castle;
    private List<ATower> towerList;
    private List<AEnemy> enemyList;
    private Texture menuPause;
    private PauseMenu pausemenu;
    private Button closeButton;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private MapObjects noTowerZoneObject, enemyPathObject;
    private boolean isTowerPlaceable = false;
    private RectangleMapObject randomRectangleObject;
    private int mouseX, mouseY;

    public GameScreen(final TowerDefense game) {
        this.game = game;
    }

    int count = 1;
    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        coins = new Coin(200, 20, 20);
        castle = new Castle(2000, 1280, 490);
        pausemenu = new PauseMenu();
        menuPause = new Texture("UI/menu.png");
        closeButton = new CloseButton(500, 500);
        towerButtonList = new ArrayList<>();
        towerButtonList.add(new RocketTurretButton(1480, 20));
        towerButtonList.add(new GunTurretButton(1400, 20));
        towerButtonList.add(new SniperTowerButton(1310, 20));

        towerList = new ArrayList<>();
        enemyList = new ArrayList<>();

        // map
        map = new TmxMapLoader().load("map/map.tmx");

        noTowerZoneObject = map.getLayers().get("nonTowerZone").getObjects();
        randomRectangleObject = map.getLayers().get("startPoint").getObjects().getByType(RectangleMapObject.class).first();
        enemyPathObject = map.getLayers().get("enemyPathLayer").getObjects();

        // For Layer
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0); // Center the camera
        camera.update();

        // items, mobs etc...
        pausemenu = new PauseMenu();
        closeButton = new CloseButton(500, 500);

        font = new BitmapFont();
        font.setColor(1, 1, 1, 1); // Set the font color (white in this example)
    }

    @Override
    public void render(float delta) {
        try {
            mouseX = Gdx.input.getX();
            mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            frameCount++;

            // Render the map
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            mapRenderer.setView(camera);
            mapRenderer.render();

            for (ATower tower : towerList) {
                tower.displayRangeHitbox();
            }

            /*for (AEnemy enemy : enemyList) {
                enemy.displayHitbox();
            }*/

            for (ATower tower : towerList) {
                if (tower instanceof SniperTower) {
                    SniperTower sniper = (SniperTower) tower;
                    sniper.sniperLaser();
                }
            }

            batch.begin();

            // bullet.shootAt(Gdx.input.getX() - (((float) img.getHeight()) / 2), -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) / 2)), 20);
            // System.out.println(Gdx.input.isKeyPressed(Input.Keys.A));

            /*
             * if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
             * spawnBullet(10, 10, Gdx.input.getX() - (((float) img.getHeight()) / 2),
             * -Gdx.input.getY() + (Gdx.graphics.getHeight() - (((float) img.getWidth()) /
             * 2)));
             * }
             */

            // display FPS
            font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, Gdx.graphics.getHeight() - 10);

            // display Coordinates of the mouse cursor
            font.draw(batch, "Mouse coords: " + mouseX + "X, " + mouseY + "Y", 10, Gdx.graphics.getHeight() - 30);

            // display mobs
            batch.draw(castle.getImg(), castle.getAxisX(), castle.getAxisY());

            for (AEnemy enemy : enemyList) {
                if (!enemyList.isEmpty()) {
                    /*if (enemy.animation() == null) {
                        batch.draw(enemy.animation(), enemy.getAxisX(), enemy.getAxisY());
                    } else {
                        batch.draw(enemy.getImg(), enemy.getAxisX(), enemy.getAxisY());
                    }*/

                    batch.draw(enemy.animation(), enemy.getAxisX(), enemy.getAxisY());
                }
            }

            for (ATower tower : towerList) {
                batch.draw(tower.animation(), tower.getAxisX(), tower.getAxisY());
                tower.drawProjectile(batch);
            }

            for (TowerButton tower : towerButtonList) {
                batch.draw(tower.getTexture(), tower.getAxisX(), tower.getAxisY());
            }

            batch.draw(coins.animation(), coins.getAxisX(), coins.getAxisY(), coins.getTexture().getWidth() * 3, coins.getTexture().getHeight() * 3);
            font.getData().setScale(2.0f);
            font.draw(batch, coins.getAmount() + "", coins.getAxisX() + 65, coins.getAxisY() + ((float) coins.getTexture().getHeight() * 3) - 20);
            font.getData().setScale(1.0f);

            batch.end();
        }
        catch (NoSuchGameException e) {
            System.out.println(e);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        batch.begin();

        float centerX = Gdx.graphics.getWidth() / 2f - (pausemenu.getAxisX()) / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f - (pausemenu.getAxisY()) / 2f;

        batch.draw(pausemenu.getImg(), centerX, centerY, 730, 370);
        batch.draw(closeButton.getTexture(), 500, 500);

        if (closeButton.isClicked(mouseX, mouseY)) {
            game.resumeGame();
        }

        batch.end();
    }

    @Override
    public void resume() {
        batch.begin();

        if (castle.isDestroyed()) {
            clearGameEntities();
            game.gameOver();
        }

        if (frameCount % 1 == 0) {

            if (frameCount % 120 == 0) {
                if (true) {
                    spawnNewEnemy("Giant");
                }
                count++;
            }

            if (!enemyList.isEmpty()) {
                for (AEnemy enemy : enemyList) {
                    enemy.attack(castle);
                    enemy.move();
                }
            }
        }

        // remove enemy when hp == 0
        for (int i = 0; i < enemyList.size(); i++) {
            AEnemy enemy = enemyList.get(i);

            if (enemy.isDead()) {
                this.coins.updateAmount(enemy.getCoins());
                enemy.dispose();
                enemyList.remove(i);
            }
        }

        for (TowerButton towerButton : towerButtonList) {
            isTowerPlaceable = canPlaceTower(mouseX - ((float) towerButton.getTexture().getRegionWidth() / 2), mouseY + towerButton.getTexture().getRegionHeight()) && canPlaceTower(mouseX - ((float) towerButton.getTexture().getRegionWidth() / 2), mouseY);

            if (towerButton.getIsSetPressed()) {
                batch.draw(towerButton.getSelectedImg(), mouseX - (towerButton.getTexture().getRegionWidth() / 2f), mouseY);
                towerButton.updateHitboxCoords(mouseX - (towerButton.getSelectedImg().getRegionWidth() / 2), mouseY);
                // towerButton.displayHitbox(batch);

                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

                    int count = 0;
                    for (ATower tower : towerList) {
                        if (!(towerButton.isOverlaping(tower))) {
                            count++;
                        }
                    }

                    if (count == towerList.size()) {
                        if (isTowerPlaceable && this.coins.getAmount() - towerButton.getTowerPrice() >= 0) {
                            this.coins.updateAmount(-towerButton.getTowerPrice());

                            towerList.add(towerButton.getATower((int) (mouseX - (towerButton.getTexture().getRegionWidth() / 2f)), mouseY));
                        }
                    }
                }
            }

            if (towerButton.getIsSetPressed() && Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                towerButton.setPressed(false);
            }

            if (towerButton.isClicked(mouseX, mouseY)) {
                towerButton.setPressed(true);
            }
        }

        // towers shooting projectiles
        for (ATower tower : towerList) {
            tower.projectileMove();

            for (int u = enemyList.size() - 1; u >= 0; u--) {
                AEnemy enemy = enemyList.get(u);
                if (tower.isInRange(enemy)) {
                    tower.spawnProjectile(enemy.getAxisX(), enemy.getAxisY());

                    tower.updateProjectile(enemy);
                    tower.ProjectileHit(enemy);
                    tower.projectileAim(enemy);
                }
            }
        }

        batch.end();
    }

    @Override
    public void hide() {

    }

    private boolean isMouseInsideObject(float mouseX, float mouseY) {
        boolean isInside = false;

        for (MapObject object : noTowerZoneObject) {
            if (object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                if (rectangle.contains(mouseX, mouseY)) {
                    isInside = true;
                    break;
                }
            } else if (object instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) object).getPolygon();
                float[] vertices = polygon.getTransformedVertices();
                if (polygonContains(vertices, mouseX, mouseY)) {
                    isInside = true;
                    break;
                }
            }
        }

        return isInside;
    }

    private boolean canPlaceTower(float mouseX, float mouseY) {
        return !isMouseInsideObject(mouseX, mouseY);
    }

    private boolean polygonContains(float[] vertices, float x, float y) {
        int intersects = 0;
        int numVertices = vertices.length / 2;

        for (int i = 0, j = numVertices - 1; i < numVertices; j = i++) {
            float xi = vertices[i * 2];
            float yi = vertices[i * 2 + 1];
            float xj = vertices[j * 2];
            float yj = vertices[j * 2 + 1];

            boolean intersect = ((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
            if (intersect) {
                intersects++;
            }
        }

        return intersects % 2 == 1;
    }

    private void spawnNewEnemy(String enemyName) {
        Rectangle randomRectangle = randomRectangleObject.getRectangle();
        float minX = randomRectangle.x;
        float minY = randomRectangle.y;
        float maxX = randomRectangle.x + randomRectangle.width;
        float maxY = randomRectangle.y + randomRectangle.height;

        int randomX = (int) MathUtils.random(minX, maxX);
        int randomY = (int) MathUtils.random(minY, maxY);

        switch (enemyName) {
            case "Giant":
                Giant giant = new Giant(randomX, randomY, enemyPath());
                giant.setCoords(randomX - (giant.getImg().getRegionWidth() / 2), randomY);
                enemyList.add(giant);
                break;

            case "Zombie":
                Zombie zombie = new Zombie(randomX, randomY, enemyPath());
                zombie.setCoords(randomX - (zombie.getImg().getRegionWidth() / 2), randomY);
                enemyList.add(zombie);
                break;

            case "Slime":
                Slime slime = new Slime(randomX, randomY, enemyPath());
                slime.setCoords(randomX - (slime.getImg().getRegionWidth() / 2), randomY);
                enemyList.add(slime);
                break;

            case "Minotaur":
                Minotaur minotaur = new Minotaur(randomX, randomY, enemyPath());
                minotaur.setCoords(randomX - (minotaur.getImg().getRegionWidth() / 2), randomY);
                enemyList.add(minotaur);
                break;
        }
    }

    private float[] enemyPath() {
        float[] vertices = null;

        for (MapObject object : enemyPathObject) {
            if (object instanceof PolylineMapObject) {
                Polyline polyline = ((PolylineMapObject) object).getPolyline();

                vertices = polyline.getTransformedVertices();
                // The vertices array is in the format [x1, y1, x2, y2, x3, y3, ...]
            }
        }
        return vertices;
    }

    void deleteTower(int index) {
        towerList.remove(index);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        castle.dispose();
        pausemenu.dispose();
        menuPause.dispose();
        closeButton.dispose();
        coins.dispose();

        for (ATower tower : towerList) {
            tower.dispose();
        }

        for (AEnemy enemy : enemyList) {
            enemy.dispose();
        }

        map.dispose();
        mapRenderer.dispose();
    }

    private void clearGameEntities() {
        Iterator<ATower> towerIterator = towerList.iterator();
        while (towerIterator.hasNext()) {
            ATower tower = towerIterator.next();
            tower.dispose();
            towerIterator.remove();
        }

        Iterator<AEnemy> enemyIterator = enemyList.iterator();
        while (enemyIterator.hasNext()) {
            AEnemy enemy = enemyIterator.next();
            enemy.dispose();
            enemyIterator.remove();
        }
    }
}