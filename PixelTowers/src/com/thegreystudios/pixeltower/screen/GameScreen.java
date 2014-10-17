
package com.thegreystudios.pixeltower.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.thegreystudios.pixeltower.PixelTower;
import com.thegreystudios.pixeltower.ai.AILibrary;
import com.thegreystudios.pixeltower.gamelogic.GameLogicProcessor;
import com.thegreystudios.pixeltower.renderers.WorldRenderer;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;
import com.thegreystudios.pixeltower.status.GameStatus;
import com.thegreystudios.pixeltower.status.LocalStorage;

public class GameScreen extends Screen
    implements InputProcessor
{

    public GameScreen(PixelTower pixelTower, WorldRenderer renderer)
    {
        super(pixelTower);
        batch = new SpriteBatch();
        AILibrary.loadAILibrary();
        font = renderer.font;
        logic = new GameLogicProcessor();
        this.renderer = renderer;
        this.renderer.renderCrane = true;
        this.renderer.renderHighscore = true;
        camera = new OrthographicCamera(120F, 200F);
        camera.position.set(60F, 100F, 0.0F);
        camera.update();
        logic.startNewGame();
        batch.setShader(null);
        batch.setProjectionMatrix(camera.combined);
        loadAssets(renderer.atlas);
    }

    private void loadAssets(TextureAtlas atlas)
    {
        slowDown = atlas.findRegion("slowDown");
        extraStack = atlas.findRegion("extraStack");
        bonusBlock = atlas.findRegion("bonusBlock");
    }

    public void update(float deltaTime)
    {
        logic.update(deltaTime);
        if(GameStatus.status == 1)
        {
            if(Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.Android)
            {
                if(!GameStatus.highscoreSubmitted)
                {
                    LocalStorage.save(pixelTower);
                    ScreenLibrary.getHighscoreScreen(pixelTower).refresh();
                    GameStatus.highscoreSubmitted = true;
                    pixelTower.actionResolver.submitScore(GameStatus.height * 15);
                }
            } else
            {
                LocalStorage.save(pixelTower);
                ScreenLibrary.getHighscoreScreen(pixelTower).refresh();
            }
            ScreenLibrary.getGameOverScreen(pixelTower).stateTime = 0.0F;
            pixelTower.setScreen(ScreenLibrary.getGameOverScreen(pixelTower));
            GameStatus.gameOverScreen();
        }
        if(GameStatus.status == 2 && ScreenLibrary.getGameOverScreen(pixelTower).stateTime > 1.0F)
        {
            if(GameStatus.cameraHeight >= (float)(GameStatus.height * 15))
                GameStatus.menuCameraVelocity = -GameStatus.menuScrollSpeed;
            else
            if(GameStatus.cameraHeight <= 90F)
                GameStatus.menuCameraVelocity = GameStatus.menuScrollSpeed;
            GameStatus.cameraHeight += GameStatus.menuCameraVelocity * deltaTime;
        }
        if(displayMessage)
        {
            messageStateTime += deltaTime;
            if(messageStateTime > messageDuration + fadeInDuration + fadeOutDuration)
                displayMessage = false;
        }
    }

    public void present(float deltaTime)
    {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        if(GameStatus.status == 0)
        {
            renderer.render(logic.world);
            batch.begin();
            batch.enableBlending();
            font.setColor(Color.WHITE);
            font.draw(batch, (new StringBuilder("HEIGHT: ")).append(GameStatus.height * 15).toString(), 3F, 14F);
            if(messageStateTime > fadeInDuration + messageDuration && messageStateTime > nextFlickerTime)
            {
                if(flicker)
                    flicker = false;
                else
                    flicker = true;
                lastFlickerDelta -= 0.18F;
                lastFlickerDelta = Math.max(lastFlickerDelta, 0.075F);
                if(!flicker)
                    nextFlickerTime += lastFlickerDelta * 0.5F;
                else
                    nextFlickerTime += lastFlickerDelta;
            }
            if(displayMessage && flicker)
            {
                fadeInScale = Math.max(2.0F - messageStateTime / fadeInDuration, 1.0F);
                if(currentMessage == 1)
                    batch.draw(extraStack, 58F - 0.5F * (49F * fadeInScale), 150F, 49F * fadeInScale, 29F * fadeInScale);
                else
                if(currentMessage == 3)
                    batch.draw(slowDown, 58F - 0.5F * (49F * fadeInScale), 150F, 49F * fadeInScale, 29F * fadeInScale);
                else
                if(currentMessage == 2)
                    batch.draw(bonusBlock, 60F - 0.5F * (49F * fadeInScale), 150F, 49F * fadeInScale, 29F * fadeInScale);
            }
            batch.end();
        } else
        if(GameStatus.status == 1 || GameStatus.status == 2)
            renderer.render(logic.world);
    }

    public static void displayExtraStack()
    {
        displayMessage = true;
        currentMessage = 1;
        messageStateTime = 0.0F;
        flicker = true;
        nextFlickerTime = fadeInDuration + messageDuration;
        lastFlickerDelta = fadeOutDuration * 0.6F;
    }

    public static void displaySlowDown()
    {
        displayMessage = true;
        currentMessage = 3;
        messageStateTime = 0.0F;
        flicker = true;
        nextFlickerTime = fadeInDuration + messageDuration;
        lastFlickerDelta = fadeOutDuration * 0.6F;
    }

    public static void displayBonusBlock()
    {
        displayMessage = true;
        currentMessage = 2;
        messageStateTime = 0.0F;
        flicker = true;
        nextFlickerTime = fadeInDuration + messageDuration;
        lastFlickerDelta = fadeOutDuration * 0.6F;
    }

    public void newGame()
    {
        logic.startNewGame();
        renderer.renderCrane = true;
        renderer.renderHighscore = true;
        displayMessage = false;
        messageStateTime = 0.0F;
    }

    public void pause()
    {
    }

    public void resume()
    {
        renderer.onResume();
        if(GameStatus.status == 0)
        {
            pixelTower.setScreen(ScreenLibrary.getPauseScreen(pixelTower));
            SoundLibrary.Menu();
        }
    }

    public void dispose()
    {
        renderer.dispose();
    }

    public boolean keyDown(int keycode)
    {
        if(keycode == 4 || keycode == 82 || keycode == 41)
        {
            SoundLibrary.Menu();
            pixelTower.setScreen(ScreenLibrary.getPauseScreen(pixelTower));
        } else
        if(keycode != 44)
            logic.keyDown(keycode);
        return false;
    }

    public boolean keyUp(int keycode)
    {
        logic.keyUp(keycode);
        return false;
    }

    public boolean keyTyped(char character)
    {
        return false;
    }

    public boolean touchDown(int x, int y, int pointer, int button)
    {
        logic.touchDown(x, y, pointer, button);
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button)
    {
        logic.touchUp(x, y, pointer, button);
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

    public static final int EXTRA_STACK = 1;
    public static final int BONUS_BLOCK = 2;
    public static final int SLOW_DOWN = 3;
    WorldRenderer renderer;
    GameLogicProcessor logic;
    BitmapFont font;
    SpriteBatch batch;
    TextureRegion slowDown;
    TextureRegion extraStack;
    TextureRegion bonusBlock;
    OrthographicCamera camera;
    static boolean displayMessage;
    static int currentMessage;
    float fadeInScale;
    static float messageDuration;
    static float fadeInDuration;
    static float fadeOutDuration;
    static float messageStateTime;
    static float lastFlickerDelta;
    static float nextFlickerTime;
    private static boolean flicker = true;

    static 
    {
        messageDuration = 1.0F;
        fadeInDuration = 0.25F;
        fadeOutDuration = 1.0F;
        lastFlickerDelta = fadeOutDuration * 0.025F;
        nextFlickerTime = fadeInDuration + messageDuration;
    }
}
