package com.thegreystudios.pixeltower.status;

public class GameStatus {

	public static final int PLAYING = 0;
	public static final int GAME_OVER = 1;
	public static final int GAME_OVER_SCREEN = 2;
	public static float menuCameraVelocity = -10F;
	public static int menuScrollSpeed = 5;
	public static int status;
	public static int height = 0;
	public static float cameraHeight = 0.0F;
	public static int lineLength = 4;
	public static float currentSpeed = 100F;
	public static boolean lineMaxed;
	public static boolean waitingForNewLine;
	public static boolean firstDropImpact;
	public static boolean firstDropProcessed;
	public static boolean newHighscoreReached;
	public static boolean newHighscoreTriggered;
	public static boolean highscoreSubmitted;
	public static boolean askingForGlobalHighscores;
	public static boolean globalHighscoresReceived;
	public static boolean askingForTodaysHighscores;
	public static boolean todaysHighscoresReceived;
	public static boolean rankUpdated;
	public static boolean ufoSighted;

	public GameStatus() {
	}

	public static void newGame() {
		status = 0;
		setHeight(0);
		lineLength = 4;
		currentSpeed = 50F;
		firstDropImpact = false;
		firstDropProcessed = false;
		newHighscoreReached = false;
		highscoreSubmitted = false;
		ufoSighted = false;
	}

	public static void gameOver() {
		status = 1;
	}

	public static void gameOverScreen() {
		status = 2;
	}

	public static void bonusLength() {
		if (lineLength < 5)
			lineLength++;
	}

	public static void setHeight(int h) {
		height = h;
		cameraHeight = h * 15;
	}

	public static void bonusSlowdown() {
		currentSpeed = Math.max(currentSpeed - 50F, 50F);
	}

}
