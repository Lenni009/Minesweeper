package minesweeper.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public abstract class FileCreator {

	/**
	 * writes a text file, overwrites existing files
	 * @param filePath path to the file that shall be written
	 * @param data data to be written into the file
	 */
	public static void writeFile(String filePath, String data) {
		try {
			// copied from
			// https://stackoverflow.com/questions/52581404/java-create-a-new-file-or-override-the-existing-file
			Writer fileWriter = new FileWriter(filePath, false); // overwrites file
			fileWriter.write(data);
			fileWriter.close(); // it would be better if this was in the `finally` block, but .close() can throw
								// as well, which would need a nested try-catch inside the `finally` block
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}
}
