
package com.thegreystudios.pixeltower.sfx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundLibrary
{

    public SoundLibrary()
    {
    }

    public static void load()
    {
        backgroundMusic = Gdx.app.getAudio().newMusic(Gdx.files.internal("sfx/backgroundLoop.ogg"));
        gameOverMusic = Gdx.app.getAudio().newMusic(Gdx.files.internal("sfx/gameOverLoop.ogg"));
        backgroundMusic.setLooping(true);
        gameOverMusic.setLooping(true);
        tileHitSound = Gdx.app.getAudio().newSound(Gdx.files.internal("sfx/tileHit.ogg"));
        tileCrashSound = Gdx.app.getAudio().newSound(Gdx.files.internal("sfx/tileCrash.ogg"));
        boniSound = Gdx.app.getAudio().newSound(Gdx.files.internal("sfx/boni.ogg"));
        selectSound = Gdx.app.getAudio().newSound(Gdx.files.internal("sfx/select.ogg"));
        gameOverSound = Gdx.app.getAudio().newSound(Gdx.files.internal("sfx/gameOverFx.ogg"));
        ufoSound = Gdx.app.getAudio().newSound(Gdx.files.internal("sfx/ufo.ogg"));
    }

    public static void MainGame()
    {
        backgroundMusic.stop();
        backgroundMusic.play();
        gameOverMusic.stop();
    }

    public static void Menu()
    {
        backgroundMusic.stop();
        gameOverMusic.stop();
    }

    public static void GameOver()
    {
        backgroundMusic.stop();
        gameOverMusic.stop();
        gameOverMusic.play();
    }

    public static Music backgroundMusic;
    public static Music gameOverMusic;
    public static Sound tileHitSound;
    public static Sound tileCrashSound;
    public static Sound boniSound;
    public static Sound selectSound;
    public static Sound gameOverSound;
    public static Sound ufoSound;
}
