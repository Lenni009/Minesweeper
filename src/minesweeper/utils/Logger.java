package minesweeper.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// using a custom logger implementation as opposed to java.util.logging.Logger because it makes our life easier
public abstract class Logger {

	private static final boolean isLoggingActive = false;

	public static void info(String msg) {
		log(msg, false);
	}

	public static void error(String msg) {
		log(msg, true);
	}

	/**
	 * logs a formatted debug string to the console
	 * 
	 * @param msg     string to be logged
	 * @param isError whether the message is an error
	 */
	private static void log(String msg, boolean isError) {
		if (!isLoggingActive) {
			return;
		}

		// adding timestamp to log
		String time = LocalDate.now().toString() + " " + LocalTime.now().toString();

		if (isError) {
			System.err.println(time + " ERROR: " + msg);
		} else {
			System.out.println(time + " INFO: " + msg);
		}
	}

	/**
	 * log all items in a list of string arrays
	 * 
	 * @param arr list of string arrays to log
	 */
	public static void logArray(List<String[]> arr) {
		if (!isLoggingActive) {
			return;
		}

		for (String[] row : arr) {
			for (String item : row) {
				System.out.println(item);
			}
			System.out.println("------------------");
		}
	}
}
