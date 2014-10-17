
package com.thegreystudios.pixeltower.renderers;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.thegreystudios.pixeltower.ai.Person;
import com.thegreystudios.pixeltower.blocks.Block;
import com.thegreystudios.pixeltower.status.GameStatus;

public class PeopleRenderer
{

    public PeopleRenderer(SpriteBatch batch, TextureAtlas atlas)
    {
        walk = new Animation[6];
        sitRight = new TextureRegion[6];
        sitLeft = new TextureRegion[6];
        sleepRight = new TextureRegion[6];
        sleepLeft = new TextureRegion[6];
        idle = new TextureRegion[6];
        this.batch = batch;
        loadAssets(atlas);
    }

    private void loadAssets(TextureAtlas atlas)
    {
        for(int i = 1; i <= 6; i++)
        {
            walk[i - 1] = new Animation(0.25F, atlas.findRegion((new StringBuilder("man0")).append(i).append("Walk").toString()).split(3, 3)[0]);
            sitRight[i - 1] = atlas.findRegion((new StringBuilder("man0")).append(i).append("SitRight").toString());
            sitLeft[i - 1] = atlas.findRegion((new StringBuilder("man0")).append(i).append("SitLeft").toString());
            sleepRight[i - 1] = atlas.findRegion((new StringBuilder("man0")).append(i).append("SleepRight").toString());
            sleepLeft[i - 1] = atlas.findRegion((new StringBuilder("man0")).append(i).append("SleepLeft").toString());
            idle[i - 1] = atlas.findRegion((new StringBuilder("man0")).append(i).append("Walk").toString()).split(3, 3)[0][0];
        }

    }

    public void renderPeople(Array people)
    {
        for(int i = 0; i < people.size; i++)
        {
            Person person = (Person)people.get(i);
            if(person.owner.y >= GameStatus.cameraHeight - 130F && person.owner.y <= GameStatus.cameraHeight + 105F)
            {
                renderedPeopleCount++;
                if(person.state == 0 || person.state == 4)
                    batch.draw(idle[person.type], person.x + person.owner.x, person.y + person.owner.y);
                else
                if(person.state == 1)
                    batch.draw(walk[person.type].getKeyFrame(person.stateTime, true), person.x + person.owner.x, person.y + person.owner.y);
                else
                if(person.state == 2)
                    batch.draw(walk[person.type].getKeyFrame(person.stateTime, true), person.x + person.owner.x, person.y + person.owner.y);
            }
        }

    }

    public void renderPeople(Block block)
    {
        for(int i = 0; i < block.people.size; i++)
        {
            Person person = (Person)block.people.get(i);
            if(block.y >= GameStatus.cameraHeight - 130F && block.y <= GameStatus.cameraHeight + 105F)
            {
                renderedPeopleCount++;
                if(person.state == 0 || person.state == 4)
                    batch.draw(idle[person.type], person.x + block.x, person.y + block.y);
                else
                if(person.state == 1)
                    batch.draw(walk[person.type].getKeyFrame(person.stateTime, true), person.x + block.x, person.y + block.y);
                else
                if(person.state == 2)
                    batch.draw(walk[person.type].getKeyFrame(person.stateTime, true), person.x + block.x, person.y + block.y);
            }
        }

    }

    public static int renderedPeopleCount;
    private SpriteBatch batch;
    Animation walk[];
    TextureRegion sitRight[];
    TextureRegion sitLeft[];
    TextureRegion sleepRight[];
    TextureRegion sleepLeft[];
    TextureRegion idle[];
}
