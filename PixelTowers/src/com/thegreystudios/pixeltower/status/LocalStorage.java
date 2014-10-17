
package com.thegreystudios.pixeltower.status;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.Array;
import com.thegreystudios.pixeltower.*;

public class LocalStorage
{

    private static int localScores[] = new int[20];
    private static PixelTowerScore globalScores[] = new PixelTowerScore[20];
    private static PixelTowerScore todaysScores[] = new PixelTowerScore[20];
    private static boolean dirty;
    private static int playCount;
    private static String user;
    private static int userGlobalRank;
    private static boolean declinedCAB;
    public LocalStorage()
    {
    }

    public static synchronized void load()
    {
        Preferences preferences = Gdx.app.getPreferences(".PixelTowers");
        for(int i = 0; i < 20; i++)
        {
            localScores[i] = preferences.getInteger((new StringBuilder("lScore")).append(i).toString(), -1);
            if(Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.Android)
            {
                String glName = preferences.getString((new StringBuilder("glName")).append(i).toString(), null);
                long glScore = preferences.getLong((new StringBuilder("glScore")).append(i).toString(), -1L);
                int glRank = preferences.getInteger((new StringBuilder("glRank")).append(i).toString(), -1);
                globalScores[i] = new PixelTowerScore(glName, glScore, glRank);
                String tName = preferences.getString((new StringBuilder("tName")).append(i).toString(), null);
                long tScore = preferences.getLong((new StringBuilder("tScore")).append(i).toString(), -1L);
                int tRank = preferences.getInteger((new StringBuilder("tRank")).append(i).toString(), -1);
                todaysScores[i] = new PixelTowerScore(tName, tScore, tRank);
            }
            user = preferences.getString("User", "Local");
            userGlobalRank = preferences.getInteger("Rank", 99);
            playCount = preferences.getInteger("playCount", 0);
            declinedCAB = preferences.getBoolean("declinedCAB", false);
        }

    }

    public static synchronized void putHighscore(int highscore)
    {
        int newScorePosition = -1;
        for(int i = 0; i < 20; i++)
        {
            if(highscore <= localScores[i])
                continue;
            newScorePosition = i;
            break;
        }

        if(newScorePosition != -1)
        {
            for(int i = 19; i > newScorePosition; i--)
                localScores[i] = localScores[i - 1];

            localScores[newScorePosition] = highscore;
            dirty = true;
        }
    }

    public static synchronized void putGlobalScores(Array<PixelTowerScore> globalScores) {
        for (int i = 0; i < globalScores.size; i++) {
          globalScores.items[i] = ((PixelTowerScore)globalScores.get(i));
        }
        dirty = true;

        Gdx.app.log("ScoreLoop", "Global Highscores received");
      }

      public static synchronized void putTodaysScores(Array<PixelTowerScore> todaysScores) {
        for (int i = 0; i < todaysScores.size; i++) {
          todaysScores.items[i] = ((PixelTowerScore)todaysScores.get(i));
        }
        for (int i = todaysScores.size; i < todaysScores.size; i++) {
          todaysScores.items[i].reset();
        }
        dirty = true;

        Gdx.app.log("ScoreLoop", "Todays Highscores received");
      }

    public static synchronized void save(PixelTower pixelTower)
    {
        if(Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.Desktop)
        {
            Preferences preferences = Gdx.app.getPreferences(".PixelTowers");
            preferences.clear();
            for(int i = 0; i < 20; i++)
            {
                preferences.putInteger((new StringBuilder("lScore")).append(i).toString(), localScores[i]);
                if(Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.Android)
                {
                    if(globalScores[i] != null)
                    {
                        preferences.putString((new StringBuilder("glName")).append(i).toString(), globalScores[i].name);
                        preferences.putInteger((new StringBuilder("glRank")).append(i).toString(), globalScores[i].rank);
                        preferences.putLong((new StringBuilder("glScore")).append(i).toString(), (long)globalScores[i].score);
                    }
                    if(todaysScores[i] != null)
                    {
                        preferences.putString((new StringBuilder("tName")).append(i).toString(), todaysScores[i].name);
                        preferences.putInteger((new StringBuilder("tRank")).append(i).toString(), todaysScores[i].rank);
                        preferences.putLong((new StringBuilder("tScore")).append(i).toString(), (long)todaysScores[i].score);
                    }
                }
            }

            preferences.putInteger("playCount", playCount);
            preferences.putBoolean("declinedCAB", declinedCAB);
            preferences.flush();
            dirty = false;
        } else
        if(Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.Android)
        {
            pixelTower.actionResolver.savePreferences();
            dirty = false;
        }
    }

    public static synchronized int getUserGlobalRank()
    {
        return userGlobalRank;
    }

    public static synchronized void setUserGlobalRank(int rank)
    {
        userGlobalRank = rank;
    }

    public static synchronized String getUserName()
    {
        return user;
    }

    public static synchronized void setUserName(String i)
    {
        user = i;
    }

    public static synchronized boolean getDeclinedCAB()
    {
        return declinedCAB;
    }

    public static synchronized void setDeclinedCAB(boolean d)
    {
        declinedCAB = d;
    }

    public static synchronized int[] getLocalHighscores()
    {
        return localScores;
    }

    public static synchronized PixelTowerScore[] getGlobalHighscores()
    {
        return globalScores;
    }

    public static synchronized PixelTowerScore[] getTodaysHighscores()
    {
        return todaysScores;
    }

    public static synchronized int getPlayCount()
    {
        return playCount;
    }

    public static synchronized void setPlayCount(int p)
    {
        playCount = p;
    }


}
