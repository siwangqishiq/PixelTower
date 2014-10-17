
package com.thegreystudios.pixeltower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.thegreystudios.pixeltower.PixelTower;
import com.thegreystudios.pixeltower.renderers.WorldRenderer;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;
import com.thegreystudios.pixeltower.status.GameStatus;
import com.thegreystudios.pixeltower.world.World;


public class MenuBackgroundScreen extends Screen
{

    public MenuBackgroundScreen(PixelTower pixelTower, AssetManager assetManager)
    {
        super(pixelTower);
        projectionVector = new Vector3();
        SoundLibrary.load();
        world = new World(true);
        renderer = new WorldRenderer(assetManager);
        camera = new OrthographicCamera(120F, 200F);
        camera.position.set(60F, 100F, 0.0F);
        camera.update();
    }

    public void reset()
    {
        world.reset();
    }

    public void update(float deltaTime)
    {
        world.update(deltaTime);
        if(GameStatus.cameraHeight >= (float)(GameStatus.height * 15))
            GameStatus.menuCameraVelocity = -GameStatus.menuScrollSpeed;
        else
        if(GameStatus.cameraHeight <= 90F)
            GameStatus.menuCameraVelocity = GameStatus.menuScrollSpeed;
        GameStatus.cameraHeight += GameStatus.menuCameraVelocity * deltaTime;
    }

    public void present(float deltaTime)
    {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        renderer.render(world);
    }

    public void pause()
    {
    }

    public void resume()
    {
        renderer.renderCrane = false;
        renderer.renderHighscore = false;
    }

    public void dispose()
    {
    }

    public World world;
    public WorldRenderer renderer;
    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 projectionVector;
}
