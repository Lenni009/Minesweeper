package minesweeper.utils;

import java.util.ArrayList;
import java.util.List;

public class CsvHandler {

	private static final int AMOUNT_OF_FIELDS_TO_PARSE = 2;

	public static String createCsv(String[] header, List<String[]> data) {
		// SonarQube screamed at us that we should use a StringBuilder instead of
		// manually concatenating strings
		StringBuilder csv = new StringBuilder();

		String sep = "sep=,";
		String headerString = concatArray(header);
		String newLine = "\n";
		csv.append(sep).append(newLine).append(headerString).append(newLine);

		for (int i = 0; i < data.size(); i++) {
			String[] row = data.get(i);
			String rowString = concatArray(row);
			csv.append(rowString).append(newLine);
		}

		return csv.toString().trim();
	}

	private static String concatArray(String[] arr) {
		StringBuilder arrString = new StringBuilder();

		for (int i = 0; i < arr.length; i++) {
			String arrItem = arr[i];
			// escape single quotation marks as per csv spec
			// SonarQube didn't like using "+" and wanted to use .append instead
			// blame SonarQube!
			arrString.append("\"").append(escapeQuotationMarks(arrItem.trim())).append("\"");
			if (i < arr.length - 1) {
				arrString.append(",");
			}
		}
		return arrString.toString();
	}

	private static String escapeQuotationMarks(String input) {
		// this uses the streaming API, prepare to be asked to rewrite this into a for
		// loop
		return input.replace("\"", "\"\"");
	}

	public static List<String[]> parseCsv(List<String> rows) {
		List<String[]> csvRows = new ArrayList<>();

		// starting at 2 because we ignore the first two rows (separator and header)
		for (int i = 2; i < rows.size(); i++) {
			String row = rows.get(i);
			// the 2 means that we only create an array with 2 items
			String[] rowArr = row.split(",", AMOUNT_OF_FIELDS_TO_PARSE);
			String[] unescapedRow = unescapeCsv(rowArr);
			csvRows.add(unescapedRow);
		}

		return csvRows;
	}

	private static String[] unescapeCsv(String[] row) {
		String[] resultList = new String[AMOUNT_OF_FIELDS_TO_PARSE];
		for (int i = 0; i < row.length; i++) {
			String item = row[i];
			StringBuilder result = new StringBuilder();
			char[] charArr = item.toCharArray();

			// omitting first and last character (")
			for (int j = 1; j < charArr.length - 1; j++) {
				result.append(charArr[j]);
			}

			resultList[i] = result.toString().replace("\"\"", "\"");
		}
		return resultList;
	}
}
