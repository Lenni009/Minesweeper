package minesweeper.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import minesweeper.utils.CsvHandler;
import minesweeper.utils.FileCreator;
import minesweeper.utils.FileReader;

// model for the leaderboard table
public class LeaderBoardData extends DefaultTableModel {

	private String[] columnNames;
	private List<String[]> tableData = new ArrayList<>();
	private int lastRowIndex;

	public LeaderBoardData(String[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		this.columnNames = columnNames;
	}

	// GPT generated
	@Override
	public boolean isCellEditable(int row, int column) {
		return row == lastRowIndex && column == getColumnCount() - 1;
	}
	// end of GPT

	/**
	 * adds a row to the dataset
	 * 
	 * @param row row to add to the table
	 */
	public void addTableRow(String[] row) {
		tableData.add(row);
		// sort by time taken
		tableData.sort(Comparator.comparing(arr -> arr[0])); // comparison function by GPT

		// store index of where the last row was inserted (aka last rank)
		lastRowIndex = tableData.indexOf(row);
		transferDataToModel();
	}

	/**
	 * remove a row from the table
	 * 
	 * @param index index of row to remove
	 */
	public void removeTableRow(int index) {
		tableData.remove(index);
		transferDataToModel();
	}

	public int getLastRowIndex() {
		return lastRowIndex;
	}

	/**
	 * applies custom data structure to actual table model that can be used by the
	 * table to display the data
	 */
	private void transferDataToModel() {
		// remove all rows from the model so we have no old data
		this.setRowCount(0);

		// add ranks for display purposes
		List<String[]> rankTableData = addRanksToData(tableData);

		// add all rows back from our own data
		for (String[] tableRow : rankTableData) {
			this.addRow(tableRow);
		}
	}

	/**
	 * add rank numbers to table data
	 * 
	 * @param data data to add ranks to
	 * @return list of string arrays with rank numbers added in index 0
	 */
	private List<String[]> addRanksToData(List<String[]> data) {
		List<String[]> rankData = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			String[] rowData = data.get(i);
			String[] tableRow = { Integer.toString(i + 1), rowData[0], rowData[1] };
			rankData.add(tableRow);
		}
		return rankData;
	}

	/**
	 * export table data into a csv file
	 * 
	 * @param sideLength length of one side of the playing field
	 * @param mineCount  total amount of mines
	 */
	public void exportData(int sideLength, int mineCount) {
		String filePath = getFilePath(sideLength, mineCount);
		String[] columnNamesWithoutRank = { columnNames[1], columnNames[2] };
		String csvData = CsvHandler.createCsv(columnNamesWithoutRank, tableData);

		FileCreator.writeFile(filePath, csvData);
	}

	/**
	 * load table data from a csv file
	 * 
	 * @param sideLength length of one side of the playing field
	 * @param mineCount  total amount of mines
	 */
	public void importData(int sideLength, int mineCount) {
		String filePath = getFilePath(sideLength, mineCount);
		List<String> rows = FileReader.readFile(filePath);

		// if file reading fails (i. e. file does not exist), the list will be null
		if (rows == null) {
			return;
		}

		tableData = CsvHandler.parseCsv(rows);
		transferDataToModel();
	}

	private String getFilePath(int sideLength, int mineCount) {
		return sideLength + "x" + sideLength + "_" + mineCount + ".csv";
	}
}
