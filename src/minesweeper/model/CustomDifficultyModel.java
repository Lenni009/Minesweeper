package minesweeper.model;

// data store for the custom difficulty UI
public class CustomDifficultyModel {

	private static CustomDifficultyModel instance;

	private int sideLength = 10; // setting initial value
	private int minePercentage;
	private int mineCount;

	private CustomDifficultyModel() {
	}

	public int getSideLength() {
		return sideLength;
	}

	public void setSideLength(int sideLength) {
		this.sideLength = sideLength;
	}

	public int getMinePercentage() {
		return minePercentage;
	}

	public void setMinePercentage(int minePercentage) {
		this.minePercentage = minePercentage;
	}

	public int getMineCount() {
		return mineCount;
	}

	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

	// singleton :3
	public static CustomDifficultyModel getInstance() {
		if (instance == null) {
			instance = new CustomDifficultyModel();
		}
		return instance;
	}
}
