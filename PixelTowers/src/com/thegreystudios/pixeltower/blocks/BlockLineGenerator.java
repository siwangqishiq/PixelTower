
package com.thegreystudios.pixeltower.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.thegreystudios.pixeltower.status.GameStatus;

public class BlockLineGenerator
{

    public BlockLineGenerator()
    {
    }

    public static BlockGroup getNewBlockLine(int width, int height, BlocksMap map)
    {
        BlockGroup group = null;
        int rnd = MathUtils.random(1000);
        if(rnd < 800 || bonusDelay)
        {
            group = new BlockGroup();
            bonusDelay = false;
        } else
        if(rnd < 875)
        {
            group = new BlockGroup(5);
            bonusDelay = true;
        } else
        if(rnd < 925)
        {
            group = new BlockGroup(4);
            bonusDelay = true;
        } else 
        {
            group = new BlockGroup(3);
            bonusDelay = true;
        }
        group.width = width;
        group.height = height;
        group.map = map;
        float currentVelocity = GameStatus.currentSpeed;
        int startingPosition;
        if(MathUtils.randomBoolean())
        {
            startingPosition = -width;
        } else
        {
            startingPosition = 8;
            currentVelocity *= -1F;
        }
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                if(y == 0 && x == 0)
                    group.baseHeight = GameStatus.height + 1;
                if(width == 1)
                {
                    Block block = getSingleBlock(group.type, map);
                    block.bx = startingPosition + x;
                    block.by = GameStatus.height + 1 + y;
                    block.fixPosition();
                    block.renderWallLeft = block.renderWallRight = true;
                    block.velocity.set(currentVelocity, 0.0F);
                    group.add(block);
                } else
                if(x == 0)
                {
                    Block block = getLeftBlock(group.type, map);
                    block.bx = startingPosition + x;
                    block.by = GameStatus.height + 1 + y;
                    block.fixPosition();
                    block.renderWallLeft = true;
                    block.velocity.set(currentVelocity, 0.0F);
                    group.add(block);
                } else
                if(x == width - 1)
                {
                    Block block = getRightBlock(group.type, map);
                    block.bx = startingPosition + x;
                    block.by = GameStatus.height + 1 + y;
                    block.fixPosition();
                    block.renderWallRight = true;
                    block.velocity.set(currentVelocity, 0.0F);
                    group.add(block);
                } else
                {
                    Block block = getMiddleBlock(group.type, map);
                    block.bx = startingPosition + x;
                    block.by = GameStatus.height + 1 + y;
                    block.fixPosition();
                    block.velocity.set(currentVelocity, 0.0F);
                    group.add(block);
                }
            }

        }

        return group;
    }

    private static Block getLeftBlock(int type, BlocksMap map)
    {
        if(type == 4)
            return getBiblioLeftBlock(map);
        if(type == 5)
            return getAteriumLeftBlock(map);
        if(type == 3)
            return getLabLeftBlock(map);
        else
            return getNormalLeftBlock(map);
    }

    private static Block getMiddleBlock(int type, BlocksMap map)
    {
        if(type == 4)
            return getBiblioMiddleBlock(map);
        if(type == 5)
            return getAteriumMiddleBlock(map);
        if(type == 3)
            return getLabMiddleBlock(map);
        else
            return getNormalMiddleBlock(map);
    }

    private static Block getRightBlock(int type, BlocksMap map)
    {
        if(type == 4)
            return getBiblioRightBlock(map);
        if(type == 5)
            return getAteriumRightBlock(map);
        if(type == 3)
            return getLabRightBlock(map);
        else
            return getNormalRightBlock(map);
    }

    private static Block getSingleBlock(int type, BlocksMap map)
    {
        if(type == 4)
            return getBiblioMiddleBlock(map);
        if(type == 5)
            return getAteriumMiddleBlock(map);
        if(type == 3)
            return getLabMiddleBlock(map);
        if(MathUtils.random(5) == 5)
            return getNormalStaircase(map);
        else
            return getNormalMiddleBlock(map);
    }

    public static Block getLabLeftBlock(BlocksMap map)
    {
        return new Block(4, MathUtils.random(1), map, null);
    }

    public static Block getLabMiddleBlock(BlocksMap map)
    {
        return new Block(5, MathUtils.random(2), map, null);
    }

    public static Block getLabRightBlock(BlocksMap map)
    {
        return new Block(6, MathUtils.random(1), map, null);
    }

    public static Block getAteriumLeftBlock(BlocksMap map)
    {
        return new Block(7, MathUtils.random(1), map, null);
    }

    public static Block getAteriumMiddleBlock(BlocksMap map)
    {
        return new Block(8, MathUtils.random(2), map, null);
    }

    public static Block getAteriumRightBlock(BlocksMap map)
    {
        return new Block(9, MathUtils.random(1), map, null);
    }

    public static Block getBiblioLeftBlock(BlocksMap map)
    {
        return new Block(10, MathUtils.random(1), map, null);
    }

    public static Block getBiblioMiddleBlock(BlocksMap map)
    {
        return new Block(11, MathUtils.random(2), map, null);
    }

    public static Block getBiblioRightBlock(BlocksMap map)
    {
        return new Block(12, MathUtils.random(1), map, null);
    }

    public static Block getNormalLeftBlock(BlocksMap map)
    {
        return new Block(1, MathUtils.random(3), map, null);
    }

    public static Block getNormalRightBlock(BlocksMap map)
    {
        return new Block(3, MathUtils.random(3), map, null);
    }

    public static Block getNormalMiddleBlock(BlocksMap map)
    {
        return new Block(2, MathUtils.random(6), map, null);
    }

    private static Block getNormalStaircase(BlocksMap map)
    {
        return new Block(13, 0, map, null);
    }

    public static boolean bonusDelay = false;

}
