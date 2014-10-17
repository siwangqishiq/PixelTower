package com.thegreystudios.pixeltower.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.thegreystudios.pixeltower.ai.AIPoint;
import com.thegreystudios.pixeltower.ai.Person;
import com.thegreystudios.pixeltower.ai.RoomLibrary;
import com.thegreystudios.pixeltower.world.World;

public class Block
{
  public static final int SIZE = 15;
  public static final int NORMAL_LEFT = 1;
  public static final int NORMAL_MIDDLE = 2;
  public static final int NORMAL_RIGHT = 3;
  public static final int LAB_LEFT = 4;
  public static final int LAB_MIDDLE = 5;
  public static final int LAB_RIGHT = 6;
  public static final int ATERIUM_LEFT = 7;
  public static final int ATERIUM_MIDDLE = 8;
  public static final int ATERIUM_RIGHT = 9;
  public static final int BIBLIO_LEFT = 10;
  public static final int BIBLIO_MIDDLE = 11;
  public static final int BIBLIO_RIGHT = 12;
  public static final int STAIRS = 13;
  public int type;
  public int variation;
  public int wallVariation = MathUtils.random(1);
  public int studVariation = MathUtils.random(1);
  public int balconyVariation = MathUtils.random(1);
  public boolean active;
  public float x;
  public float y;
  public int bx;
  public int by;
  public float drop;
  public float explodingStateTime;
  public Vector2 velocity = new Vector2();
  public Vector2 movingDelta = new Vector2();
  public Vector2 acceleration = new Vector2();

  public Vector2 pixelMovingDelta = new Vector2();

  public Vector2 currentFallof = new Vector2();
  public Vector2 targetFallof = new Vector2();
  private boolean exploding;
  public boolean supportedByGround;
  public boolean renderWallLeft;
  public boolean renderWallRight;
  public boolean renderStudLeft;
  public boolean renderStudRight;
  public boolean renderBalconyLeft;
  public boolean renderBalconyRight;
  private float explodeDuration = 0.01F;
  public float explodeAlpha;
  public BlockGroup group;
  public RoomLibrary library;
  public BlocksMap map;
  public Array<Person> people = new Array<Person>();
  int rnd;
  int level;
  int spawnPoints;
  int spawnPoint;

  public Block(int type, int variation, BlocksMap map, BlockGroup group)
  {
    this.type = type;
    this.variation = variation;
    this.active = true;
    this.map = map;
    this.group = group;

    this.library = com.thegreystudios.pixeltower.ai.AILibrary.library[type][variation];

    spawnPeople();
  }

  public Block(int type, int variation) {
    this.type = type;
    this.variation = variation;
    this.active = true;

    this.library = com.thegreystudios.pixeltower.ai.AILibrary.library[type][variation];

    spawnPeople();
  }

  private void spawnPeople() {
    this.rnd = MathUtils.random(10);

    if (this.rnd >= 5)
    {
      if (this.rnd <= 9)
      {
        Person person = new Person(MathUtils.random(5), this, this.map);
        int spawnPoints = this.library.spawnPoints.size;
        AIPoint spawn = (AIPoint)this.library.spawnPoints.get(spawnPoints - 1);

        person.x = spawn.x;
        person.y = spawn.y;

        this.people.add(person);
      }
      else if (this.rnd == 10)
      {
        for (int i = 0; i < 2; i++) {
          Person person = new Person(MathUtils.random(5), this, this.map);
          this.level = MathUtils.random(1);
          this.spawnPoints = this.library.spawnPoints.size;
          this.spawnPoint = MathUtils.random(this.spawnPoints - 1);
          AIPoint spawn = (AIPoint)this.library.spawnPoints.get(this.spawnPoint);

          person.x = spawn.x;
          person.y = spawn.y;

          this.people.add(person);
        }
      }
    }
  }

  public void update(float deltaTime) {
    if (!this.active)
      return;
    if (this.exploding) {
      this.explodingStateTime += deltaTime;

      this.explodeAlpha = (this.explodingStateTime / this.explodeDuration);

      if (this.explodeAlpha >= 1.0F)
        setInactive();
    }
    else
    {
      if (this.velocity.x != 0.0F) {
        this.movingDelta.x += Math.abs(this.velocity.x) * deltaTime;
        this.pixelMovingDelta.x += Math.abs(this.velocity.x) * deltaTime;

        if (this.pixelMovingDelta.x > 7.5F) {
          if (this.velocity.x > 0.0F)
            this.x += 7.5F;
          else {
            this.x -= 7.5F;
          }
          this.pixelMovingDelta.x -= 7.5F;
        }

        if (this.movingDelta.x > 15.0F) {
          if (this.velocity.x < 0.0F)
          {
            this.bx -= 1;
          }
          if (this.velocity.x > 0.0F)
          {
            this.bx += 1;
          }
          this.movingDelta.x -= 15.0F;
        }
      }
      if (this.velocity.y != 0.0F) {
        if (this.targetFallof.x != 0.0F) {
          this.x -= this.currentFallof.x;
          this.currentFallof.lerp(this.targetFallof, 0.9F);
          this.x += this.currentFallof.x;
        }

        this.velocity.y += this.acceleration.y * deltaTime;
        this.y += this.velocity.y * deltaTime;
        this.drop += this.velocity.y * deltaTime;
      }
      if (this.velocity.x < 0.0F) {
        this.bx = (int)Math.floor((this.x + 3.0F) / 15.0F);
        this.by = (int)Math.ceil(this.y / 15.0F);
      }
      else if (this.velocity.x > 0.0F) {
        this.bx = (int)Math.ceil((this.x - 3.0F) / 15.0F);
        this.by = (int)Math.ceil(this.y / 15.0F);
      }
      else {
        this.by = (int)Math.ceil(this.y / 15.0F);
      }
    }
  }

  public void setInactive() {
    this.velocity.set(0.0F, 0.0F);
    this.acceleration.set(0.0F, 0.0F);
    this.active = false;
  }

  public void fixPosition() {
    this.x = (this.bx * 15);
    this.y = (this.by * 15);
  }

  public void explode() {
    this.explodingStateTime = 0.0F;
    this.exploding = true;

    World.showExplosionParticle((int)this.x, (int)this.y);
  }
}