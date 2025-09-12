package minesweeper.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public final class FileCreator {

	private FileCreator() {
		// this class is not meant to be instantiated
	}

	/**
	 * writes a text file, overwrites existing files
	 * 
	 * @param filePath path to the file that shall be written
	 * @param data     data to be written into the file
	 */
	public static void writeFile(String filePath, String data) {
		// copied from
		// https://stackoverflow.com/questions/52581404/java-create-a-new-file-or-override-the-existing-file
		// also thanks to SonarQube for suggesting try-with-resources
		try (Writer fileWriter = new FileWriter(filePath, false)) {
			fileWriter.write(data);
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}
}
