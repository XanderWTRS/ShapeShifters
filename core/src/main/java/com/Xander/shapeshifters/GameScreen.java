package com.Xander.shapeshifters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import java.util.List;
import java.util.ArrayList;

public class GameScreen implements Screen {

    private final MainGame game;
    private Stage stage;
    private Skin skin;
    private boolean isPaused;
    private Table table;
    private Player player;
    private List<Water> waterBlocks;
    private ShapeRenderer shapeRenderer;
    private FinishPoint finishPoint;

    public GameScreen(MainGame game) {
        this.game = game;
        waterBlocks = new ArrayList<>();
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        player = new Player(100, 100, 50, 50);
        finishPoint = new FinishPoint(1500, 800, 100, 100);

        waterBlocks.add(new Water(500, 500, 100, 100));
        waterBlocks.add(new Water(800, 300, 100, 100));

        table = new Table();
        table.top().left();
        table.setFillParent(true);

        TextButton resumeButton = new TextButton("Resume Game", skin);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resumeGame();
            }
        });

        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //TODO settings
            }
        });

        TextButton mainMenuButton = new TextButton("Back to Main Menu", skin);
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.center();
        table.add(resumeButton).fillX().uniformX().pad(10);
        table.row().pad(10);
        table.add(settingsButton).fillX().uniformX().pad(10);
        table.row().pad(10);
        table.add(mainMenuButton).fillX().uniformX().pad(10);

        table.setVisible(false);
        stage.addActor(table);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (isPaused) {
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        } else {
            player.update(Gdx.graphics.getDeltaTime(), waterBlocks);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            player.render(shapeRenderer);
            finishPoint.render(shapeRenderer);

            for (Water water : waterBlocks) {
                water.render(shapeRenderer);
            }

            shapeRenderer.end();

            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            togglePause();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.switchShape();
        }

        // Check if player reached finish point
        if (finishPoint.checkCollision(player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight())) {
            game.setScreen(new MainMenuScreen(game));
        }

        // Dash input handling
        if (player.getCurrentShape().equals(ShapeType.TRIANGLE) && Gdx.input.isKeyJustPressed(Input.Keys.Q) && player.getDashCooldownTimer() <= 0) {
            // Dash cooldown check
            float dashX = player.getPosition().x;
            float dashY = player.getPosition().y;

            // Calculate new position after dash based on input direction
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                dashX -= player.getDashDistance();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                dashX += player.getDashDistance();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                dashY += player.getDashDistance();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                dashY -= player.getDashDistance();
            }

            // Check for collisions with water before allowing the dash
            boolean canDash = true;
            for (Water water : waterBlocks) {
                if (water.checkCollision(dashX, dashY, player.getWidth(), player.getHeight())) {
                    canDash = false;
                    break;  // Exit loop if collision is detected
                }
            }

            if (canDash) {
                player.dashForward();
                player.startDashCooldown();  // Start cooldown after a successful dash
            }
        }
    }


    private boolean isCollidingWithWater(float newX, float newY) {
        // Check if the new position of the player (after the movement) collides with any water blocks
        for (Water water : waterBlocks) {
            if (water.checkCollision(newX, newY, player.getWidth(), player.getHeight())) {
                return true;  // Collision detected
            }
        }
        return false;  // No collision
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        skin.dispose();
    }

    private void togglePause() {
        isPaused = !isPaused;
        table.setVisible(isPaused);
    }

    private void resumeGame() {
        isPaused = false;
        table.setVisible(false);
    }
}
