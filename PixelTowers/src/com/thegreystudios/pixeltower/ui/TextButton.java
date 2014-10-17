// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TextButton.java

package com.thegreystudios.pixeltower.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class TextButton
{
    public static interface ClickListener
    {

        public abstract void onTouchDown(TextButton textbutton);

        public abstract void onTouchUp(TextButton textbutton);
    }


    public TextButton(String text, Color downColor, Color upColor, BitmapFont font)
    {
        x = 0;
        y = 0;
        boundaries = new Rectangle();
        this.downColor = downColor;
        this.upColor = upColor;
        this.text = text;
        this.font = font;
        calculateBoundaries();
    }

    private void calculateBoundaries()
    {
        com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds bounds = font.getBounds(text);
        boundaries.x = x - 2;
        boundaries.y = y - 2;
        boundaries.width = bounds.width + 4F;
        boundaries.height = bounds.height + 4F;
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
        calculateBoundaries();
    }

    public void render(SpriteBatch batch)
    {
        if(pressed)
            font.setColor(upColor);
        else
            font.setColor(downColor);
        font.draw(batch, text, x, (float)y + font.getLineHeight());
        font.setColor(Color.WHITE);
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

    public int x;
    public int y;
    public Color downColor;
    public Color upColor;
    public String text;
    public BitmapFont font;
    public Rectangle boundaries;
    public boolean pressed;
    public ClickListener listener;
}
