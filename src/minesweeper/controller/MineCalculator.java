package minesweeper.controller;

// abstract means that this class cannot be instantiated, but it can still be inherited from
public abstract class MineCalculator {
	public static int getMineCount(int sideLength, int bombPercentage) {
		int allFields = sideLength * sideLength;
		int bombCount = (int) Math.round(allFields * divideIntegers(bombPercentage, 100));
		return bombCount;
	}

	public static int getMinMinePercentage(int sideLength) {
		int minBombCount = 1;
		int minBombPercentage = getMinePercentageForSpecificMineCount(sideLength, minBombCount);
		return minBombPercentage;
	}

	public static int getMaxMinePercentage(int sideLength) {
		int allFields = sideLength * sideLength;
		int maxBombCount = allFields - 10;
		int maxBombPercentage = getMinePercentageForSpecificMineCount(sideLength, maxBombCount);
		return maxBombPercentage;
	}

	public static int getMinePercentageForSpecificMineCount(int sideLength, int targetBombCount) {
		int allFields = sideLength * sideLength;
		int targetBombPercentage = (int) Math.round(divideIntegers(targetBombCount, allFields) * 100);
		return targetBombPercentage;
	}

	// can't be bothered to write that ugly type cast everywhere, so I'm just
	// extracting it out into this method
	private static double divideIntegers(int a, int b) {
		return (double) a / b;
	}
}
