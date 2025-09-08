package minesweeper.view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LeaderBoard extends JPanel {

	public LeaderBoard() {

		String[] dummyData = { "1", "1:00", "idk" }; // TODO: remove this and make dynamic

		// copied from
		// https://stackoverflow.com/questions/20526917/load-arraylist-data-into-jtable
		String columnNames[] = { "Platzierung", "Zeit", "Name" };

		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Only the last cell in the last row is editable
				return row == getRowCount() - 1 && column == getColumnCount() - 1;
			};
		};

		tableModel.addRow(dummyData);
		tableModel.addRow(dummyData);
		// end copy

		// copied from GPT
		JTable table = new JTable(tableModel);
		table.setRowHeight(30); // Set row height
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		// end of copy

		/*
		 * JPanel currentUserData = new JPanel(); currentUserData.setLayout(new
		 * GridLayout(1, 3));
		 * 
		 * JLabel userRank = new JLabel("1"); JLabel userTime = new JLabel("1:00");
		 * JTextField userName = new JTextField(); currentUserData.add(userRank);
		 * currentUserData.add(userTime); currentUserData.add(userName);
		 * this.add(currentUserData);
		 */

		this.add(new JScrollPane(table));
	}
}
