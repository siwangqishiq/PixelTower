package com.thegreystudios.pixeltower.ai;

public class AIPoint {

	public static final int MOVEMENT = 0;
	public static final int DOOR = 1;
	public static final int SIT = 2;
	public static final int SLEEP = 3;
	public static final int SPAWN = 4;
	public int x;
	public int y;
	public int type;

	public AIPoint(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

}
