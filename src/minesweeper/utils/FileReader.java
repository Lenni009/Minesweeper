package minesweeper.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class FileReader {

	private FileReader() {
		// this class is not meant to be instantiated
	}

	/**
	 * reads a text file
	 * 
	 * @param filePath path to the file
	 * @return list of lines in the file
	 */
	public static List<String> readFile(String filePath) {
		List<String> rows = new ArrayList<>();
		// copied from https://www.w3schools.com/java/java_files_read.asp
		try {
			File file = new File(filePath);
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				rows.add(data);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Logger.error(e.getMessage());
		}
		// end of copy
		return rows;
	}
}
