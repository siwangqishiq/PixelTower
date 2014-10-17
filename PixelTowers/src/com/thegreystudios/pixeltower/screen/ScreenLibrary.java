
package com.thegreystudios.pixeltower.screen;

import com.thegreystudios.pixeltower.PixelTower;

public class ScreenLibrary
{

    public ScreenLibrary()
    {
    }

    public static GameScreen getGameScreen(PixelTower pixelTower)
    {
        if(gameScreen == null)
            gameScreen = new GameScreen(pixelTower, getMenuBackgroundScreen(pixelTower).renderer);
        return gameScreen;
    }

    public static SplashScreen getSplashScreen(PixelTower pixelTower)
    {
        if(splashScreen == null)
            splashScreen = new SplashScreen(pixelTower);
        return splashScreen;
    }

    public static MenuScreen getMenuScreen(PixelTower pixelTower)
    {
        if(menuScreen == null)
        {
            MenuBackgroundScreen bgScreen = getMenuBackgroundScreen(pixelTower);
            menuScreen = new MenuScreen(pixelTower, bgScreen, bgScreen.renderer);
        }
        return menuScreen;
    }

    public static MenuBackgroundScreen getMenuBackgroundScreen(PixelTower pixelTower)
    {
        if(menuBackgroundScreen == null)
        {
            SplashScreen splash = getSplashScreen(pixelTower);
            menuBackgroundScreen = new MenuBackgroundScreen(pixelTower, splash.assetManager);
        }
        return menuBackgroundScreen;
    }

    public static HighscoreScreen getHighscoreScreen(PixelTower pixelTower)
    {
        if(highscoreScreen == null)
        {
            MenuBackgroundScreen bgScreen = getMenuBackgroundScreen(pixelTower);
            highscoreScreen = new HighscoreScreen(pixelTower, bgScreen, bgScreen.renderer);
        }
        return highscoreScreen;
    }

    public static CreditsScreen getCreditsScreen(PixelTower pixelTower)
    {
        if(creditsScreen == null)
        {
            MenuBackgroundScreen bgScreen = getMenuBackgroundScreen(pixelTower);
            creditsScreen = new CreditsScreen(pixelTower, bgScreen, bgScreen.renderer);
        }
        return creditsScreen;
    }

    public static PauseScreen getPauseScreen(PixelTower pixelTower)
    {
        if(pauseScreen == null)
        {
            GameScreen bgScreen = getGameScreen(pixelTower);
            pauseScreen = new PauseScreen(pixelTower, bgScreen, bgScreen.renderer);
        }
        return pauseScreen;
    }

    public static GameOverScreen getGameOverScreen(PixelTower pixelTower)
    {
        if(gameOverScreen == null)
        {
            GameScreen bgScreen = getGameScreen(pixelTower);
            gameOverScreen = new GameOverScreen(pixelTower, bgScreen, bgScreen.renderer);
        }
        return gameOverScreen;
    }

    public static OverlayScreen getOverlayScreen()
    {
        if(overlayScreen == null)
            overlayScreen = new OverlayScreen();
        return overlayScreen;
    }

    public static CookiesAndBeerScreen getCookiesAndBeerScreen(PixelTower pixelTower)
    {
        if(cabScreen == null)
        {
            MenuScreen bgScreen = getMenuScreen(pixelTower);
            GameScreen gameScreen = getGameScreen(pixelTower);
            cabScreen = new CookiesAndBeerScreen(pixelTower, bgScreen, gameScreen.renderer);
        }
        return cabScreen;
    }

    private static GameScreen gameScreen;
    private static SplashScreen splashScreen;
    private static MenuScreen menuScreen;
    private static MenuBackgroundScreen menuBackgroundScreen;
    private static HighscoreScreen highscoreScreen;
    private static CreditsScreen creditsScreen;
    private static PauseScreen pauseScreen;
    private static GameOverScreen gameOverScreen;
    private static OverlayScreen overlayScreen;
    private static CookiesAndBeerScreen cabScreen;
}
