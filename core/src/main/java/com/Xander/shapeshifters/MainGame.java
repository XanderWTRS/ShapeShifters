package com.Xander.shapeshifters;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game
{
    public SpriteBatch batch;
    private AudioManager audioManager;
    @Override
    public void create()
    {
        batch = new SpriteBatch();
        audioManager = new AudioManager();
        this.setScreen(new MainMenuScreen(this));
    }
    @Override
    public void dispose()
    {
        batch.dispose();
        super.dispose();
    }

    public AudioManager getAudioManager()
    {
        return audioManager;
    }
}
