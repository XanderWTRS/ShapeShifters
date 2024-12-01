package com.Xander.shapeshifters;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainMenuScreen implements Screen
{
    private Stage stage;
    private Skin skin;
    private MainGame game;

    public MainMenuScreen(MainGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label titleLabel = new Label("ShapeShifters", skin);
        titleLabel.setFontScale(2);

        Label subtitleLabel = new Label("Shape of Change", skin);
        subtitleLabel.setFontScale(1.5f);

        TextButton startButton = new TextButton("Start Game", skin);
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton exitButton = new TextButton("Exit Game", skin);

        startButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                game.setScreen(new GameScreen(game));
            }
        });

        settingsButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                System.out.println("Settings clicked");
            }
        });

        exitButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                Gdx.app.exit();  // Exit the game
            }
        });

        Table table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.padTop(100);

        table.add(titleLabel).colspan(2).center().padBottom(20).row();
        table.add(subtitleLabel).colspan(2).center().padBottom(40).row();
        table.add(startButton).width(200).padBottom(20).row();
        table.add(settingsButton).width(200).padBottom(20).row();
        table.add(exitButton).width(200).padBottom(20).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide()
    {
        stage.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose()
    {
        skin.dispose();
    }
}
