package com.thegreystudios.pixeltower.json;

import com.badlogic.gdx.utils.Json;
import com.thegreystudios.pixeltower.blocks.Block;

public class BlockSerializer
  implements Json.Serializer<Block>
{
  public void write(Json json, Block block, Class knownType)
  {
    json.writeObjectStart();
    json.writeValue("x", Float.valueOf(block.x));
    json.writeValue("y", Float.valueOf(block.y));
    json.writeValue("variation", Integer.valueOf(block.variation));
    json.writeValue("type", Integer.valueOf(block.type));

    json.writeValue("renderBalconyLeft", Boolean.valueOf(block.renderBalconyLeft));
    json.writeValue("renderBalconyRight", Boolean.valueOf(block.renderBalconyRight));
    json.writeValue("renderStudLeft", Boolean.valueOf(block.renderStudLeft));
    json.writeValue("renderStudRight", Boolean.valueOf(block.renderStudRight));
    json.writeValue("renderWallLeft", Boolean.valueOf(block.renderWallLeft));
    json.writeValue("renderWallRight", Boolean.valueOf(block.renderWallRight));

    json.writeObjectEnd();
  }

  public Block read(Json json, Object jsonData, Class type)
  {
    return null;
  }
}