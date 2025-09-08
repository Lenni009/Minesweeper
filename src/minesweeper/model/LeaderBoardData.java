package minesweeper.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.table.DefaultTableModel;

public class LeaderBoardData extends DefaultTableModel {

	private List<String[]> tableData = new ArrayList<>();

	public LeaderBoardData(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	// GPT generated
	@Override
	public boolean isCellEditable(int row, int column) {
		// Only the last cell in the last row is editable
		return row == getRowCount() - 1 && column == getColumnCount() - 1;
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

		this.setRowCount(0);

		for (int i = 0; i < tableData.size(); i++) {

			String[] rowData = tableData.get(i);
			String[] tableRow = { Integer.toString(i + 1), row[0], rowData[1] };

			this.addRow(tableRow);
		}
	}
}
