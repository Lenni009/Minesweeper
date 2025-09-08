package minesweeper.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class LeaderBoardData extends DefaultTableModel {

	private List<String[]> tableData = new ArrayList<>();
	private int lastRowIndex;

	public LeaderBoardData(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
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

		this.setRowCount(0);

		for (int i = 0; i < tableData.size(); i++) {

			String[] rowData = tableData.get(i);
			String[] tableRow = { Integer.toString(i + 1), rowData[0], rowData[1] };

			this.addRow(tableRow);
		}
	}

	public int getLastRowIndex() {
		return lastRowIndex;
	}
}
