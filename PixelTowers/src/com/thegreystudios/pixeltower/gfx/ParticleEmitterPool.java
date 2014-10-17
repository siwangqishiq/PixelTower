package com.thegreystudios.pixeltower.gfx;

import com.badlogic.gdx.utils.Pool;

public class ParticleEmitterPool extends Pool<ParticleEmitter>
{
  ParticleEmitterDescription desc;
  int type;

  public ParticleEmitterPool(ParticleEmitterDescription desc, int type)
  {
    this.desc = desc;
    this.type = type;
  }

  protected ParticleEmitter newObject()
  {
    return new ParticleEmitter(this.desc, this.type);
  }
}