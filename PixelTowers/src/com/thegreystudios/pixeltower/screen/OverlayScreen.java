
package com.thegreystudios.pixeltower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class OverlayScreen extends Screen
{

    public OverlayScreen()
    {
        super(null);
        slideSpeed = 70F;
        state = 1;
        camera = new OrthographicCamera(480F, 800F);
        camera.position.set(240F, 400F, 0.0F);
        camera.update();
        batch = new SpriteBatch();
        batch.setShader(null);
        batch.setProjectionMatrix(camera.combined);
        loadAssets();
    }

    private void loadAssets()
    {
        Texture texture = new Texture(Gdx.files.internal("scoreLoopNotification.png"));
        scoreLoopBanner = new TextureRegion(texture, 0, 0, 480, 41);
        font = new BitmapFont(Gdx.files.internal("fonts/wendy.fnt"), Gdx.files.internal("fonts/wendy_00.png"), false);
    }

    public synchronized void displayMessage(String message, float delay)
    {
        stateTime = 0.0F;
        this.delay = delay;
        x = 0.0F;
        y = 800F;
        this.message = message.toUpperCase();
        state = 3;
    }

    public void update(float deltaTime)
    {
        if(state == 4)
        {
            stateTime += deltaTime;
            if(stateTime > delay)
                state = 2;
        }
        if(state == 3)
        {
            y -= slideSpeed * deltaTime;
            if(y < 760F)
            {
                y = 760F;
                state = 4;
            }
        }
        if(state == 2)
        {
            y += slideSpeed * deltaTime;
            if(y > 800F)
            {
                y = 800F;
                state = 1;
            }
        }
    }

    public void present(float deltaTime)
    {
        batch.begin();
        if(state != 1)
        {
            batch.draw(scoreLoopBanner, x, y);
            font.draw(batch, message, 45F, y + 36F);
        }
        font.setScale(2.0F);
        batch.end();
    }

    public void pause()
    {
    }

    public void resume()
    {
    }

    public void dispose()
    {
    }

    public static final int HIDDEN = 1;
    public static final int SLIDE_IN = 2;
    public static final int SLIDE_OUT = 3;
    public static final int SHOWING = 4;
    SpriteBatch batch;
    TextureRegion scoreLoopBanner;
    BitmapFont font;
    OrthographicCamera camera;
    float delay;
    float stateTime;
    float slideSpeed;
    int state;
    private String message;
    float x;
    float y;
}
