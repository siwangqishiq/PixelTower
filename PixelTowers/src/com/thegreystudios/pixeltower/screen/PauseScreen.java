
package com.thegreystudios.pixeltower.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector3;
import com.thegreystudios.pixeltower.PixelTower;
import com.thegreystudios.pixeltower.renderers.WorldRenderer;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;
import com.thegreystudios.pixeltower.status.GameStatus;
import com.thegreystudios.pixeltower.ui.TextButton;

public class PauseScreen extends PopupScreen
    implements InputProcessor
{

    public PauseScreen(PixelTower pixelTower, Screen parent, WorldRenderer renderer)
    {
        super(pixelTower, parent, false);
        buttonUp = new Color(1.0F, 0.9568627F, 0.1568628F, 1.0F);
        buttonDown = new Color(1.0F, 1.0F, 0.509804F, 1.0F);
        projectionVector = new Vector3();
        font = renderer.font;
        camera = new OrthographicCamera(120F, 200F);
        camera.position.set(60F, 100F, 0.0F);
        camera.update();
        batch = new SpriteBatch();
        batch.setShader(null);
        batch.setProjectionMatrix(camera.combined);
        loadAssets(renderer.atlas);
        setupUI();
    }

    private void loadAssets(TextureAtlas atlas)
    {
        backdrop = atlas.findRegion("pauseBackdrop");
    }

    private void setupUI()
    {
        restart = new TextButton("RESTART", buttonUp, buttonDown, font);
        restart.setPosition(46, 100);
        restart.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

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
        mainMenu = new TextButton("MAIN MENU", buttonUp, buttonDown, font);
        mainMenu.setPosition(41, 85);
        mainMenu.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                ScreenLibrary.getMenuBackgroundScreen(pixelTower).reset();
                ScreenLibrary.getGameScreen(pixelTower).renderer.renderCrane = false;
                ScreenLibrary.getGameScreen(pixelTower).renderer.renderHighscore = false;
                GameStatus.newGame();
                pixelTower.setScreen(ScreenLibrary.getMenuScreen(pixelTower));
                SoundLibrary.selectSound.play();
                SoundLibrary.Menu();
            }

 
        }
;
        resume = new TextButton("RESUME", buttonUp, buttonDown, font);
        resume.setPosition(47, 115);
        resume.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                SoundLibrary.backgroundMusic.play();
                pixelTower.setScreen(parent);
                SoundLibrary.selectSound.play();
            }

        }
;
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    public void present(float deltaTime)
    {
        super.present(deltaTime);
        batch.begin();
        batch.draw(backdrop, 28F, 40F);
        font.draw(batch, "PAUSE", 50F, 157F);
        restart.render(batch);
        resume.render(batch);
        mainMenu.render(batch);
        batch.end();
    }

    public boolean keyDown(int keycode)
    {
        if(keycode == 4)
        {
            SoundLibrary.backgroundMusic.play();
            pixelTower.setScreen(parent);
            SoundLibrary.selectSound.play();
        }
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
        restart.touchDown((int)projectionVector.x, (int)projectionVector.y);
        resume.touchDown((int)projectionVector.x, (int)projectionVector.y);
        mainMenu.touchDown((int)projectionVector.x, (int)projectionVector.y);
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button)
    {
        projectionVector.set(x, y, 0.0F);
        camera.unproject(projectionVector);
        restart.touchUp((int)projectionVector.x, (int)projectionVector.y);
        resume.touchUp((int)projectionVector.x, (int)projectionVector.y);
        mainMenu.touchUp((int)projectionVector.x, (int)projectionVector.y);
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
    TextButton restart;
    TextButton mainMenu;
    TextButton resume;
    TextureRegion backdrop;
    OrthographicCamera camera;
    Vector3 projectionVector;
}
