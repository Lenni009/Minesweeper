package minesweeper.utils;

public abstract class Clamp {
	// copied from https://www.baeldung.com/java-clamp-function
	public static int clamp(int value, int min, int max) {
		return Math.max(min, Math.min(max, value));
	}
}
