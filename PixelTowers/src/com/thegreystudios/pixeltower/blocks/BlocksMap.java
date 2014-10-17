package com.thegreystudios.pixeltower.blocks;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.thegreystudios.pixeltower.ai.AIPoint;
import com.thegreystudios.pixeltower.ai.Person;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;
import com.thegreystudios.pixeltower.status.GameStatus;
import com.thegreystudios.pixeltower.world.World;
import java.io.IOException;
import java.io.Writer;

public class BlocksMap {

	private int blocksCounter = 0;
	public Array<int[]> blocksMap;
	public int maxHeight;
	public Array<Block> blocks = new Array<Block>();
	public Array<Block> queue = new Array<Block>();
	public Array<Block> remove = new Array<Block>();

	public Array<Person> people = new Array<Person>();
	public static Json jsonLoader;
	int count;
	int rnd;
	Block triggerBonus;

	public BlocksMap(int maxHeight) {
		this.maxHeight = maxHeight;
		this.blocksMap = new Array<int[]>();
		for (int i = 0; i < maxHeight; i++) {
			this.blocksMap.add(new int[] { -1, -1, -1, -1, -1, -1, -1, -1 });
		}

		resetBlocks();
	}

	private void resetBlocks() {
		for (int i = 0; i < this.blocksMap.size; i++) {
			int[] line = (int[]) this.blocksMap.get(i);
			for (int j = 0; j < 8; j++) {
				line[j] = -1;
			}
		}

		this.blocks.clear();
		this.queue.clear();
		this.remove.clear();
		this.blocksCounter = 0;
	}

	public void queueBlockGroup(BlockGroup group) {
		for (int i = 0; i < group.blocks.size; i++) {
			Block block = (Block) group.blocks.get(i);

			if (block.type == 0)
				continue;
			if (block.bx < 0) {
				group.blocks.removeIndex(i);
				group.width -= 1;
				i--;
			} else if (block.bx > 7) {
				group.blocks.removeIndex(i);
				group.width -= 1;
				i--;
			} else {
				queueBlock(block);

				block.targetFallof.x = (block.bx * 15 - block.x);
			}
		}
	}

	public void update(float deltaTime) {
		this.triggerBonus = null;

		for (int i = 0; i < this.remove.size; i++) {
			Block block = (Block) this.remove.get(i);

			if (block.active) {
				block.update(deltaTime);
			} else {
				this.remove.removeIndex(i);
				i--;
			}
		}

		for (int i = 0; i < this.queue.size; i++) {
			Block block = (Block) this.queue.get(i);

			block.update(deltaTime);

			if ((block.by == block.y)
					|| ((block.by != 0) && (getBlock(block.bx, block.by - 1) == null)))
				continue;
			block.fixPosition();
			this.triggerBonus = block;

			if (Math.abs(block.drop) < 25.0F) {
				block.setInactive();
				addBlock(block);
				if (block.by == GameStatus.height - block.group.height) {
					World.showDustParticle((int) block.x, (int) block.y);

					if (GameStatus.height <= 1) {
						GameStatus.firstDropImpact = true;
					}
				}
			} else {
				block.velocity.set(0.0F, 0.0F);
				block.acceleration.set(0.0F, 0.0F);
				this.remove.add(block);
				block.explode();
				SoundLibrary.tileCrashSound.play();
			}
			this.queue.removeIndex(i);
			i--;
		}

		if ((this.triggerBonus != null) && (GameStatus.status != 1)) {
			this.triggerBonus.group.triggerPostBonuses();
		}

		for (int i = 0; i < this.people.size; i++) {
			Person person = (Person) this.people.get(i);

			person.update(deltaTime);
		}
	}

	public void queueBlock(Block block) {
		this.queue.add(block);
	}

	public void addBlock(Block block) {
		this.blocks.add(block);
		if (this.blocksMap.size <= block.by) {
			this.blocksMap.add(new int[] { -1, -1, -1, -1, -1, -1, -1, -1 });
		}
		((int[]) this.blocksMap.get(block.by))[block.bx] = this.blocksCounter;
		this.blocksCounter += 1;

		for (int i = 0; i < block.people.size; i++) {
			Person person = (Person) block.people.get(i);

			assignNewMovementPoint(person);

			this.people.add(person);
		}
	}

	public void assignNewMovementPoint(Person person) {
		Block block = person.owner;
		AIPoint newAIPoint = null;

		this.count = block.library.movePoints.size;
		this.rnd = MathUtils.random(this.count - 1);

		newAIPoint = (AIPoint) block.library.movePoints.get(this.rnd);

		while (person.y != newAIPoint.y) {
			this.rnd = MathUtils.random(this.count - 1);
			newAIPoint = (AIPoint) block.library.movePoints.get(this.rnd);
		}

		person.currentAIPoint = newAIPoint;
		person.nextTaskDuration = MathUtils.random(0.5F, 3.0F);

		person.currentTarget.set(person.currentAIPoint.x,
				person.currentAIPoint.y);
	}

	public Block getBlock(int x, int y) {
		if ((x < 0) || (y < 0) || (x > 7)) {
			return null;
		}
		if (y + 4 >= this.blocksMap.size) {
			while (y + 4 >= this.blocksMap.size)
				this.blocksMap
						.add(new int[] { -1, -1, -1, -1, -1, -1, -1, -1 });
		}
		try {
			if (((int[]) this.blocksMap.get(y))[x] == -1) {
				return null;
			}
			return (Block) this.blocks.get(((int[]) this.blocksMap.get(y))[x]);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return null;
	}

	public void reset() {
		resetBlocks();
		this.people.clear();
	}

	public void loadFromFile(FileHandle handle) {
		int height = 0;

		Array<?> loadedBlocks = (Array<?>) getJsonLoader().fromJson(
				Array.class, handle);

		reset();

		for (int i = 0; i < loadedBlocks.size; i++) {
			Block block = (Block) loadedBlocks.get(i);

			if (block.by > height) {
				height = block.by;
			}
			addBlock(block);
		}

		GameStatus.setHeight(height);
	}

	public void saveToFile(FileHandle handle) {
		String text = getJsonLoader().prettyPrint(this.blocks, 130);
		Writer writer = handle.writer(false);
		try {
			writer.write(text);
			writer.close();
		} catch (IOException ex) {
			throw new GdxRuntimeException(ex);
		}
	}

	private Json getJsonLoader() {
		if (jsonLoader == null) {
			Json json = new Json();

			json.setTypeName(null);
			json.setUsePrototypes(false);

			json.setSerializer(Array.class, new Json.Serializer() {
				public void write(Json json, Object o, Class knownType) {

					Array object = (Array) o;
					json.writeArrayStart();

					for (int i = 0; i < object.size; i++) {
						json.writeValue(object.get(i), knownType);
					}
					json.writeArrayEnd();
				}

				public Array read(Json json, Object jsonData, Class type) {
					Array blocks = new Array();

					Array map = (Array) jsonData;

					for (int i = 0; i < map.size; i++) {
						Block block = (Block) json.readValue(Block.class,
								map.get(i));
						blocks.add(block);
					}
					return blocks;
				}
			});
			json.setSerializer(Block.class, new Json.Serializer() {

				int bx;
				int by;
				int variation;
				int type;
				boolean bl;
				boolean br;
				boolean sl;
				boolean sr;
				boolean wl;
				boolean wr;

				public void write(Json json, Object o, Class knownType) {

					Block block = (Block) o;
					json.writeObjectStart();
					json.writeValue("x", Integer.valueOf(block.bx));
					json.writeValue("y", Integer.valueOf(block.by));
					json.writeValue("variation",
							Integer.valueOf(block.variation));
					json.writeValue("type", Integer.valueOf(block.type));

					json.writeValue("bl",
							Boolean.valueOf(block.renderBalconyLeft));
					json.writeValue("br",
							Boolean.valueOf(block.renderBalconyRight));
					json.writeValue("sl", Boolean.valueOf(block.renderStudLeft));
					json.writeValue("sr",
							Boolean.valueOf(block.renderStudRight));
					json.writeValue("wl", Boolean.valueOf(block.renderWallLeft));
					json.writeValue("wr",
							Boolean.valueOf(block.renderWallRight));

					json.writeObjectEnd();
				}

				public Block read(Json json, Object jsonData, Class pType) {
					this.bx = ((Integer) json.readValue("x", Integer.TYPE,
							jsonData)).intValue();
					this.by = ((Integer) json.readValue("y", Integer.TYPE,
							jsonData)).intValue();

					this.variation = ((Integer) json.readValue("variation",
							Integer.TYPE, jsonData)).intValue();
					this.type = ((Integer) json.readValue("type", Integer.TYPE,
							jsonData)).intValue();

					this.bl = ((Boolean) json.readValue("bl", Boolean.TYPE,
							jsonData)).booleanValue();
					this.br = ((Boolean) json.readValue("br", Boolean.TYPE,
							jsonData)).booleanValue();

					this.sl = ((Boolean) json.readValue("sl", Boolean.TYPE,
							jsonData)).booleanValue();
					this.sr = ((Boolean) json.readValue("sr", Boolean.TYPE,
							jsonData)).booleanValue();

					this.wl = ((Boolean) json.readValue("wl", Boolean.TYPE,
							jsonData)).booleanValue();
					this.wr = ((Boolean) json.readValue("wr", Boolean.TYPE,
							jsonData)).booleanValue();

					Block block = new Block(this.type, this.variation,
							BlocksMap.this, null);
					block.bx = this.bx;
					block.by = this.by;
					block.renderBalconyLeft = this.bl;
					block.renderBalconyRight = this.bl;
					block.renderStudLeft = this.sl;
					block.renderStudRight = this.sr;
					block.renderWallLeft = this.wl;
					block.renderWallRight = this.wr;
					block.fixPosition();
					block.active = false;

					return block;
				}
			});
			jsonLoader = json;

			return jsonLoader;
		}

		return jsonLoader;
	}
}
