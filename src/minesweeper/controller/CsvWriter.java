package minesweeper.controller;

import java.util.List;

public class CsvWriter {

	public static String createCsv(String[] header, List<String[]> data) {
		String sep = "sep=,";
		String headerString = concatArray(header);

		String csv = sep + "\n" + headerString + "\n";

		for (int i = 0; i < data.size(); i++) {
			String[] row = data.get(i);
			String rowString = concatArray(row);
			csv += (rowString + "\n");
		}
		
		return csv.trim();
	}

	private static String concatArray(String[] arr) {
		String arrString = "";
		for (int i = 0; i < arr.length; i++) {
			String arrItem = arr[i];
			// escape single quotation marks as per csv spec
			// this uses the streaming API, prepare to be asked to rewrite this into a for
			// loop
			arrString += ("\"" + escapeQuotationMarks(arrItem.trim()) + "\"");
			if (i < arr.length - 1) {
				arrString += ",";
			}
		}
		return arrString;
	}

	private static String escapeQuotationMarks(String input) {
		return input.replaceAll("\"", "\"\"");
	}
}
