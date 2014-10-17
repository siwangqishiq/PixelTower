
package com.thegreystudios.pixeltower;

import com.badlogic.gdx.*;
import com.thegreystudios.pixeltower.screen.OverlayScreen;
import com.thegreystudios.pixeltower.screen.Screen;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;

public abstract class Game
    implements ApplicationListener
{

    public Game()
    {
    }

    public void setScreen(Screen screen)
    {
        if(this.screen != null)
        {
            this.screen.pause();
            this.screen.dispose();
        }
        this.screen = screen;
        if(InputProcessor.class.isInstance(this.screen))
            Gdx.input.setInputProcessor((InputProcessor)this.screen);
    }

    public abstract Screen getStartScreen();

    public void create()
    {
        setScreen(getStartScreen());
    }

    public void render()
    {
        screen.present(Gdx.graphics.getDeltaTime());
        screen.update(Gdx.graphics.getDeltaTime());
        overlay.update(Gdx.graphics.getDeltaTime());
        overlay.present(Gdx.graphics.getDeltaTime());
    }

    public void resume()
    {
        screen.resume();
    }

    public void pause()
    {
        screen.pause();
        if(SoundLibrary.backgroundMusic != null)
            SoundLibrary.backgroundMusic.pause();
        if(SoundLibrary.gameOverMusic != null)
            SoundLibrary.gameOverMusic.pause();
    }

    public void resize(int i, int j)
    {
    }

    public void dispose()
    {
        screen.dispose();
    }

    Screen screen;
    OverlayScreen overlay;
}
