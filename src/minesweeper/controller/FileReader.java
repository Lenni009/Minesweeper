package minesweeper.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

	public static List<String> readFile(String filePath) {
		List<String> rows = new ArrayList<>();
		// copied from https://www.w3schools.com/java/java_files_read.asp
		try {
			File myObj = new File(filePath);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				rows.add(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// end of copy
		return rows;
	}
}
