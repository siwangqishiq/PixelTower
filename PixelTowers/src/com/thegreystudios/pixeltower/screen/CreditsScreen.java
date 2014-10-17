package com.thegreystudios.pixeltower.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.thegreystudios.pixeltower.PixelTower;
import com.thegreystudios.pixeltower.renderers.WorldRenderer;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;
import com.thegreystudios.pixeltower.ui.ClickableArea;
import com.thegreystudios.pixeltower.ui.TextButton;

public class CreditsScreen extends PopupScreen
  implements InputProcessor
{
  Color buttonUp = new Color(1.0F, 0.9568628F, 0.1568628F, 1.0F);
  Color buttonDown = new Color(1.0F, 1.0F, 0.509804F, 1.0F);
  TextureRegion highscoreBanner;
  TextureRegion twitter;
  TextureRegion website;
  TextureRegion market;
  SpriteBatch batch;
  OrthographicCamera camera;
  TextButton backButton;
  ClickableArea twitterArea;
  ClickableArea websiteArea;
  ClickableArea marketArea;
  BitmapFont font;
  Vector3 projectionVector = new Vector3();

  public CreditsScreen(PixelTower pixelTower, Screen parent, WorldRenderer renderer) {
    super(pixelTower, parent, true);

    loadAssets(renderer.atlas);

    this.font = renderer.font;

    this.camera = new OrthographicCamera(120.0F, 200.0F);
    this.camera.position.set(60.0F, 100.0F, 0.0F);
    this.camera.update();

    this.batch = new SpriteBatch();
    this.batch.setShader(null);
    this.batch.setProjectionMatrix(this.camera.combined);

    setupUI();
  }

  private void loadAssets(TextureAtlas atlas) {
    this.highscoreBanner = atlas.findRegion("highscoreBanner01");

    this.twitter = atlas.findRegion("twitter");
    this.website = atlas.findRegion("rss");
    this.market = atlas.findRegion("market");
  }

  private void setupUI() {
    this.backButton = new TextButton("BACK", this.buttonUp, this.buttonDown, this.font);
    this.backButton.setPosition(3, 2);
    this.backButton.listener = new TextButton.ClickListener()
    {
      public void onTouchDown(TextButton button)
      {
      }

      public void onTouchUp(TextButton button)
      {
        CreditsScreen.this.pixelTower.setScreen(ScreenLibrary.getMenuScreen(CreditsScreen.this.pixelTower));
        SoundLibrary.selectSound.play();
      }
    };
    this.twitterArea = new ClickableArea(new Rectangle(26.0F, 45.0F, 13.0F, 15.0F));
    this.twitterArea.listener = new ClickableArea.ClickListener()
    {
      public void onTouchDown(ClickableArea clickableArea)
      {
      }

      public void onTouchUp(ClickableArea button) {
        SoundLibrary.selectSound.play();

        CreditsScreen.this.pixelTower.actionResolver.openTwitter();
      }
    };
    this.websiteArea = new ClickableArea(new Rectangle(53.0F, 45.0F, 13.0F, 15.0F));
    this.websiteArea.listener = new ClickableArea.ClickListener()
    {
      public void onTouchDown(ClickableArea clickableArea)
      {
      }

      public void onTouchUp(ClickableArea button) {
        SoundLibrary.selectSound.play();

        CreditsScreen.this.pixelTower.actionResolver.openWebsite();
      }
    };
    this.marketArea = new ClickableArea(new Rectangle(80.0F, 45.0F, 13.0F, 15.0F));
    this.marketArea.listener = new ClickableArea.ClickListener()
    {
      public void onTouchDown(ClickableArea clickableArea)
      {
      }

      public void onTouchUp(ClickableArea button) {
        SoundLibrary.selectSound.play();

        CreditsScreen.this.pixelTower.actionResolver.openMarket();
      }
    };
  }

  public void update(float deltaTime) {
    super.update(deltaTime);
  }

  public void present(float deltaTime)
  {
    super.present(deltaTime);

    this.batch.begin();

    this.batch.draw(this.highscoreBanner, 8.0F, 28.0F);

    this.backButton.render(this.batch);

    this.font.setColor(this.buttonUp);
    this.font.draw(this.batch, "THE GREY STUDIOS", 28.0F, 185.0F);
    this.font.setColor(Color.WHITE);
    this.font.draw(this.batch, "SIMON BACHMANN", 30.0F, 175.0F);
    this.font.draw(this.batch, "STEFAN BACHMANN", 28.0F, 165.0F);

    this.font.setColor(this.buttonUp);
    this.font.draw(this.batch, "MUSIC BY", 41.0F, 145.0F);
    this.font.setColor(Color.WHITE);
    this.font.draw(this.batch, "GAVIN HARRISON", 32.0F, 135.0F);

    this.font.setColor(this.buttonUp);
    this.font.draw(this.batch, "SPECIAL THANKS TO", 26.0F, 115.0F);
    this.font.setColor(Color.WHITE);
    this.font.draw(this.batch, "MADISON BACHMANN", 26.0F, 105.0F);
    this.font.draw(this.batch, "MELANIE ZUELCH", 31.0F, 95.0F);
    this.font.draw(this.batch, "LIBGDX CREW", 36.0F, 85.0F);

    this.batch.draw(this.twitter, 26.0F, 45.0F);
    this.batch.draw(this.website, 53.0F, 45.0F);
    this.batch.draw(this.market, 80.0F, 45.0F);

    this.batch.end();
  }

  public boolean keyDown(int keycode)
  {
    if (keycode == 4)
      this.pixelTower.setScreen(ScreenLibrary.getMenuScreen(this.pixelTower));
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
    this.projectionVector.set(x, y, 0.0F);
    this.camera.unproject(this.projectionVector);

    this.backButton.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.twitterArea.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.websiteArea.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.marketArea.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    return false;
  }

  public boolean touchUp(int x, int y, int pointer, int button)
  {
    this.projectionVector.set(x, y, 0.0F);
    this.camera.unproject(this.projectionVector);

    this.backButton.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.twitterArea.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.websiteArea.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.marketArea.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
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
}