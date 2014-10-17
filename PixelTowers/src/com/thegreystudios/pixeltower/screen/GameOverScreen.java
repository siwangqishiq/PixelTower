package com.thegreystudios.pixeltower.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
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
import com.thegreystudios.pixeltower.status.GameStatus;
import com.thegreystudios.pixeltower.status.LocalStorage;
import com.thegreystudios.pixeltower.ui.ClickableArea;

public class GameOverScreen extends PopupScreen
  implements InputProcessor
{
  String loading = "...";

  Color buttonUp = new Color(1.0F, 0.9568628F, 0.1568628F, 1.0F);
  Color buttonDown = new Color(1.0F, 1.0F, 0.509804F, 1.0F);
  SpriteBatch batch;
  BitmapFont font;
  TextureRegion backdrop;
  TextureRegion menuIcon;
  TextureRegion replayIcon;
  TextureRegion highscoreIcon;
  ClickableArea newGame;
  ClickableArea mainMenu;
  ClickableArea highscores;
  OrthographicCamera camera;
  Vector3 projectionVector = new Vector3();
  public float stateTime;

  public GameOverScreen(PixelTower pixelTower, Screen parent, WorldRenderer renderer)
  {
    super(pixelTower, parent, true);

    this.font = renderer.font;

    this.camera = new OrthographicCamera(120.0F, 200.0F);
    this.camera.position.set(60.0F, 100.0F, 0.0F);
    this.camera.update();

    this.batch = new SpriteBatch();
    this.batch.setShader(null);
    this.batch.setProjectionMatrix(this.camera.combined);

    loadAssets(renderer.atlas);
    setupUI();
  }

  private void loadAssets(TextureAtlas atlas) {
    this.backdrop = atlas.findRegion("gameoverBackdrop");

    this.menuIcon = atlas.findRegion("menuIcon");
    this.replayIcon = atlas.findRegion("replayIcon");
    this.highscoreIcon = atlas.findRegion("highscoreIcon");
  }

  private void setupUI() {
    this.newGame = new ClickableArea(new Rectangle(25.0F, 120.0F, 15.0F, 14.0F));
    this.newGame.listener = new ClickableArea.ClickListener()
    {
      public void onTouchDown(ClickableArea button)
      {
      }

      public void onTouchUp(ClickableArea button)
      {
        ScreenLibrary.getGameScreen(GameOverScreen.this.pixelTower).newGame();
        GameOverScreen.this.pixelTower.setScreen(ScreenLibrary.getGameScreen(GameOverScreen.this.pixelTower));
        SoundLibrary.selectSound.play();
        SoundLibrary.MainGame();
      }
    };
    this.mainMenu = new ClickableArea(new Rectangle(54.0F, 120.0F, 15.0F, 14.0F));
    this.mainMenu.listener = new ClickableArea.ClickListener()
    {
      public void onTouchDown(ClickableArea button)
      {
      }

      public void onTouchUp(ClickableArea button)
      {
        ScreenLibrary.getGameScreen(GameOverScreen.this.pixelTower).renderer.renderCrane = false;
        ScreenLibrary.getGameScreen(GameOverScreen.this.pixelTower).renderer.renderHighscore = false;
        ScreenLibrary.getMenuBackgroundScreen(GameOverScreen.this.pixelTower).reset();

        GameStatus.newGame();

        GameOverScreen.this.pixelTower.setScreen(ScreenLibrary.getMenuScreen(GameOverScreen.this.pixelTower));
        SoundLibrary.selectSound.play();
        SoundLibrary.Menu();
      }
    };
    this.highscores = new ClickableArea(new Rectangle(83.0F, 120.0F, 15.0F, 14.0F));
    this.highscores.listener = new ClickableArea.ClickListener()
    {
      public void onTouchDown(ClickableArea button)
      {
      }

      public void onTouchUp(ClickableArea button)
      {
        ScreenLibrary.getGameScreen(GameOverScreen.this.pixelTower).renderer.renderCrane = false;
        ScreenLibrary.getGameScreen(GameOverScreen.this.pixelTower).renderer.renderHighscore = false;
        ScreenLibrary.getMenuBackgroundScreen(GameOverScreen.this.pixelTower).reset();

        GameStatus.newGame();

        GameOverScreen.this.pixelTower.setScreen(ScreenLibrary.getHighscoreScreen(GameOverScreen.this.pixelTower));
        SoundLibrary.selectSound.play();
        SoundLibrary.Menu();
      }
    };
  }

  public void update(float deltaTime) {
    super.update(deltaTime);
    this.stateTime += deltaTime;

    if ((this.stateTime > 1.2F) && (!SoundLibrary.gameOverMusic.isPlaying()))
      SoundLibrary.GameOver();
  }

  public void present(float deltaTime)
  {
    super.present(deltaTime);

    if (this.stateTime > 1.0F) {
      this.batch.begin();

      this.batch.draw(this.backdrop, 8.0F, 102.0F);

      this.font.draw(this.batch, "GAME OVER", 43.0F, 187.0F);
      this.font.draw(this.batch, "SCORE: " + GameStatus.height * 15, 25.0F, 173.0F);
      this.font.draw(this.batch, "HIGHSCORE: " + LocalStorage.getLocalHighscores()[0], 25.0F, 163.0F);
      if (Gdx.app.getType() == Application.ApplicationType.Android) {
        if (GameStatus.rankUpdated) {
          this.font.draw(this.batch, "ONLINE RANK: " + LocalStorage.getUserGlobalRank(), 25.0F, 153.0F);
        }
        else {
          this.font.draw(this.batch, "ONLINE RANK: ...", 25.0F, 153.0F);
        }
      }
      this.batch.draw(this.replayIcon, 25.0F, 120.0F);
      this.batch.draw(this.menuIcon, 52.0F, 120.0F);
      this.batch.draw(this.highscoreIcon, 79.0F, 120.0F);

      this.batch.end();
    }
  }

  public boolean touchDown(int x, int y, int pointer, int button)
  {
    this.projectionVector.set(x, y, 0.0F);
    this.camera.unproject(this.projectionVector);

    this.newGame.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.highscores.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.mainMenu.touchDown((int)this.projectionVector.x, (int)this.projectionVector.y);
    return false;
  }

  public boolean touchUp(int x, int y, int pointer, int button)
  {
    this.projectionVector.set(x, y, 0.0F);
    this.camera.unproject(this.projectionVector);

    this.newGame.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.highscores.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
    this.mainMenu.touchUp((int)this.projectionVector.x, (int)this.projectionVector.y);
    return false;
  }

  public boolean keyDown(int keycode)
  {
    if (keycode == 4)
    {
      ScreenLibrary.getMenuBackgroundScreen(this.pixelTower).reset();
      ScreenLibrary.getGameScreen(this.pixelTower).renderer.renderCrane = false;
      ScreenLibrary.getGameScreen(this.pixelTower).renderer.renderHighscore = false;

      GameStatus.newGame();

      this.pixelTower.setScreen(ScreenLibrary.getMenuScreen(this.pixelTower));
      SoundLibrary.selectSound.play();
      SoundLibrary.Menu();
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