
package com.thegreystudios.pixeltower.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector3;
import com.thegreystudios.pixeltower.PixelTower;
import com.thegreystudios.pixeltower.ai.AILibrary;
import com.thegreystudios.pixeltower.renderers.WorldRenderer;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;
import com.thegreystudios.pixeltower.status.LocalStorage;
import com.thegreystudios.pixeltower.ui.TextButton;

public class MenuScreen extends PopupScreen
    implements InputProcessor
{

    public MenuScreen(PixelTower pixelTower, Screen parentScreen, WorldRenderer renderer)
    {
        super(pixelTower, parentScreen, true);
        buttonUp = new Color(1.0F, 0.9568627F, 0.1568628F, 1.0F);
        buttonDown = new Color(1.0F, 1.0F, 0.509804F, 1.0F);
        projectionVector = new Vector3();
        font = renderer.font;
        AILibrary.loadAILibrary();
        camera = new OrthographicCamera(120F, 200F);
        camera.position.set(60F, 100F, 0.0F);
        camera.update();
        batch = new SpriteBatch();
        batch.setShader(null);
        batch.setProjectionMatrix(camera.combined);
        loadAssets(renderer.atlas);
        setupUI();
        Gdx.input.setCatchBackKey(true);
    }

    private void loadAssets(TextureAtlas atlas)
    {
        if(!PixelTower.COOKIES_BEER)
            menuBackdrop = atlas.findRegion("pixelTowerLogo");
        else
            menuBackdrop = atlas.findRegion("pixelTowerLogo02");
    }

    private void setupUI()
    {
        newGame = new TextButton("NEW GAME", buttonUp, buttonDown, font);
        newGame.setPosition(43, 130);
        newGame.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                ScreenLibrary.getGameScreen(pixelTower).newGame();
                pixelTower.setScreen(ScreenLibrary.getGameScreen(pixelTower));
                SoundLibrary.selectSound.play();
                SoundLibrary.MainGame();
            }

        
        }
;
        highscores = new TextButton("HIGHSCORES", buttonUp, buttonDown, font);
        highscores.setPosition(41, 115);
        highscores.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                pixelTower.setScreen(ScreenLibrary.getHighscoreScreen(pixelTower));
                SoundLibrary.selectSound.play();
            }

  
        }
;
        credits = new TextButton("CREDITS", buttonUp, buttonDown, font);
        credits.setPosition(46, 100);
        credits.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                pixelTower.setScreen(ScreenLibrary.getCreditsScreen(pixelTower));
                SoundLibrary.selectSound.play();
            }

      
        }
;
        scoreloop = new TextButton("SCORELOOP", buttonUp, buttonDown, font);
        scoreloop.setPosition(42, 85);
        scoreloop.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                if(Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.Android)
                    pixelTower.actionResolver.showScoreloopScreen();
                SoundLibrary.selectSound.play();
            }

     
        }
;
        if(PixelTower.COOKIES_BEER)
        {
            exit = new TextButton("EXIT", buttonUp, buttonDown, font);
            exit.setPosition(52, 70);
            exit.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

                public void onTouchDown(TextButton textbutton)
                {
                }

                public void onTouchUp(TextButton button)
                {
                    SoundLibrary.selectSound.play();
                    Gdx.app.exit();
                    System.exit(0);
                }

   
            }
;
        } else
        {
            supportUs = new TextButton("SUPPORT US", buttonUp, buttonDown, font);
            supportUs.setPosition(40, 70);
            supportUs.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

                public void onTouchDown(TextButton textbutton)
                {
                }

                public void onTouchUp(TextButton button)
                {
                    SoundLibrary.selectSound.play();
                    pixelTower.setScreen(ScreenLibrary.getCookiesAndBeerScreen(pixelTower));
                }

   
            }
;
            exit = new TextButton("EXIT", buttonUp, buttonDown, font);
            exit.setPosition(52, 55);
            exit.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

                public void onTouchDown(TextButton textbutton)
                {
                }

                public void onTouchUp(TextButton button)
                {
                    SoundLibrary.selectSound.play();
                    Gdx.app.exit();
                    System.exit(0);
                }

  
            }
;
        }
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);
        statusTime += deltaTime;
        if(!PixelTower.COOKIES_BEER && statusTime > 0.5F && LocalStorage.getPlayCount() == 3 && !LocalStorage.getDeclinedCAB())
            pixelTower.setScreen(ScreenLibrary.getCookiesAndBeerScreen(pixelTower));
    }

    public void present(float deltaTime)
    {
        super.present(deltaTime);
        batch.begin();
        batch.draw(menuBackdrop, 5F, 28F);
        newGame.render(batch);
        highscores.render(batch);
        credits.render(batch);
        scoreloop.render(batch);
        exit.render(batch);
        if(!PixelTower.COOKIES_BEER)
            supportUs.render(batch);
        batch.end();
    }

    public boolean keyDown(int keycode)
    {
        if(keycode == 4)
        {
            Gdx.app.exit();
            System.exit(0);
        }
        if(keycode == 41 && ScreenLibrary.getOverlayScreen().state == 1)
            ScreenLibrary.getOverlayScreen().displayMessage("Welcome back bach!", 2.0F);
        return false;
    }

    public boolean keyUp(int keycode)
    {
        return false;
    }

    public boolean keyTyped(char character)
    {
        return false;
    }

    public boolean touchDown(int x, int y, int pointer, int button)
    {
        projectionVector.set(x, y, 0.0F);
        camera.unproject(projectionVector);
        newGame.touchDown((int)projectionVector.x, (int)projectionVector.y);
        highscores.touchDown((int)projectionVector.x, (int)projectionVector.y);
        scoreloop.touchDown((int)projectionVector.x, (int)projectionVector.y);
        credits.touchDown((int)projectionVector.x, (int)projectionVector.y);
        exit.touchDown((int)projectionVector.x, (int)projectionVector.y);
        if(!PixelTower.COOKIES_BEER)
            supportUs.touchDown((int)projectionVector.x, (int)projectionVector.y);
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button)
    {
        projectionVector.set(x, y, 0.0F);
        camera.unproject(projectionVector);
        newGame.touchUp((int)projectionVector.x, (int)projectionVector.y);
        highscores.touchUp((int)projectionVector.x, (int)projectionVector.y);
        scoreloop.touchUp((int)projectionVector.x, (int)projectionVector.y);
        credits.touchUp((int)projectionVector.x, (int)projectionVector.y);
        exit.touchUp((int)projectionVector.x, (int)projectionVector.y);
        if(!PixelTower.COOKIES_BEER)
            supportUs.touchUp((int)projectionVector.x, (int)projectionVector.y);
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer)
    {
        return false;
    }

    public boolean touchMoved(int x, int y)
    {
        return false;
    }

    public boolean scrolled(int amount)
    {
        return false;
    }

    Color buttonUp;
    Color buttonDown;
    SpriteBatch batch;
    BitmapFont font;
    TextureRegion menuBackdrop;
    TextButton newGame;
    TextButton highscores;
    TextButton credits;
    TextButton scoreloop;
    TextButton supportUs;
    TextButton exit;
    OrthographicCamera camera;
    Vector3 projectionVector;
    private float statusTime;
}
