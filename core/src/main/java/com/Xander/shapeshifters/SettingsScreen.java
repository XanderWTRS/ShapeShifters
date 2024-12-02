package com.Xander.shapeshifters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

        Label musicVolumeLabel = new Label("Music Volume", skin);
        final Slider musicVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
        musicVolumeSlider.setValue(AudioManager.getMusicVolume());
        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getAudioManager().setMusicVolume(musicVolumeSlider.getValue());
            }
        });

        Label soundVolumeLabel = new Label("Sound Volume", skin);
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
}
