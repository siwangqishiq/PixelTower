
package com.thegreystudios.pixeltower.ai;

import com.badlogic.gdx.math.Vector2;
import com.thegreystudios.pixeltower.blocks.Block;
import com.thegreystudios.pixeltower.blocks.BlocksMap;

public class Person
{

    public Person(int type, Block owner, BlocksMap map)
    {
        currentTarget = new Vector2();
        this.type = type;
        this.owner = owner;
        this.map = map;
        setState(4);
    }

    public void update(float deltaTime)
    {
        stateTime += deltaTime;
        if(stateTime < nextTaskDuration)
        {
            if(currentAIPoint.type == 0)
                if((int)currentTarget.x == (int)x)
                    setState(0);
                else
                if(currentTarget.x < x)
                {
                    x -= WALK_SPEED * deltaTime;
                    setState(1);
                } else
                if(currentTarget.x > x)
                {
                    x += WALK_SPEED * deltaTime;
                    setState(2);
                }
        } else
        {
            stateTime = 0.0F;
            map.assignNewMovementPoint(this);
        }
    }

    public void setState(int state)
    {
        if(this.state != state)
            this.state = state;
    }

    public static float WALK_SPEED = 10F;
    public static final int IDLE = 0;
    public static final int WALK_LEFT = 1;
    public static final int WALK_RIGHT = 2;
    public static final int SIT = 3;
    public static final int INACTIVE = 4;
    public float x;
    public float y;
    public int type;
    public int level;
    public int state;
    public float stateTime;
    public float nextTaskDuration;
    public AIPoint currentAIPoint;
    public Vector2 currentTarget;
    public Block owner;
    public BlocksMap map;

}
