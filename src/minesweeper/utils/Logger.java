package minesweeper.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// using a custom logger implementation as opposed to java.util.logging.Logger because it makes our life easier
public abstract class Logger {

	private static boolean isLoggingActive = true;

	public static void info(String msg) {
		log(msg, false);
	}

	public static void error(String msg) {
		log(msg, true);
	}

	private static void log(String msg, boolean isError) {
		if (!isLoggingActive)
			return;

		String time = LocalDate.now().toString() + " " + LocalTime.now().toString();

		if (isError) {
			System.err.println(time + " ERROR: " + msg);
		} else {
			System.out.println(time + " INFO: " + msg);
		}
	}

	public static void logArray(List<String[]> arr) {
		for (String[] row : arr) {
			for (String item : row) {
				System.out.println(item);
			}
			System.out.println("------------------");
		}
	}
}
