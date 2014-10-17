package com.thegreystudios.pixeltower.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.thegreystudios.pixeltower.PixelTower;
import com.thegreystudios.pixeltower.renderers.WorldRenderer;
import com.thegreystudios.pixeltower.status.LocalStorage;
import com.thegreystudios.pixeltower.ui.ClickableArea;
import com.thegreystudios.pixeltower.ui.TextButton;


public class CookiesAndBeerScreen extends PopupScreen
  implements InputProcessor
{
  Color buttonUp = new Color(1.0F, 0.9568628F, 0.1568628F, 1.0F);
  Color buttonDown = new Color(1.0F, 1.0F, 0.509804F, 1.0F);
  SpriteBatch batch;
  TextButton goToMarket;
  TextButton notInterested;
  ClickableArea goToMarketCA;
  ClickableArea notInterestedCA;
  TextureRegion backdrop;
  TextureRegion beerYes;
  TextureRegion beerNo;
  OrthographicCamera camera;
  Vector3 projectionVector = new Vector3();
  BitmapFont font;

  public CookiesAndBeerScreen(PixelTower pixelTower, Screen parent, WorldRenderer renderer)
  {
    super(pixelTower, parent, false);

    this.camera = new OrthographicCamera(120.0F, 200.0F);
    this.camera.position.set(60.0F, 100.0F, 0.0F);
    this.camera.update();

    this.batch = new SpriteBatch();
    this.batch.setShader(null);
    this.batch.setProjectionMatrix(this.camera.combined);

    loadAssets(renderer);
    setupUI();
  }

  private void loadAssets(WorldRenderer renderer) {
    this.font = renderer.font;

    this.backdrop = renderer.atlas.findRegion("greyOverlay");
    this.beerYes = renderer.atlas.findRegion("beerYes");
    this.beerNo = renderer.atlas.findRegion("beerNo");
  }

  private void setupUI() {
    this.goToMarket = new TextButton("GO TO MARKET", this.buttonUp, this.buttonDown, this.font);
    this.goToMarket.setPosition(10, 15);
    this.goToMarket.listener = new TextButton.ClickListener()
    {
      public void onTouchDown(TextButton button)
      {
      }

      public void onTouchUp(TextButton button)
      {
        LocalStorage.setDeclinedCAB(true);
        LocalStorage.save(CookiesAndBeerScreen.this.pixelTower);

        CookiesAndBeerScreen.this.pixelTower.actionResolver.openCAB();
      }
    };
    this.notInterested = new TextButton("NO THANKS", this.buttonUp, this.buttonDown, this.font);
    this.notInterested.setPosition(70, 15);
    this.notInterested.listener = new TextButton.ClickListener()
    {
      public void onTouchDown(TextButton button)
      {
      }

      public void onTouchUp(TextButton button)
      {
        LocalStorage.setDeclinedCAB(true);
        LocalStorage.save(CookiesAndBeerScreen.this.pixelTower);
        ScreenLibrary.getGameScreen(CookiesAndBeerScreen.this.pixelTower).renderer.renderCrane = false;
        ScreenLibrary.getGameScreen(CookiesAndBeerScreen.this.pixelTower).renderer.renderHighscore = false;
        CookiesAndBeerScreen.this.pixelTower.setScreen(ScreenLibrary.getMenuScreen(CookiesAndBeerScreen.this.pixelTower));
      }
    };
    this.notInterestedCA = new ClickableArea(new Rectangle(80.0F, 27.0F, 17.0F, 20.0F));
    this.notInterestedCA.listener = new ClickableArea.ClickListener()
    {
      public void onTouchDown(ClickableArea clickableArea)
      {
      }

      public void onTouchUp(ClickableArea button) {
        LocalStorage.setDeclinedCAB(true);
        LocalStorage.save(CookiesAndBeerScreen.this.pixelTower);
        ScreenLibrary.getGameScreen(CookiesAndBeerScreen.this.pixelTower).renderer.renderCrane = false;
        ScreenLibrary.getGameScreen(CookiesAndBeerScreen.this.pixelTower).renderer.renderHighscore = false;
        CookiesAndBeerScreen.this.pixelTower.setScreen(ScreenLibrary.getMenuScreen(CookiesAndBeerScreen.this.pixelTower));
      }
    };
    this.goToMarketCA = new ClickableArea(new Rectangle(25.0F, 27.0F, 17.0F, 20.0F));
    this.goToMarketCA.listener = new ClickableArea.ClickListener()
    {
      public void onTouchDown(ClickableArea clickableArea)
      {
      }

      public void onTouchUp(ClickableArea button) {
        LocalStorage.setDeclinedCAB(true);
        LocalStorage.save(CookiesAndBeerScreen.this.pixelTower);

        CookiesAndBeerScreen.this.pixelTower.actionResolver.openCAB();
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
    this.batch.draw(this.backdrop, 0.0F, 0.0F, 120.0F, 200.0F);

    this.font.draw(this.batch, "HI THERE, ", 10.0F, 190.0F);
    this.font.draw(this.batch, "PIXEL TOWERS IS COMPLETELY", 10.0F, 170.0F);
    this.font.draw(this.batch, "FREE. HOWEVER, IF YOU WOULD", 10.0F, 160.0F);
    this.font.draw(this.batch, "LIKE TO SHOUT US SOME", 10.0F, 150.0F);
    this.font.draw(this.batch, "COOKIES + BEER WE HAVE A", 10.0F, 140.0F);
    this.font.draw(this.batch, "DONATION VERSION ON THE", 10.0F, 130.0F);
    this.font.draw(this.batch, "MARKET. IT IS THE EXACT", 10.0F, 120.0F);
    this.font.draw(this.batch, "SAME GAME BUT WE GET TO", 10.0F, 110.0F);
    this.font.draw(this.batch, "ENJOY SOME COOKIES + BEER.", 10.0F, 100.0F);
    this.font.draw(this.batch, "CHEERS,", 10.0F, 80.0F);
    this.font.draw(this.batch, "THE GREY STUDIOS", 10.0F, 70.0F);

    this.goToMarket.render(this.batch);
    this.notInterested.render(this.batch);

    this.batch.draw(this.beerYes, 25.0F, 27.0F);
    this.batch.draw(this.beerNo, 80.0F, 27.0F);

    this.batch.end();
  }

  public boolean keyDown(int keycode)
  {
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

    this.goToMarket.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.notInterested.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.goToMarketCA.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.notInterestedCA.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    return false;
  }

  public boolean touchUp(int x, int y, int pointer, int button)
  {
    this.projectionVector.set(x, y, 0.0F);
    this.camera.unproject(this.projectionVector);

    this.goToMarket.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.notInterested.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.goToMarketCA.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.notInterestedCA.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
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