package minesweeper.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import minesweeper.controller.CsvHandler;
import minesweeper.controller.FileCreator;
import minesweeper.controller.FileReader;

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
	};
	// end of GPT

	/**
	 * adds a row to the dataset
	 * 
	 * @param row row to add to the table
	 */
	public void addTableRow(String[] row) {
		tableData.add(row);
		tableData.sort(Comparator.comparing(arr -> arr[0])); // comparison function by GPT

		lastRowIndex = tableData.indexOf(row);
		transferDataToModel();
	}

	public void removeTableRow(int index) {
		tableData.remove(index);
		transferDataToModel();
	}

	public int getLastRowIndex() {
		return lastRowIndex;
	}

	private void transferDataToModel() {
		// remove all rows from the model so we have no old data
		this.setRowCount(0);

		List<String[]> rankTableData = addRanksToData(tableData);

		// add all rows back from our own data
		for (String[] tableRow : rankTableData) {
			this.addRow(tableRow);
		}

	}

	private List<String[]> addRanksToData(List<String[]> data) {
		List<String[]> rankData = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			String[] rowData = data.get(i);
			String[] tableRow = { Integer.toString(i + 1), rowData[0], rowData[1] };
			rankData.add(tableRow);
		}
		return rankData;
	}

	public void exportData(int sideLength, int mineCount) {
		String filePath = sideLength + "x" + sideLength + "_" + mineCount + ".csv";
		List<String[]> rankTableData = addRanksToData(tableData);
		String csvData = CsvHandler.createCsv(columnNames, rankTableData);

		FileCreator.writeFile(filePath, csvData);
	}

	public void importData(int sideLength, int mineCount) {
		String filePath = sideLength + "x" + sideLength + "_" + mineCount + ".csv";
		List<String> rows = FileReader.readFile(filePath);
		tableData = CsvHandler.parseCsv(rows);
		transferDataToModel();
	}
}
