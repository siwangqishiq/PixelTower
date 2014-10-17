package com.thegreystudios.pixeltower.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class AILevel
{
  public Array<Person> people = new Array<Person>();

  public Array<AIPoint> spawnPoints = new Array<AIPoint>();
  public Array<AIPoint> movePoints = new Array<AIPoint>();
  public Array<AIPoint> doorPoints = new Array<AIPoint>();
  public Array<AIPoint> sitPoints = new Array<AIPoint>();
  public Array<AIPoint> sleepPoints = new Array<AIPoint>();
  public int x;
  public int y;
  public boolean active;

  public void update(float deltaTime)
  {
    for (int i = 0; i < this.people.size; i++) {
      Person person = (Person)this.people.get(i);

      if (person.stateTime > person.nextTaskDuration) {
        person.stateTime = 0.0F;
        person.nextTaskDuration = MathUtils.random(4.0F, 15.0F);

        int rnd = MathUtils.random(3);

        if (rnd == 0)
        {
          person.currentAIPoint = ((AIPoint)this.movePoints.get(MathUtils.random(this.movePoints.size - 1)));
        }
        else if (rnd == 1)
        {
          person.currentAIPoint = ((AIPoint)this.movePoints.get(MathUtils.random(this.movePoints.size - 1)));
        }
        else if (rnd == 2)
        {
          person.currentAIPoint = ((AIPoint)this.movePoints.get(MathUtils.random(this.movePoints.size - 1)));
        } else {
          if (rnd != 3)
            continue;
          person.currentAIPoint = ((AIPoint)this.movePoints.get(MathUtils.random(this.movePoints.size - 1)));
        }
      }
      else {
        person.update(deltaTime);
      }
    }
  }
}