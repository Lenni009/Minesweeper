package minesweeper.utils;

import java.util.ArrayList;
import java.util.List;

// handles csv operations
public abstract class CsvHandler {

	private static final int AMOUNT_OF_FIELDS_TO_PARSE = 2;

	/**
	 * creates a csv string that can be written to a file
	 * 
	 * @param header array of strings containing the table header labels
	 * @param data   tabular data representing the table body
	 * @return string in csv format representing the data
	 */
	public static String createCsv(String[] header, List<String[]> data) {
		// SonarQube screamed at us that we should use a StringBuilder instead of
		// manually concatenating strings
		StringBuilder csv = new StringBuilder();

		String sep = "sep=,"; // some spreadsheet programs use localised default separators, we're
								// overriding this behaviour
		String headerString = concatArray(header);
		String newLine = "\n";
		csv.append(sep).append(newLine).append(headerString).append(newLine);

		for (int i = 0; i < data.size(); i++) {
			String[] row = data.get(i);
			String rowString = concatArray(row);
			csv.append(rowString).append(newLine);
		}

		return csv.toString().trim(); // trim so any trailing newlines get removed
	}

	/**
	 * concatenates an array into a string in escaped csv format
	 * 
	 * @param arr array to concatenate
	 * @return concatenated and escaped stringified array
	 */
	private static String concatArray(String[] arr) {
		StringBuilder arrString = new StringBuilder();

		for (int i = 0; i < arr.length; i++) {
			String arrItem = arr[i];
			// escape single quotation marks as per csv spec
			// SonarQube didn't like using "+" and wanted to use .append instead
			// blame SonarQube!
			arrString.append("\"").append(escapeQuotationMarks(arrItem.trim())).append("\"");
			if (i < arr.length - 1) {
				// if not the last item in the array, add a comma
				arrString.append(",");
			}
		}
		return arrString.toString();
	}

	/**
	 * escapes quotation marks in strings according to the csv spec
	 * 
	 * @param input string to escape
	 * @return escaped string
	 */
	private static String escapeQuotationMarks(String input) {
		return input.replace("\"", "\"\"");
	}

	/**
	 * parse raw csv data back into a list of string arrays
	 * 
	 * @param rows list of csv rows
	 * @return list of string arrays representing the csv without the header
	 */
	public static List<String[]> parseCsv(List<String> rows) {
		List<String[]> csvRows = new ArrayList<>();

		// starting at 2 because we ignore the first two rows (separator and header)
		for (int i = 2; i < rows.size(); i++) {
			String row = rows.get(i);
			// the second argument means that we create an array with only
			// AMOUNT_OF_FIELDS_TO_PARSE items
			String[] rowArr = row.split(",", AMOUNT_OF_FIELDS_TO_PARSE);
			String[] unescapedRow = unescapeCsv(rowArr);
			csvRows.add(unescapedRow);
		}

		return csvRows;
	}

	/**
	 * reverse the quotation mark escaping and remove csv field quotation marks
	 * 
	 * @param row array of strings to unescape
	 * @return array of unescaped strings
	 */
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
