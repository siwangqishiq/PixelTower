
package com.thegreystudios.pixeltower;

import com.thegreystudios.pixeltower.screen.Screen;
import com.thegreystudios.pixeltower.screen.ScreenLibrary;

public class PixelTower extends Game
{

    public PixelTower(ActionResolver resolver, boolean cookiesAndBeer)
    {
        actionResolver = resolver;
        COOKIES_BEER = cookiesAndBeer;
    }

    public Screen getStartScreen()
    {
        overlay = ScreenLibrary.getOverlayScreen();
        return ScreenLibrary.getSplashScreen(this);
    }

    public void render()
    {
        super.render();
    }

    public void resume()
    {
        super.resume();
        while(!ScreenLibrary.getSplashScreen(this).assetManager.update()) ;
    }

    public static boolean COOKIES_BEER = false;
    public ActionResolver actionResolver;

}
