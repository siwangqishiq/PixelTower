package com.thegreystudios.pixeltower;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.imagepacker.TexturePacker;

import android.os.Bundle;

public class PixelTowersActivity extends AndroidApplication {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = false;
		config.useGL20 = true;
		config.resolutionStrategy = new RatioResolutionStrategy(360, 600);
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.padding = 2;
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		settings.incremental = true;
		settings.stripWhitespace = false;
		settings.defaultFilterMin = TextureFilter.Nearest;
		settings.defaultFilterMag = TextureFilter.Nearest;
		TexturePacker.process(settings, "assets/gfx", "assets/gfx");

		ActionResolverAndroid actionResolver = new ActionResolverAndroid();

		initialize(new PixelTower(actionResolver, false), config);
	
	}

}