package minesweeper.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class Logger {

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

}
