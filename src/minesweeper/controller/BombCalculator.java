package minesweeper.controller;

import minesweeper.utils.Logger;

// abstract means that this class cannot be instantiated, but it can still be inherited from
public abstract class BombCalculator {
	public static int getBombCount(int sideLength, int bombPercentage) {
		int allFields = sideLength * sideLength;
		int bombCount = Math.round(allFields * (bombPercentage / 100));
		return bombCount;
	}

	public static int getMinBombPercentage(int sideLength) {
		int allFields = sideLength * sideLength;
		int minBombCount = 1;

		int minBombPercentage = (int) Math.ceil((minBombCount / allFields) * 100);
		Logger.info(minBombCount + " " + allFields + " " + (minBombCount / allFields));
		return minBombPercentage;
	}

	public static int getMaxBombPercentage(int sideLength) {
		int allFields = sideLength * sideLength;
		int maxBombCount = allFields - 10;

		int maxBombPercentage = (int) Math.floor((maxBombCount / allFields) * 100);
		Logger.info(maxBombCount + " " + allFields + " " + (maxBombCount / allFields));
		return maxBombPercentage;
	}
}
