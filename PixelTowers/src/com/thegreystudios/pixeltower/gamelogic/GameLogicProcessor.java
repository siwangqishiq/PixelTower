
package com.thegreystudios.pixeltower.gamelogic;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.MathUtils;
import com.thegreystudios.pixeltower.blocks.*;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;
import com.thegreystudios.pixeltower.status.GameStatus;
import com.thegreystudios.pixeltower.status.LocalStorage;
import com.thegreystudios.pixeltower.world.World;

public class GameLogicProcessor
    implements InputProcessor
{

    public GameLogicProcessor()
    {
        hits = 0;
        world = new World(true);
    }

    public void startNewGame()
    {
        GameStatus.newGame();
        world.reset();
        newLine(1);
    }

    public void dropLine()
    {
        if(((Block)world.currentGroup.blocks.get(0)).bx > 7 || ((Block)world.currentGroup.blocks.get(0)).bx + (world.currentGroup.width - 1) < 0)
            return;
        if(checkForHits() > 0)
        {
            world.blocks.queueBlockGroup(world.currentGroup);
            world.currentGroup.triggerPreBonuses();
            if(world.currentGroup.type == 5 && GameStatus.lineLength < 6)
            {
                if(((Block)world.currentGroup.blocks.get(0)).bx + world.currentGroup.width < 6)
                    world.currentGroup.type = 2;
                else
                    world.currentGroup.type = 1;
            } else
            if(world.currentGroup.type == 5 && GameStatus.lineLength >= 6)
                world.currentGroup.type = 0;
            world.currentGroup.setVelocity(0.0F, -30F);
            world.currentGroup.setAcceleration(0, -250);
            GameStatus.setHeight(GameStatus.height + world.currentGroup.height);
            if(GameStatus.height * 15 > LocalStorage.getLocalHighscores()[0] && LocalStorage.getLocalHighscores()[0] > 0 && !GameStatus.newHighscoreReached)
            {
                GameStatus.newHighscoreReached = true;
                World.showHighscoreParticle(0, LocalStorage.getLocalHighscores()[0] + 5);
            }
            GameStatus.currentSpeed += (float)world.currentGroup.height * 2.5F;
            if(world.currentGroup.type == 4)
                newLine(MathUtils.random(2, 4));
            else
                newLine(1);
        } else
        {
            for(int i = 0; i < world.currentGroup.blocks.size; i++)
                ((Block)world.currentGroup.blocks.get(i)).drop = 200F;

            world.blocks.queueBlockGroup(world.currentGroup);
            world.currentGroup.setVelocity(0.0F, -30F);
            world.currentGroup.setAcceleration(0, -250);
            world.currentGroup = null;
            GameStatus.gameOver();
            LocalStorage.putHighscore(GameStatus.height * 15);
            SoundLibrary.backgroundMusic.stop();
            SoundLibrary.gameOverSound.play();
            Gdx.app.log("Game Logic", "Game Over");
        }
    }

    private int checkForHits()
    {
        hits = 0;
        BlocksMap map = world.blocks;
        for(int i = 0; i < world.currentGroup.blocks.size; i++)
        {
            Block block = (Block)world.currentGroup.blocks.get(i);
            if(GameStatus.height == 0)
            {
                block.supportedByGround = true;
                hits++;
            } else
            if(block.bx >= 0 && block.bx <= 7 && map.getBlock(block.bx, block.group.baseHeight - 2) != null)
            {
                if(block.by == block.group.baseHeight)
                    hits++;
                block.supportedByGround = true;
            }
        }

        GameStatus.lineLength = hits;
        if(GameStatus.lineLength < 5)
            GameStatus.lineMaxed = false;
        return hits;
    }

    public void newLine(int height)
    {
        world.currentGroup = BlockLineGenerator.getNewBlockLine(GameStatus.lineLength, height, world.blocks);
    }

    public void update(float deltaTime)
    {
        world.update(deltaTime);
    }

    public boolean keyDown(int keycode)
    {
        if(Gdx.app.getType() == com.badlogic.gdx.Application.ApplicationType.Desktop && GameStatus.status == 0)
            dropLine();
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
        if(GameStatus.status == 0)
            dropLine();
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int i)
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

    public World world;
    int hits;
}
