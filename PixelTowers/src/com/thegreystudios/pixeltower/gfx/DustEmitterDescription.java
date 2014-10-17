
package com.thegreystudios.pixeltower.gfx;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DustEmitterDescription extends ParticleEmitterDescription
{

    public DustEmitterDescription()
    {
        minPosition = new Vector2();
        maxPosition = new Vector2();
        minVelocity = new Vector2();
        maxVelocity = new Vector2();
        acceleration = new Vector2();
    }

    public ParticleEmitter.Particle emitParticle()
    {
        ParticleEmitter.Particle p = new ParticleEmitter.Particle();
        p.startingAcceleration.set(acceleration);
        p.startingVelocity.x = MathUtils.random(minVelocity.x, maxVelocity.x);
        p.startingVelocity.y = MathUtils.random(minVelocity.y, maxVelocity.y);
        p.startingPosition.x = MathUtils.random(minPosition.x, maxPosition.x);
        p.startingPosition.y = MathUtils.random(minPosition.y, maxPosition.y);
        p.lifeTime = MathUtils.random(minLifeTime, maxLifeTime);
        if(monochrome)
        {
            float color = MathUtils.random(minColor, maxColor);
            p.color.set(color, color, color, 1.0F);
        } else
        {
            p.color.set(MathUtils.random(minColor, maxColor), MathUtils.random(minColor, maxColor), MathUtils.random(minColor, maxColor), 1.0F);
        }
        return p;
    }

    public Vector2 minPosition;
    public Vector2 maxPosition;
    public Vector2 minVelocity;
    public Vector2 maxVelocity;
    public Vector2 acceleration;
    public float minLifeTime;
    public float maxLifeTime;
    public float minColor;
    public float maxColor;
    public boolean monochrome;
}
