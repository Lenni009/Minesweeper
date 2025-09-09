package minesweeper.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import minesweeper.utils.Logger;

public class FileCreator {

	public static void writeFile(String filePath, String data) {
		// copied from
		// https://stackoverflow.com/questions/52581404/java-create-a-new-file-or-override-the-existing-file
		try {
			Writer fileWriter = new FileWriter(filePath, false); // overwrites file
			fileWriter.write(data);
			fileWriter.close(); // it would be better if this was in the `finally` block, but .close() can throw
								// as well, which would need a nested try-catch inside the `finally` block
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}
}
