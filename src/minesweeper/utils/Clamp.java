package minesweeper.utils;

public final class Clamp {
	
	private Clamp() {
		// this class is not meant to be instantiated
	}

	// copied from https://www.baeldung.com/java-clamp-function
	public static int clamp(int value, int min, int max) {
		return Math.max(min, Math.min(max, value));
	}
}
