package com.thegreystudios.pixeltower.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thegreystudios.pixeltower.PixelTower;
import com.thegreystudios.pixeltower.ai.AILibrary;
import com.thegreystudios.pixeltower.status.LocalStorage;

public class SplashScreen extends Screen implements InputProcessor {
	BitmapFont font = new BitmapFont();
	TextureRegion splashScreen;
	float percentage = 0.2F;

	int currentItem = 0;
	boolean loading;
	SpriteBatch batch = new SpriteBatch();
	OrthographicCamera camera;
	public AssetManager assetManager;
	private float splashTime = 0.0F;

	public SplashScreen(PixelTower pixelTower) {
		super(pixelTower);

		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		this.camera.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0.0F);
		this.camera.update();

		LocalStorage.load();
		LocalStorage.setPlayCount(LocalStorage.getPlayCount() + 1);
		LocalStorage.save(pixelTower);

		Texture texture = new Texture(Gdx.files.internal("splash.png"));
		this.splashScreen = new TextureRegion(texture, 0, 0, 480, 800);

		this.assetManager = new AssetManager();

		Texture.setAssetManager(this.assetManager);

		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			pixelTower.actionResolver.fetchHighscores();
			pixelTower.actionResolver.fetchUserDetails();
			pixelTower.actionResolver.fetchUserRank();
		}

		loadAssetManager();
		AILibrary.loadAILibrary();
	}

	private void loadAssetManager() {
		this.assetManager.load("gfx/pack", TextureAtlas.class);
		this.assetManager.load("fonts/wendy.fnt", BitmapFont.class);
	}

	public void update(float deltaTime) {
		if ((this.assetManager.getProgress() == 1.0F)
				&& (this.splashTime > 2.5F)) {
			this.pixelTower.setScreen(ScreenLibrary
					.getMenuScreen(this.pixelTower));
		}
		this.assetManager.update();

		this.splashTime += deltaTime;
	}

	public void present(float deltaTime) {
		GL20 gl = Gdx.graphics.getGL20();

		if (gl != null) {
			gl.glViewport(0, 0, Gdx.graphics.getWidth(),
					Gdx.graphics.getHeight());
			gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
			gl.glClear(16640);
			gl.glEnable(3553);

			this.batch.setProjectionMatrix(this.camera.combined);

			float desiredAspect = 0.6F;
			float currentAspect = Gdx.graphics.getWidth()
					/ Gdx.graphics.getHeight();
			float difference = currentAspect - desiredAspect;

			this.batch.begin();
			this.batch.setColor(Color.WHITE);
			this.batch.draw(this.splashScreen, 0.0F + Gdx.graphics.getWidth()
					* difference, 0.0F,
					Gdx.graphics.getWidth() - Gdx.graphics.getWidth()
							* difference, Gdx.graphics.getHeight());
			this.batch.setColor(Color.BLACK);
			this.batch.end();
		}
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
	}

	public boolean keyDown(int keycode) {
		return false;
	}

	public boolean keyUp(int keycode) {
		return false;
	}

	public boolean keyTyped(char character) {
		return false;
	}

	public boolean touchDown(int x, int y, int pointer, int button) {
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	public boolean touchMoved(int x, int y) {
		return false;
	}

	public boolean scrolled(int amount) {
		return false;
	}
}