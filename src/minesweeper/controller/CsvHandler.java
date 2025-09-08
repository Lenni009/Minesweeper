package minesweeper.controller;

import java.util.ArrayList;
import java.util.List;

public class CsvHandler {

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
			arrString += ("\"" + escapeQuotationMarks(arrItem.trim()) + "\"");
			if (i < arr.length - 1) {
				arrString += ",";
			}
		}
		return arrString;
	}

	private static String escapeQuotationMarks(String input) {
		// this uses the streaming API, prepare to be asked to rewrite this into a for
		// loop
		return input.replaceAll("\"", "\"\"");
	}

	public static List<String[]> parseCsv(List<String> rows) {
		List<String[]> csvRows = new ArrayList<>();

		// starting at 2 because we ignore the first two rows (separator and header)
		for (int i = 2; i < rows.size(); i++) {
			String row = rows.get(i);
			String[] rowArr = row.split(",", 3);
			String[] unescapedRow = unescapeCsv(rowArr);
			csvRows.add(unescapedRow);
		}

		return csvRows;
	}

	private static String[] unescapeCsv(String[] row) {
		String[] resultList = new String[3];
		for (int i = 0; i < row.length; i++) {
			String item = row[i];
			String result = "";
			char[] charArr = item.toCharArray();

			// omitting first and last character (")
			for (int j = 1; j < charArr.length - 1; j++) {
				result += charArr[j];
			}

			resultList[i] = result.replaceAll("\"\"", "\"");
		}
		return resultList;
	}
}
