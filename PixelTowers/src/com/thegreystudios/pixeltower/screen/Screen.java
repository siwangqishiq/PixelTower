
package com.thegreystudios.pixeltower.screen;

import com.thegreystudios.pixeltower.PixelTower;

public abstract class Screen
{

    public Screen(PixelTower pixelTower)
    {
        this.pixelTower = pixelTower;
    }

    public abstract void update(float f);

    public abstract void present(float f);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    PixelTower pixelTower;
}
