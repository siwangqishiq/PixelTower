
package com.thegreystudios.pixeltower.gfx;


public abstract class ParticleEmitterDescription
{

    public ParticleEmitterDescription()
    {
    }

    public abstract ParticleEmitter.Particle emitParticle();

    public int particleAmount;
}
