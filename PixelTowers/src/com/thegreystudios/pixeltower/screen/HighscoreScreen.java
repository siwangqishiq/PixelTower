
package com.thegreystudios.pixeltower.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector3;
import com.thegreystudios.pixeltower.*;
import com.thegreystudios.pixeltower.renderers.WorldRenderer;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;
import com.thegreystudios.pixeltower.status.GameStatus;
import com.thegreystudios.pixeltower.status.LocalStorage;
import com.thegreystudios.pixeltower.ui.TextButton;

public class HighscoreScreen extends PopupScreen
    implements InputProcessor
{
    public class HighscoreEntry
    {

        public int x;
        public int y;
        public String name;
        public String rank;
        public String score;
        public boolean player;
 
        public HighscoreEntry()
        {
       
        }
    }


    public HighscoreScreen(PixelTower pixelTower, Screen parent, WorldRenderer renderer)
    {
        super(pixelTower, parent, true);
        buttonUp = new Color(1.0F, 0.9568627F, 0.1568628F, 1.0F);
        buttonDown = new Color(1.0F, 1.0F, 0.509804F, 1.0F);
        highlightColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        entries = new HighscoreEntry[15];
        projectionVector = new Vector3();
        currentMode = -1;
        loadAssets(renderer.atlas);
        font = renderer.font;
        camera = new OrthographicCamera(120F, 200F);
        camera.position.set(60F, 100F, 0.0F);
        camera.update();
        batch = new SpriteBatch();
        batch.setShader(null);
        batch.setProjectionMatrix(camera.combined);
        setupUI();
        setupEntries();
        setHighscores(0, true);
    }

    private void setupEntries()
    {
        for(int i = 0; i < entries.length; i++)
        {
            HighscoreEntry entry = new HighscoreEntry();
            entry.x = 25;
            entry.y = 170 - i * 7;
            entry.rank = (new StringBuilder(String.valueOf(i + 1))).append(".").toString();
            entries[i] = entry;
        }

    }

    private void setHighscores(int mode, boolean refresh)
    {
        if(currentMode != mode || refresh)
        {
            currentMode = mode;
            switch(mode)
            {
            default:
                break;

            case 0: // '\0'
                int localScores[] = LocalStorage.getLocalHighscores();
                for(int i = 0; i < entries.length; i++)
                {
                    entries[i].rank = (new StringBuilder()).append(i + 1).toString();
                    entries[i].player = false;
                    if(localScores[i] != -1)
                        entries[i].score = (new StringBuilder()).append(localScores[i]).toString();
                    else
                        entries[i].score = "-";
                    if(LocalStorage.getUserName().toUpperCase().length() > 7)
                        entries[i].name = LocalStorage.getUserName().toUpperCase().substring(0, 7);
                    else
                        entries[i].name = LocalStorage.getUserName().toUpperCase();
                }

                break;

            case 1: // '\001'
                PixelTowerScore globalScores[] = LocalStorage.getGlobalHighscores();
                if(Gdx.app.getType() != com.badlogic.gdx.Application.ApplicationType.Android)
                    break;
                for(int i = 0; i < entries.length; i++)
                {
                    PixelTowerScore score = globalScores[i];
                    entries[i].rank = (new StringBuilder()).append(score.rank).toString();
                    if(score.score != -1D)
                    {
                        entries[i].score = (new StringBuilder()).append((int)score.score).toString();
                        if(score.name.equals(LocalStorage.getUserName()))
                            entries[i].player = true;
                        else
                            entries[i].player = false;
                        if(score.name.length() > 7)
                            entries[i].name = score.name.toUpperCase().substring(0, 7);
                        else
                            entries[i].name = score.name.toUpperCase();
                    } else
                    {
                        entries[i].score = "-";
                        entries[i].name = "---";
                    }
                }

                break;

            case 2: // '\002'
                PixelTowerScore todaysScores[] = LocalStorage.getTodaysHighscores();
                if(Gdx.app.getType() != com.badlogic.gdx.Application.ApplicationType.Android)
                    break;
                for(int i = 0; i < entries.length; i++)
                {
                    PixelTowerScore score = todaysScores[i];
                    entries[i].rank = (new StringBuilder()).append(score.rank).toString();
                    if(score.score != -1D)
                    {
                        entries[i].score = (new StringBuilder()).append((int)score.score).toString();
                        if(score.name.equals(LocalStorage.getUserName()))
                            entries[i].player = true;
                        else
                            entries[i].player = false;
                        if(score.name.length() > 7)
                            entries[i].name = score.name.toUpperCase().substring(0, 7);
                        else
                            entries[i].name = score.name.toUpperCase();
                    } else
                    {
                        entries[i].score = "-";
                        entries[i].name = "---";
                    }
                }

                break;
            }
        }
    }

    private void loadAssets(TextureAtlas atlas)
    {
        highscoreBanner = atlas.findRegion("highscoreBanner01");
    }

    private void setupUI()
    {
        backButton = new TextButton("MENU", buttonUp, buttonDown, font);
        backButton.setPosition(3, 2);
        backButton.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                pixelTower.setScreen(ScreenLibrary.getMenuScreen(pixelTower));
                SoundLibrary.selectSound.play();
            }

       
        }
;
        refreshButton = new TextButton("REFRESH", buttonUp, buttonDown, font);
        refreshButton.setPosition(88, 2);
        refreshButton.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                if(Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.Android)
                    pixelTower.actionResolver.fetchHighscores();
                SoundLibrary.selectSound.play();
            }

 
        }
;
        localButton = new TextButton("LOCAL", buttonUp, buttonDown, font);
        localButton.setPosition(19, 40);
        localButton.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                setHighscores(0, true);
                SoundLibrary.selectSound.play();
            }

   
        }
;
        globalButton = new TextButton("GLOBAL", buttonUp, buttonDown, font);
        globalButton.setPosition(47, 40);
        globalButton.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                setHighscores(1, true);
                SoundLibrary.selectSound.play();
            }

 
        }
;
        todayButton = new TextButton("TODAY", buttonUp, buttonDown, font);
        todayButton.setPosition(78, 40);
        todayButton.listener = new com.thegreystudios.pixeltower.ui.TextButton.ClickListener() {

            public void onTouchDown(TextButton textbutton)
            {
            }

            public void onTouchUp(TextButton button)
            {
                setHighscores(2, true);
                SoundLibrary.selectSound.play();
            }

        }
;
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    public void present(float deltaTime)
    {
        super.present(deltaTime);
        batch.begin();
        batch.draw(highscoreBanner, 8F, 28F);
        backButton.render(batch);
        refreshButton.render(batch);
        localButton.render(batch);
        globalButton.render(batch);
        todayButton.render(batch);
        for(int i = 0; i < entries.length; i++)
            renderEntry(entries[i]);

        if(currentMode == 0)
            font.draw(batch, "LOCAL HIGHSCORES", 27F, 185F);
        else
        if(currentMode == 1)
        {
            font.draw(batch, "GLOBAL HIGHSCORES", 26F, 185F);
            if(GameStatus.askingForGlobalHighscores)
                font.draw(batch, "UPDATING ...", 25F, 63F);
        } else
        if(currentMode == 2)
        {
            font.draw(batch, "TODAY'S HIGHSCORES", 26F, 185F);
            if(GameStatus.askingForTodaysHighscores)
                font.draw(batch, "UPDATING ...", 25F, 63F);
        }
        batch.end();
    }

    public void refresh()
    {
        setHighscores(currentMode, true);
    }

    private void renderEntry(HighscoreEntry entry)
    {
        if(entry != null && entry.name == null)
        {
            font.setColor(Color.WHITE);
            font.draw(batch, entry.rank, entry.x - 4, entry.y);
            font.draw(batch, entry.score, entry.x + 10, entry.y);
        } else
        if(entry != null)
        {
            if(entry.player)
                font.setColor(buttonDown);
            else
                font.setColor(Color.WHITE);
            font.draw(batch, entry.rank, entry.x - 4, entry.y);
            font.draw(batch, entry.name, entry.x + 18, entry.y);
            font.draw(batch, entry.score, entry.x + 51, entry.y);
        }
    }

    public boolean keyDown(int keycode)
    {
        if(keycode == 4)
            pixelTower.setScreen(ScreenLibrary.getMenuScreen(pixelTower));
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
        projectionVector.set(x, y, 0.0F);
        camera.unproject(projectionVector);
        backButton.touchDown((int)projectionVector.x, (int)projectionVector.y);
        refreshButton.touchDown((int)projectionVector.x, (int)projectionVector.y);
        localButton.touchDown((int)projectionVector.x, (int)projectionVector.y);
        globalButton.touchDown((int)projectionVector.x, (int)projectionVector.y);
        todayButton.touchDown((int)projectionVector.x, (int)projectionVector.y);
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button)
    {
        projectionVector.set(x, y, 0.0F);
        camera.unproject(projectionVector);
        backButton.touchUp((int)projectionVector.x, (int)projectionVector.y);
        refreshButton.touchUp((int)projectionVector.x, (int)projectionVector.y);
        localButton.touchUp((int)projectionVector.x, (int)projectionVector.y);
        globalButton.touchUp((int)projectionVector.x, (int)projectionVector.y);
        todayButton.touchUp((int)projectionVector.x, (int)projectionVector.y);
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

    public static final int LOCAL = 0;
    public static final int GLOBAL = 1;
    public static final int TODAY = 2;
    Color buttonUp;
    Color buttonDown;
    Color highlightColor;
    TextureRegion highscoreBanner;
    SpriteBatch batch;
    OrthographicCamera camera;
    TextButton backButton;
    TextButton localButton;
    TextButton globalButton;
    TextButton todayButton;
    TextButton refreshButton;
    HighscoreEntry entries[];
    BitmapFont font;
    private Vector3 projectionVector;
    public int currentMode;

}
