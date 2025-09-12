package minesweeper.controller;

public final class MineCalculator {

	private MineCalculator() {
		// this class is not meant to be instantiated
	}

	/**
	 * calculate total absolute mine count for specific playing field settings
	 * 
	 * @param minePercentage percentage of mines
	 * @param sideLength     length of the side of the playing field
	 * @return total absolute mine count
	 */
	public static int getMineCount(int minePercentage, int sideLength) {
		int allFields = sideLength * sideLength;
		return (int) Math.round(allFields * divideIntegers(minePercentage, 100));
	}

	/**
	 * calculate the minimum mine percentage for a specific field size
	 * 
	 * @param sideLength length of the side of the playing field
	 * @return minimum mine percentage
	 */
	public static int getMinMinePercentage(int sideLength) {
		int minMineCount = 1;
		return (int) Math.ceil(getMinePercentageForSpecificMineCount(minMineCount, sideLength));
	}

	/**
	 * calculate the maximum mine percentage for a specific field size
	 * 
	 * @param sideLength length of the side of the playing field
	 * @return maximum mine percentage
	 */
	public static int getMaxMinePercentage(int sideLength) {
		int allFields = sideLength * sideLength;
		int maxMineCount = allFields - 10;
		return (int) Math.floor(getMinePercentageForSpecificMineCount(maxMineCount, sideLength));
	}

	/**
	 * calculates the percentage of mines needed to get a specified amount of mines
	 * with the specified field side length
	 * 
	 * @param targetMineCount total amount of mines wanted in the field
	 * @param sideLength
	 * @return percentage of mines needed to get the specified amount of mines
	 */
	public static double getMinePercentageForSpecificMineCount(int targetMineCount, int sideLength) {
		int allFields = sideLength * sideLength;
		return divideIntegers(targetMineCount, Math.max(1, allFields)) * 100; // also making sure we don't divide by 0
	}

	// I can't be bothered to write that ugly (double) type cast everywhere, so I'm
	// just extracting it out into this method
	private static double divideIntegers(int a, int b) {
		return (double) a / b;
	}
}
