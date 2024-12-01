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

public class GameScreen implements Screen {

    private final MainGame game;
    private Stage stage;
    private Skin skin;
    private boolean isPaused;
    private Table table;
    private Player player;

    private ShapeRenderer shapeRenderer;

    public GameScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        player = new Player(100, 100, 50, 50);

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
            player.update(Gdx.graphics.getDeltaTime());

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED); //COLOR of CHARACTER
            player.render(shapeRenderer);
            shapeRenderer.end();

            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            togglePause();
        }
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
