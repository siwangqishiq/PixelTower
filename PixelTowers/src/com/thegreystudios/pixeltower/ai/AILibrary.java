package com.thegreystudios.pixeltower.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

public class AILibrary {
	public static int SPAWN_COLOR = 16777215;
	public static int MOVEMENT_COLOR = 16711680;

	public static RoomLibrary[][] library = new RoomLibrary[14][];

	public static void loadAILibrary() {
		library[5] = new RoomLibrary[3];
		Pixmap pixmap = new Pixmap(Gdx.files.internal("ai/labMiddleRoom01.png"));
		library[5][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/labMiddleRoom02.png"));
		library[5][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/labMiddleRoom03.png"));
		library[5][2] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[4] = new RoomLibrary[2];
		pixmap = new Pixmap(Gdx.files.internal("ai/labLeftRoom01.png"));
		library[4][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/labLeftRoom02.png"));
		library[4][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[6] = new RoomLibrary[2];
		pixmap = new Pixmap(Gdx.files.internal("ai/labRightRoom01.png"));
		library[6][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/labRightRoom02.png"));
		library[6][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[7] = new RoomLibrary[2];
		pixmap = new Pixmap(Gdx.files.internal("ai/ateriumLeftRoom01.png"));
		library[7][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/ateriumLeftRoom02.png"));
		library[7][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[8] = new RoomLibrary[3];
		pixmap = new Pixmap(Gdx.files.internal("ai/ateriumMiddleRoom01.png"));
		library[8][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/ateriumMiddleRoom02.png"));
		library[8][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/ateriumMiddleRoom03.png"));
		library[8][2] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[9] = new RoomLibrary[2];
		pixmap = new Pixmap(Gdx.files.internal("ai/ateriumRightRoom01.png"));
		library[9][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/ateriumRightRoom02.png"));
		library[9][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[10] = new RoomLibrary[2];
		pixmap = new Pixmap(Gdx.files.internal("ai/biblioLeftRoom01.png"));
		library[10][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/biblioLeftRoom02.png"));
		library[10][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[11] = new RoomLibrary[3];
		pixmap = new Pixmap(Gdx.files.internal("ai/biblioMiddleRoom01.png"));
		library[11][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/biblioMiddleRoom02.png"));
		library[11][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/biblioMiddleRoom03.png"));
		library[11][2] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[12] = new RoomLibrary[2];
		pixmap = new Pixmap(Gdx.files.internal("ai/biblioRightRoom01.png"));
		library[12][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/biblioRightRoom02.png"));
		library[12][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[1] = new RoomLibrary[4];
		pixmap = new Pixmap(Gdx.files.internal("ai/normalLeftRoom01.png"));
		library[1][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalLeftRoom02.png"));
		library[1][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalLeftRoom03.png"));
		library[1][2] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalLeftRoom04.png"));
		library[1][3] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[2] = new RoomLibrary[7];
		pixmap = new Pixmap(Gdx.files.internal("ai/normalMiddleRoom01.png"));
		library[2][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalMiddleRoom02.png"));
		library[2][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalMiddleRoom03.png"));
		library[2][2] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalStairsRoom01.png"));
		library[2][3] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalStairsRoom02.png"));
		library[2][4] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalMiddleRoom04.png"));
		library[2][5] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalMiddleRoom05.png"));
		library[2][6] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[3] = new RoomLibrary[4];
		pixmap = new Pixmap(Gdx.files.internal("ai/normalRightRoom01.png"));
		library[3][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalRightRoom02.png"));
		library[3][1] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalRightRoom03.png"));
		library[3][2] = parsePixmapForAI(pixmap);
		pixmap.dispose();
		pixmap = new Pixmap(Gdx.files.internal("ai/normalRightRoom04.png"));
		library[3][3] = parsePixmapForAI(pixmap);
		pixmap.dispose();

		library[13] = new RoomLibrary[1];
		pixmap = new Pixmap(Gdx.files.internal("ai/normalStairsCase01.png"));
		library[13][0] = parsePixmapForAI(pixmap);
		pixmap.dispose();
	}

	public static RoomLibrary parsePixmapForAI(Pixmap pixmap) {
		RoomLibrary library = new RoomLibrary();

		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = 0; y < pixmap.getHeight(); y++) {
				int pixel = pixmap.getPixel(x, y) >>> 8;

				if (pixel == SPAWN_COLOR)
					library.spawnPoints.add(new AIPoint(x, 14 - y, 4));
				else if (pixel == MOVEMENT_COLOR) {
					library.movePoints.add(new AIPoint(x, 14 - y, 0));
				}
			}
		}
		return library;
	}
}