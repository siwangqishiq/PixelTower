package com.thegreystudios.pixeltower.blocks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.thegreystudios.pixeltower.ai.AILevel;
import com.thegreystudios.pixeltower.screen.GameScreen;
import com.thegreystudios.pixeltower.sfx.SoundLibrary;
import com.thegreystudios.pixeltower.status.GameStatus;

public class BlockGroup {

	public static final int DEFAULT = 0;
	public static final int BONUS_LENGTH_LEFT = 1;
	public static final int BONUS_LENGTH_RIGHT = 2;
	public static final int BONUS_TIME = 3;
	public static final int BONUS_STACK = 4;
	public static final int BONUS_LENGTH = 5;
	public Array<Block> blocks;
	public boolean preBonusTriggered;
	public boolean postBonusTriggered;
	public BlocksMap map;
	public AILevel topLevelAI;
	public AILevel bottomLevelAI;
	public int type;
	public int width;
	public int height;
	public int baseHeight;
	public float colorVariance;
	int currentHeight;

	public BlockGroup() {
		this(0);
	}

	public BlockGroup(int type) {
		blocks = new Array<Block>();
		topLevelAI = new AILevel();
		bottomLevelAI = new AILevel();
		colorVariance = MathUtils.random(0.9F, 1.0F);
		this.type = type;
	}

	public void update(float deltaTime) {
		for (int i = 0; i < blocks.size; i++)
			((Block) blocks.get(i)).update(deltaTime);

		baseHeight = ((Block) blocks.get(0)).by;
		if (((Block) blocks.get(0)).bx + width < 0
				&& ((Block) blocks.get(0)).velocity.x < 0.0F)
			reverseVelocity();
		else if (((Block) blocks.get(0)).bx > 8
				&& ((Block) blocks.get(0)).velocity.x > 0.0F)
			reverseVelocity();
	}

	public void triggerPreBonuses() {
		if (preBonusTriggered)
			return;
		preBonusTriggered = true;
		if (type == 3)
			GameStatus.bonusSlowdown();
		else if (type == 5)
			GameStatus.bonusLength();
	}

	public void triggerPostBonuses() {
		if (postBonusTriggered)
			return;
		postBonusTriggered = true;
		baseHeight = ((Block) blocks.get(0)).by;
		if (type == 1 && !GameStatus.lineMaxed) {
			GameScreen.displayBonusBlock();
			for (int i = 0; i < blocks.size; i++) {
				Block block = (Block) blocks.get(i);
				if (!block.supportedByGround)
					continue;
				for (int y = 0; y < height; y++) {
					Block newBlock = BlockLineGenerator
							.getAteriumLeftBlock(map);
					newBlock.group = this;
					newBlock.bx = block.bx - 1;
					newBlock.by = block.by + y;
					newBlock.fixPosition();
					newBlock.supportedByGround = true;
					map.addBlock(newBlock);
					blocks.add(newBlock);
				}

				break;
			}

			if (GameStatus.lineLength == 5)
				GameStatus.lineMaxed = true;
		} else if (type == 2 && !GameStatus.lineMaxed) {
			GameScreen.displayBonusBlock();
			for (int i = blocks.size - 1; i >= 0; i--) {
				Block block = (Block) blocks.get(i);
				if (!block.supportedByGround)
					continue;
				for (int y = 0; y < height; y++) {
					Block newBlock = BlockLineGenerator
							.getAteriumRightBlock(map);
					newBlock.group = this;
					newBlock.bx = block.bx + 1;
					newBlock.by = block.by + y;
					newBlock.fixPosition();
					newBlock.supportedByGround = true;
					map.addBlock(newBlock);
					blocks.add(newBlock);
				}

				break;
			}

			if (GameStatus.lineLength == 5)
				GameStatus.lineMaxed = true;
		} else if (type == 3)
			GameScreen.displaySlowDown();
		else if (type == 4)
			GameScreen.displayExtraStack();
		if (type != 0)
			SoundLibrary.boniSound.play();
		else
			SoundLibrary.tileHitSound.play();
		checkWalls();
		if (GameStatus.height > 1 && GameStatus.status != 2)
			checkRowBelow();
	}

	public void checkWalls() {
		Block mostLeft[] = new Block[height];
		Block mostRight[] = new Block[height];
		for (int i = 0; i < blocks.size; i++) {
			Block block = (Block) blocks.get(i);
			if (block.supportedByGround && block.bx >= 0 && block.bx < 8) {
				currentHeight = block.by - baseHeight;
				if (currentHeight >= 0 && currentHeight <= mostLeft.length - 1
						&& currentHeight <= mostRight.length - 1) {
					if (mostLeft[currentHeight] == null
							|| block.bx < mostLeft[currentHeight].bx)
						mostLeft[currentHeight] = block;
					if (mostRight[currentHeight] == null
							|| block.bx > mostRight[currentHeight].bx)
						mostRight[currentHeight] = block;
					block.renderWallLeft = block.renderWallRight = false;
				}
			}
		}

		for (int y = 0; y < height; y++) {
			if (mostLeft[y] != null)
				mostLeft[y].renderWallLeft = true;
			if (mostRight[y] != null)
				mostRight[y].renderWallRight = true;
		}

	}

	private void checkRowBelow() {
		for (int i = 0; i < 8; i++) {
			Block blockBelow = map.getBlock(i, baseHeight - 1);
			Block blockCurrent = map.getBlock(i, baseHeight);
			if (blockBelow == null && blockCurrent != null)
				if (map.getBlock(i + 1, baseHeight - 1) != null)
					blockCurrent.renderStudLeft = true;
				else
					blockCurrent.renderStudRight = true;
			if (blockBelow != null && blockCurrent == null)
				if (map.getBlock(i + 1, baseHeight) != null)
					blockBelow.renderBalconyLeft = true;
				else
					blockBelow.renderBalconyRight = true;
		}

	}

	public void add(Block block) {
		block.group = this;
		blocks.add(block);
	}

	public void setVelocity(float x, float y) {
		for (int i = 0; i < blocks.size; i++)
			((Block) blocks.get(i)).velocity.set(x, y);

	}

	public void setInactive() {
		for (int i = 0; i < blocks.size; i++)
			((Block) blocks.get(i)).active = false;

	}

	public void moveBy(int x, int y) {
		for (int i = 0; i < blocks.size; i++) {
			Block block = (Block) blocks.get(i);
			block.x += x;
			block.y += y;
			block.bx = (int) (block.x / 15F);
			block.by = (int) (block.y / 15F);
		}

	}

	public void reverseVelocity() {
		for (int i = 0; i < blocks.size; i++) {
			Block block = (Block) blocks.get(i);
			block.velocity.x *= -1F;
			block.velocity.y *= -1F;
		}

	}

	public void setAcceleration(int x, int y) {
		for (int i = 0; i < blocks.size; i++)
			((Block) blocks.get(i)).acceleration.set(x, y);

	}

}
