
package com.thegreystudios.pixeltower.world;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.thegreystudios.pixeltower.ai.Person;
import com.thegreystudios.pixeltower.blocks.*;
import com.thegreystudios.pixeltower.gfx.*;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;
import com.thegreystudios.pixeltower.status.GameStatus;

public class World
{

    public World(boolean spawnPeople)
    {
        groundPeople = new Array();
        minX = 0.0F;
        maxX = 0.0F;
        ufoSighting = false;
        blocks = new BlocksMap(10);
        setupParticlePools();
        if(spawnPeople)
            setupGroundPeople();
    }

    private void setupGroundPeople()
    {
        groundBlock = new Block(1, 0, blocks, null);
        for(int i = 0; i < 35; i++)
        {
            Person person = new Person(MathUtils.random(5), groundBlock, blocks);
            person.x = MathUtils.random(0, 120);
            person.y = 0.0F;
            if(MathUtils.randomBoolean())
                person.state = 2;
            else
                person.state = 1;
            groundPeople.add(person);
        }

    }

    private void setupParticlePools()
    {
        if(dustEmitterPool == null)
        {
            DustEmitterDescription desc = new DustEmitterDescription();
            desc.particleAmount = 25;
            desc.acceleration.set(0.0F, 5F);
            desc.minPosition.set(0.0F, 0.0F);
            desc.maxPosition.set(16F, 0.0F);
            desc.minVelocity.set(-25F, -5F);
            desc.maxVelocity.set(25F, 5F);
            desc.minLifeTime = 0.3F;
            desc.maxLifeTime = 0.5F;
            desc.minColor = 0.7F;
            desc.maxColor = 0.95F;
            desc.monochrome = true;
            dustEmitterPool = new ParticleEmitterPool(desc, 1);
        }
        if(explosionEmitterPool == null)
        {
            DustEmitterDescription desc = new DustEmitterDescription();
            desc.particleAmount = 64;
            desc.acceleration.set(0.0F, -20F);
            desc.minPosition.set(0.0F, 0.0F);
            desc.maxPosition.set(16F, 16F);
            desc.minVelocity.set(-5F, -10F);
            desc.maxVelocity.set(5F, 15F);
            desc.minLifeTime = 0.5F;
            desc.maxLifeTime = 1.25F;
            desc.monochrome = true;
            desc.minColor = 0.3F;
            desc.maxColor = 0.9F;
            explosionEmitterPool = new ParticleEmitterPool(desc, 2);
        }
        if(bloodEmitterPool == null)
        {
            BloodEmitterDescription desc = new BloodEmitterDescription();
            desc.particleAmount = 20;
            desc.acceleration.set(0.0F, -0.05F);
            desc.minPosition.set(-4F, -1F);
            desc.maxPosition.set(4F, 1.0F);
            desc.minVelocity.set(0.0F, 0.0F);
            desc.maxVelocity.set(0.0F, 0.0F);
            desc.minLifeTime = 5F;
            desc.maxLifeTime = 10F;
            desc.monochrome = true;
            desc.minColor = 0.5F;
            desc.maxColor = 1.0F;
            bloodEmitterPool = new ParticleEmitterPool(desc, 3);
        }
        if(highscoreEmitterPool == null)
        {
            DustEmitterDescription desc = new DustEmitterDescription();
            desc.particleAmount = 500;
            desc.acceleration.set(0.0F, -30F);
            desc.minPosition.set(0.0F, -3F);
            desc.maxPosition.set(200F, 4F);
            desc.minVelocity.set(0.0F, -50F);
            desc.maxVelocity.set(0.0F, 10F);
            desc.minLifeTime = 1.0F;
            desc.maxLifeTime = 1.5F;
            desc.minColor = 0.0F;
            desc.maxColor = 1.0F;
            desc.monochrome = false;
            highscoreEmitterPool = new ParticleEmitterPool(desc, 4);
        }
    }

    public void update(float deltaTime)
    {
        if(deltaTime > 0.2F)
            deltaTime = 0.2F;
        if(GameStatus.cameraHeight >= 1000F && !GameStatus.ufoSighted)
        {
            GameStatus.ufoSighted = true;
            SoundLibrary.ufoSound.play();
            ufoSighting = true;
            ufoX = -5F;
            ufoY = GameStatus.cameraHeight + 60F;
            ufoStartY = GameStatus.cameraHeight + 60F;
        }
        if(ufoSighting)
        {
            stateTime += deltaTime;
            ufoX += 40F * deltaTime;
            ufoY = (float)((double)ufoStartY + Math.sin(stateTime * 5D) * 10D);
            if(ufoX >= 200F)
            {
                ufoSighting = false;
                SoundLibrary.ufoSound.play();
            }
        }
        if(currentGroup != null)
        {
            currentGroup.update(deltaTime);
            currentGroupCentre = (int)(((Block)currentGroup.blocks.get(0)).x + (float)((currentGroup.width * 15) / 2));
        }
        blocks.update(deltaTime);
        if(GameStatus.height == 1 && !GameStatus.firstDropProcessed && GameStatus.firstDropImpact)
        {
            GameStatus.firstDropProcessed = true;
            minX = 0.0F;
            maxX = 0.0F;
            for(int x = 0; x < 8; x++)
            {
                if(blocks.getBlock(x, 0) == null)
                    continue;
                minX = (int)blocks.getBlock(x, 0).x;
                break;
            }

            for(int x = 7; x >= 0; x--)
            {
                if(blocks.getBlock(x, 0) == null)
                    continue;
                maxX = (int)blocks.getBlock(x, 0).x + 15;
                break;
            }

            for(int i = 0; i < groundPeople.size; i++)
            {
                Person person = (Person)groundPeople.get(i);
                if(person.x >= minX && person.x <= maxX)
                {
                    showBloodParticle((int)person.x, (int)person.y);
                    groundPeople.removeIndex(i);
                    i--;
                } else
                if(person.x < minX)
                    person.state = 1;
                else
                    person.state = 2;
            }

        }
        for(int i = 0; i < groundPeople.size; i++)
        {
            Person person = (Person)groundPeople.get(i);
            person.stateTime += deltaTime;
            if(person.state == 1)
            {
                if(GameStatus.height >= 1)
                    person.x -= Person.WALK_SPEED * 2.0F * deltaTime;
                else
                    person.x -= Person.WALK_SPEED * deltaTime;
                if(person.x < 0.0F && GameStatus.height < 1)
                    person.x = 121F;
            } else
            {
                if(GameStatus.height >= 1)
                    person.x += Person.WALK_SPEED * 2.0F * deltaTime;
                else
                    person.x += Person.WALK_SPEED * deltaTime;
                if(person.x > 120F && GameStatus.height < 1)
                    person.x = -1F;
            }
        }

        updateParticleEmitters(deltaTime);
    }

    public void updateParticleEmitters(float deltaTime)
    {
        for(int i = 0; i < particleEmitters.size; i++)
        {
            ParticleEmitter emitter = (ParticleEmitter)particleEmitters.get(i);
            if(emitter.active)
            {
                emitter.update(deltaTime);
            } else
            {
                if(emitter.type == 1)
                {
                    dustEmitterPool.free(emitter);
                    particleEmitters.removeIndex(i);
                    i--;
                }
                if(emitter.type == 2)
                {
                    explosionEmitterPool.free(emitter);
                    particleEmitters.removeIndex(i);
                    i--;
                }
                if(emitter.type == 3)
                {
                    bloodEmitterPool.free(emitter);
                    particleEmitters.removeIndex(i);
                    i--;
                }
            }
        }

    }

    public void reset()
    {
        groundPeople.clear();
        setupGroundPeople();
        blocks.reset();
        currentGroup = null;
    }

    public static void showDustParticle(int x, int y)
    {
        ParticleEmitter emitter = (ParticleEmitter)dustEmitterPool.obtain();
        emitter.x = x;
        emitter.y = y;
        emitter.start();
        particleEmitters.add(emitter);
    }

    public static void showExplosionParticle(int x, int y)
    {
        ParticleEmitter emitter = (ParticleEmitter)explosionEmitterPool.obtain();
        emitter.x = x;
        emitter.y = y;
        emitter.start();
        particleEmitters.add(emitter);
    }

    public static void showBloodParticle(int x, int y)
    {
        ParticleEmitter emitter = (ParticleEmitter)bloodEmitterPool.obtain();
        emitter.x = x;
        emitter.y = y;
        emitter.start();
        particleEmitters.add(emitter);
    }

    public static void showHighscoreParticle(int x, int y)
    {
        ParticleEmitter emitter = (ParticleEmitter)highscoreEmitterPool.obtain();
        emitter.x = x;
        emitter.y = y;
        emitter.start();
        particleEmitters.add(emitter);
    }

    public static final int WIDTH = 120;
    public static final int HEIGHT = 200;
    public BlocksMap blocks;
    public BlockGroup currentGroup;
    public int currentGroupCentre;
    public static ParticleEmitterPool dustEmitterPool;
    public static ParticleEmitterPool explosionEmitterPool;
    public static ParticleEmitterPool bloodEmitterPool;
    public static ParticleEmitterPool highscoreEmitterPool;
    public static ParticleEmitterPool slowDownEmitterPool;
    public static ParticleEmitterPool bonusBlockEmitterPool;
    public static Array particleEmitters = new Array();
    public Array groundPeople;
    Block groundBlock;
    float minX;
    float maxX;
    public boolean ufoSighting;
    public float ufoX;
    public float ufoY;
    private double stateTime;
    private float ufoStartY;

}
