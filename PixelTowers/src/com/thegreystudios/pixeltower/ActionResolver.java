
package com.thegreystudios.pixeltower;


public interface ActionResolver
{

    public abstract void showScoreloopScreen();

    public abstract void submitScore(int i);

    public abstract void fetchHighscores();

    public abstract void openTwitter();

    public abstract void openWebsite();

    public abstract void openMarket();

    public abstract void openCAB();

    public abstract void savePreferences();

    public abstract void fetchUserDetails();

    public abstract void fetchUserRank();
}
