// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClickableArea.java

package com.thegreystudios.pixeltower.ui;

import com.badlogic.gdx.math.Rectangle;

public class ClickableArea
{
    public static interface ClickListener
    {

        public abstract void onTouchDown(ClickableArea clickablearea);

        public abstract void onTouchUp(ClickableArea clickablearea);
    }


    public ClickableArea(Rectangle boundaries)
    {
        this.boundaries = new Rectangle();
        this.boundaries = boundaries;
    }

    public boolean touchDown(int x, int y)
    {
        if((float)x > boundaries.x && (float)x < boundaries.x + boundaries.width && (float)y > boundaries.y && (float)y < boundaries.y + boundaries.height)
        {
            pressed = true;
            if(listener != null)
                listener.onTouchDown(this);
        } else
        {
            pressed = false;
        }
        return pressed;
    }

    public boolean touchUp(int x, int y)
    {
        pressed = false;
        if((float)x > boundaries.x && (float)x < boundaries.x + boundaries.width && (float)y > boundaries.y && (float)y < boundaries.y + boundaries.height)
        {
            if(listener != null)
                listener.onTouchUp(this);
            return true;
        } else
        {
            return false;
        }
    }

    public Rectangle boundaries;
    public boolean pressed;
    public ClickListener listener;
}
