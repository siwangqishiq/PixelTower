
package com.thegreystudios.pixeltower.gfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class BonusEmitterDescription extends ParticleEmitterDescription
{

    public BonusEmitterDescription()
    {
        minPosition = new Vector2();
        maxPosition = new Vector2();
        minVelocity = new Vector2();
        maxVelocity = new Vector2();
        acceleration = new Vector2();
        color = new Color();
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
        p.color.set(color);
        return p;
    }

    public Vector2 minPosition;
    public Vector2 maxPosition;
    public Vector2 minVelocity;
    public Vector2 maxVelocity;
    public Vector2 acceleration;
    public float minLifeTime;
    public float maxLifeTime;
    public Color color;
}
