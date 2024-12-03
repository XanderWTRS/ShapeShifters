package com.Xander.shapeshifters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen implements Screen
{
    private final MainGame game;
    private Stage stage;
    private Skin skin;
    private BitmapFont titleFont;
    private BitmapFont extraTitleFont;

    public SettingsScreen(MainGame game)
    {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        titleFont = generateFont("fonts/TitleFont.ttf", 30);
        extraTitleFont = generateFont("fonts/TitleFont.ttf", 80);
        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.BLACK);
        Label.LabelStyle extraTitleStyle = new Label.LabelStyle(extraTitleFont, Color.BLACK);

        Label titleLabel = new Label("Settings", extraTitleStyle);

        Label musicVolumeLabel = new Label("Music Volume", titleStyle);
        final Slider musicVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        musicVolumeSlider.setValue(AudioManager.getMusicVolume());
        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getAudioManager().setMusicVolume(musicVolumeSlider.getValue());
            }
        });

        Label soundVolumeLabel = new Label("Sound Volume", titleStyle);
        final Slider soundVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        soundVolumeSlider.setValue(AudioManager.getSoundVolume());
        soundVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getAudioManager().setSoundVolume(soundVolumeSlider.getValue());
            }
        });

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getPreviousScreen() instanceof GameScreen)
                {
                    ((GameScreen) game.getPreviousScreen()).togglePause();
                }
                game.setScreen(game.getPreviousScreen());
            }
        });

        table.add(titleLabel).pad(10).padBottom(120).padTop(-100);
        table.row();
        table.add(musicVolumeLabel).pad(10);
        table.row();
        table.add(musicVolumeSlider).width(300).pad(10);
        table.row();
        table.add(soundVolumeLabel).pad(10);
        table.row();
        table.add(soundVolumeSlider).width(300).pad(10);
        table.row();
        table.add(backButton).pad(20);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    private BitmapFont generateFont(String fontPath, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }
}
