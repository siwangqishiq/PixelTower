package com.thegreystudios.pixeltower.renderers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.thegreystudios.pixeltower.blocks.BlockGroup;
import com.thegreystudios.pixeltower.blocks.BlocksMap;
import com.thegreystudios.pixeltower.gfx.ParticleEmitter;
import com.thegreystudios.pixeltower.status.GameStatus;
import com.thegreystudios.pixeltower.world.World;

public class WorldRenderer
{
  BlocksRenderer blocksRenderer;
  ParticleRenderer particleRenderer;
  PeopleRenderer peopleRenderer;
  OrthographicCamera backgroundCamera;
  public OrthographicCamera camera;
  OrthographicCamera[] parallaxLayersCamera = new OrthographicCamera[6];
  public ShaderProgram shader;
  SpriteBatch batch = new SpriteBatch();
  TextureRegion groundLayer;
  TextureRegion[] backgroundLayer = new TextureRegion[8];
  TextureRegion[] cloudLayer = new TextureRegion[3];
  TextureRegion moonLayer;
  TextureRegion highscore;
  TextureRegion craneTile;
  TextureRegion craneMain;
  TextureRegion craneHook;
  TextureRegion craneChain;
  TextureRegion ufoSighting;
  public boolean renderCrane;
  public boolean renderHighscore;
  public TextureAtlas atlas;
  public BitmapFont font;
  int craneHeight;
  int hookHeight;
  int chainNumber;

  public WorldRenderer(AssetManager assetManager)
  {
    this.atlas = ((TextureAtlas)assetManager.get("gfx/pack", TextureAtlas.class));

    this.font = 
      new BitmapFont(Gdx.files.internal("fonts/wendy.fnt"), 
      Gdx.files.internal("fonts/wendy_00.png"), false);

    this.camera = new OrthographicCamera(120.0F, 200.0F);
    this.camera.position.set(60.0F, 100.0F, 0.0F);
    this.camera.update();

    this.backgroundCamera = new OrthographicCamera(120.0F, 200.0F);
    this.backgroundCamera.position.set(60.0F, 100.0F, 0.0F);
    this.backgroundCamera.update();

    for (int i = 0; i < 6; i++) {
      this.parallaxLayersCamera[i] = new OrthographicCamera(120.0F, 200.0F);
      this.parallaxLayersCamera[i].position.set(60.0F, 100.0F, 0.0F);
    }

    this.blocksRenderer = new BlocksRenderer(this.camera, this.atlas, this.batch);
    this.particleRenderer = new ParticleRenderer(this.batch, this.atlas);
    this.peopleRenderer = new PeopleRenderer(this.batch, this.atlas);

    loadAssets(this.atlas);
    loadShaders();

    this.batch.setShader(null);
  }

  private void loadAssets(TextureAtlas atlas)
  {
    this.groundLayer = atlas.findRegion("groundLayer01");

    this.backgroundLayer[0] = atlas.findRegion("backgroundLayer01");
    this.backgroundLayer[1] = atlas.findRegion("backgroundLayer02");
    this.backgroundLayer[2] = atlas.findRegion("backgroundLayer03");
    this.backgroundLayer[3] = atlas.findRegion("backgroundLayer04");
    this.backgroundLayer[4] = atlas.findRegion("backgroundLayer05");
    this.backgroundLayer[5] = atlas.findRegion("backgroundLayer06");
    this.backgroundLayer[6] = atlas.findRegion("backgroundLayer06a");
    this.backgroundLayer[7] = atlas.findRegion("backgroundLayer06b");

    this.moonLayer = atlas.findRegion("backgroundMoon01");

    this.cloudLayer[0] = atlas.findRegion("cloudLayer01");
    this.cloudLayer[1] = atlas.findRegion("cloudLayer02");
    this.cloudLayer[2] = atlas.findRegion("cloudLayer03");

    this.craneMain = atlas.findRegion("craneMain01");
    this.craneHook = atlas.findRegion("craneHook01");
    this.craneChain = atlas.findRegion("craneChain01");
    this.craneTile = atlas.findRegion("craneTile01");

    this.highscore = atlas.findRegion("highScore01");

    this.ufoSighting = atlas.findRegion("jokesUfo01");
  }

  private void loadShaders() {
    ShaderProgram.pedantic = false;

    this.shader = 
      new ShaderProgram(Gdx.files.internal("shaders/batch.vert").readString(), 
      Gdx.files.internal("shaders/batch.frag").readString());
    if (!this.shader.isCompiled()) {
      Gdx.app.log("ShaderTest", this.shader.getLog());
      System.exit(0);
    }
  }

  public void render(World world) {
    PeopleRenderer.renderedPeopleCount = 0;
    BlocksRenderer.renderedBlocksCount = 0;

    if (GameStatus.height > 6) {
      this.camera.position.y = (85.0F + (GameStatus.cameraHeight - 90.0F));

      this.parallaxLayersCamera[0].position.y = (85.0F + (GameStatus.cameraHeight - 90.0F) * 1.0F);
      this.parallaxLayersCamera[1].position.y = (100.0F + (GameStatus.cameraHeight - 90.0F) * 0.25F);
      this.parallaxLayersCamera[2].position.y = (100.0F + (GameStatus.cameraHeight - 90.0F) * 0.12F);
      this.parallaxLayersCamera[3].position.y = (100.0F + (GameStatus.cameraHeight - 90.0F) * 0.05F);
      this.parallaxLayersCamera[4].position.y = (100.0F + (GameStatus.cameraHeight - 90.0F) * 0.05F);
      this.parallaxLayersCamera[5].position.y = (25.0F + (GameStatus.cameraHeight - 90.0F) * 0.07F);
    }
    else {
      this.camera.position.y = 85.0F;

      this.parallaxLayersCamera[0].position.y = 85.0F;
      this.parallaxLayersCamera[1].position.y = 100.0F;
      this.parallaxLayersCamera[2].position.y = 100.0F;
      this.parallaxLayersCamera[3].position.y = 100.0F;
      this.parallaxLayersCamera[4].position.y = 100.0F;
      this.parallaxLayersCamera[5].position.y = 25.0F;
    }

    this.camera.update(false);

    for (int i = 0; i < 6; i++) {
      this.parallaxLayersCamera[i].update(false);
    }
    this.batch.begin();

    for (int i = 5; i >= 0; i--) {
      this.batch.setProjectionMatrix(this.parallaxLayersCamera[i].combined);
      this.batch.draw(this.backgroundLayer[i], 0.0F, 0.0F);

      if (i == 5) {
        int j = (int)(this.parallaxLayersCamera[i].position.y / 200.0F);

        this.batch.draw(this.backgroundLayer[(i + 1)], 0.0F, 200.0F);
        for (int k = 1; k < j + 1; k++) {
          this.batch.draw(this.backgroundLayer[(i + 2)], 0.0F, 200 + k * 200);
        }
        this.batch.draw(this.cloudLayer[0], GameStatus.cameraHeight / 30.0F - 50.0F, 90.0F);
        this.batch.draw(this.cloudLayer[1], GameStatus.cameraHeight / 30.0F - 110.0F, 160.0F);
        this.batch.draw(this.cloudLayer[2], GameStatus.cameraHeight / 30.0F - 180.0F, 295.0F);

        this.batch.draw(this.moonLayer, 60.0F, -GameStatus.cameraHeight / 60.0F + 60.0F);
      }
    }
    this.batch.setProjectionMatrix(this.camera.combined);

    this.batch.draw(this.groundLayer, 0.0F, -15.0F);

    this.peopleRenderer.renderPeople(world.groundPeople);

    this.blocksRenderer.render(world.blocks);
    this.blocksRenderer.render(world.currentGroup);

    this.peopleRenderer.renderPeople(world.blocks.people);

    for (int i = 0; i < World.particleEmitters.size; i++) {
      this.particleRenderer.renderParticleEmitter((ParticleEmitter)World.particleEmitters.get(i));
    }

    if ((this.renderHighscore) && (com.thegreystudios.pixeltower.status.LocalStorage.getLocalHighscores()[0] > 0) && (!GameStatus.newHighscoreReached)) {
      this.batch.draw(this.highscore, 0.0F, com.thegreystudios.pixeltower.status.LocalStorage.getLocalHighscores()[0]);
    }

    if (this.renderCrane) {
      drawCrane(world);
    }
    if (world.ufoSighting) {
      this.batch.draw(this.ufoSighting, world.ufoX, world.ufoY);
    }

    this.batch.end();
  }

  public void drawCrane(World world)
  {
    this.craneHeight = ((GameStatus.height + 6) * 15);

    if (world.currentGroup != null) {
      this.hookHeight = (GameStatus.height + world.currentGroup.height + 1);
    }
    else {
      this.hookHeight = (GameStatus.height + 2);
    }

    for (int i = 0; i < 8; i++) {
      this.batch.draw(this.craneTile, i * 15, (GameStatus.height + 6) * 15);
    }

    this.chainNumber = ((this.craneHeight - this.hookHeight * 15) / 8);
    for (int i = 0; i < this.chainNumber; i++) {
      this.batch.draw(this.craneChain, world.currentGroupCentre, this.hookHeight * 15 + i * 8);
    }

    this.batch.draw(this.craneMain, world.currentGroupCentre - 7, this.craneHeight - 7);
    this.batch.draw(this.craneHook, world.currentGroupCentre - 7, this.hookHeight * 15);
  }

  public void onResume() {
    this.blocksRenderer.onResume();
  }

  public void dispose() {
    this.blocksRenderer.dispose();
  }
}