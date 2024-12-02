package com.Xander.shapeshifters;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuScreen implements Screen {
    private final MainGame game;
    private Stage stage;
    private Skin skin;
    private BitmapFont titleFont;
    private BitmapFont normalFont;
    private BitmapFont textFont;
    private Texture backgroundTexture;
    private Music mainMenuMusic;

    public MainMenuScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/main_menu_theme.mp3"));
        AudioManager.playMusic(mainMenuMusic, true);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));

        titleFont = generateFont("fonts/TitleFont.ttf", 80);
        normalFont = generateFont("fonts/TitleFont.ttf", 40);
        textFont = generateFont("fonts/NormalFont.ttf", 32);

        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, com.badlogic.gdx.graphics.Color.BLACK);
        Label.LabelStyle bodyStyle = new Label.LabelStyle(normalFont, com.badlogic.gdx.graphics.Color.BLACK);


        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label titleLabel = new Label("ShapeShifters", titleStyle);
        Label subtitleLabel = new Label("Shape of Change", bodyStyle);

        TextButton startButton = new TextButton("Start Game", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });

        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                game.setPreviousScreen(game.getScreen());
                game.setScreen(new SettingsScreen(game));
            }
        });

        TextButton exitButton = new TextButton("Exit Game", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(titleLabel).padTop(100).padBottom(10).row();
        table.add(subtitleLabel).padBottom(90).row();
        table.add(startButton).height(50).width(400).padBottom(15).row();
        table.add(settingsButton).height(50).width(400).padBottom(15).row();
        table.add(exitButton).height(50).width(400).padBottom(15).row();

        stage.addActor(table);
    }

    private BitmapFont generateFont(String fontPath, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Set white background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide()
    {
        if(mainMenuMusic != null)
        {
            mainMenuMusic.stop();
            mainMenuMusic.dispose();
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        titleFont.dispose();
        normalFont.dispose();
        backgroundTexture.dispose();
    }
}
