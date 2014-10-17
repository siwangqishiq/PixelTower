
package com.thegreystudios.pixeltower.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.thegreystudios.pixeltower.gfx.ParticleEmitter;

public class ParticleRenderer
{

    public ParticleRenderer(SpriteBatch batch, TextureAtlas atlas)
    {
        this.batch = batch;
        loadAssets(atlas);
    }

    private void loadAssets(TextureAtlas atlas)
    {
        dustParticle = atlas.findRegion("dustParticle");
        explosionParticle = atlas.findRegion("explosionParticle");
        bloodParticle = atlas.findRegion("bloodParticle");
        highscoreParticle = atlas.findRegion("highscoreParticle");
    }

    public void renderParticleEmitter(ParticleEmitter emitter)
    {
        if(emitter.type == 1)
        {
            for(int i = 0; i < emitter.particles.size; i++)
            {
                com.thegreystudios.pixeltower.gfx.ParticleEmitter.Particle p = (com.thegreystudios.pixeltower.gfx.ParticleEmitter.Particle)emitter.particles.get(i);
                batch.setColor(p.color);
                batch.draw(dustParticle, (int)(p.position.x + (float)emitter.x), (int)(p.position.y + (float)emitter.y));
            }

        }
        if(emitter.type == 2)
        {
            for(int i = 0; i < emitter.particles.size; i++)
            {
                com.thegreystudios.pixeltower.gfx.ParticleEmitter.Particle p = (com.thegreystudios.pixeltower.gfx.ParticleEmitter.Particle)emitter.particles.get(i);
                batch.setColor(p.color);
                batch.draw(explosionParticle, (int)(p.position.x + (float)emitter.x), (int)(p.position.y + (float)emitter.y));
            }

        }
        if(emitter.type == 3)
        {
            for(int i = 0; i < emitter.particles.size; i++)
            {
                com.thegreystudios.pixeltower.gfx.ParticleEmitter.Particle p = (com.thegreystudios.pixeltower.gfx.ParticleEmitter.Particle)emitter.particles.get(i);
                batch.setColor(p.color);
                batch.draw(bloodParticle, (int)(p.position.x + (float)emitter.x), (int)(p.position.y + (float)emitter.y));
            }

        }
        if(emitter.type == 4)
        {
            for(int i = 0; i < emitter.particles.size; i++)
            {
                com.thegreystudios.pixeltower.gfx.ParticleEmitter.Particle p = (com.thegreystudios.pixeltower.gfx.ParticleEmitter.Particle)emitter.particles.get(i);
                batch.setColor(p.color);
                batch.draw(highscoreParticle, (int)(p.position.x + (float)emitter.x), (int)(p.position.y + (float)emitter.y));
            }

        }
        batch.setColor(Color.WHITE);
    }

    SpriteBatch batch;
    TextureRegion dustParticle;
    TextureRegion explosionParticle;
    TextureRegion bloodParticle;
    TextureRegion highscoreParticle;
}
