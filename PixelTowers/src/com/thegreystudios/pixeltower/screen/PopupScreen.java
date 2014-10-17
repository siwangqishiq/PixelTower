// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PopupScreen.java

package com.thegreystudios.pixeltower.screen;

import com.thegreystudios.pixeltower.PixelTower;

// Referenced classes of package com.thegreystudios.pixeltower.screen:
//            Screen

public class PopupScreen extends Screen
{

    public PopupScreen(PixelTower pixelTower, Screen parent, boolean updateParent)
    {
        super(pixelTower);
        this.parent = parent;
        this.updateParent = updateParent;
    }

    public void update(float deltaTime)
    {
        if(updateParent)
            parent.update(deltaTime);
    }

    public void present(float deltaTime)
    {
        parent.present(deltaTime);
    }

    public void pause()
    {
        parent.pause();
    }

    public void resume()
    {
        parent.resume();
    }

    public void dispose()
    {
    }

    protected Screen parent;
    protected boolean updateParent;
}
