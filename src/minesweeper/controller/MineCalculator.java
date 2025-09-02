package minesweeper.controller;

// abstract means that this class cannot be instantiated, but it can still be inherited from
public abstract class MineCalculator {
	public static int getMineCount(int minePercentage, int sideLength) {
		int allFields = sideLength * sideLength;
		int mineCount = (int) Math.round(allFields * divideIntegers(minePercentage, 100));
		return mineCount;
	}

	public static int getMinMinePercentage(int sideLength) {
		int minMineCount = 1;
		int minMinePercentage = (int) Math.ceil(getMinePercentageForSpecificMineCount(minMineCount, sideLength));
		return minMinePercentage;
	}

	public static int getMaxMinePercentage(int sideLength) {
		int allFields = sideLength * sideLength;
		int maxMineCount = allFields - 10;
		int maxMinePercentage = (int) Math.floor(getMinePercentageForSpecificMineCount(maxMineCount, sideLength));
		return maxMinePercentage;
	}

	public static double getMinePercentageForSpecificMineCount(int targetMineCount, int sideLength) {
		int allFields = sideLength * sideLength;
		double targetMinePercentage = divideIntegers(targetMineCount, allFields) * 100;
		return targetMinePercentage;
	}

	// can't be bothered to write that ugly type cast everywhere, so I'm just
	// extracting it out into this method
	private static double divideIntegers(int a, int b) {
		return (double) a / b;
	}
}
