package com.Xander.shapeshifters;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
public class AudioManager
{
    private static float musicVolume = 1.0f;
    private static float soundVolume = 1.0f;

    public static void playMusic(Music music, boolean looping) {
        music.setLooping(looping);
        music.setVolume(musicVolume);
        music.play();
    }

    public static void playSound(Sound sound) {
        sound.play(soundVolume);
    }

    public static float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
    }

    public static float getSoundVolume() {
        return soundVolume;
    }

    public void setSoundVolume(float volume) {
        this.soundVolume = volume;
    }
}
