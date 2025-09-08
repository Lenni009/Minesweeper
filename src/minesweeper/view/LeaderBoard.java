package minesweeper.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class LeaderBoard extends JPanel {

	public LeaderBoard() {

		String[] dummyData = { "1", "1:00", "idk" }; // TODO: remove this and make dynamic

		String columnNames[] = { "Platzierung", "Zeit", "Name" };

		// GPT generated
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Only the last cell in the last row is editable
				return row == getRowCount() - 1 && column == getColumnCount() - 1;
			};
		};
		// end of GPT

		// copied from StackOverflow as above
		tableModel.addRow(dummyData);
		tableModel.addRow(dummyData);
		// end of copy

		// copied from GPT
		JTable table = new JTable(tableModel);

		table.setRowHeight(30); // Set row height
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		// end of copy

		this.add(new JScrollPane(table)); // we need a scrollplane, otherwise the header isn't shown

		// copied from GPT
		SwingUtilities.invokeLater(() -> {
			int lastRow = table.getRowCount() - 1;
			int lastCol = table.getColumnCount() - 1;
			table.editCellAt(lastRow, lastCol);
			Component editor = table.getEditorComponent();
			if (editor != null) {
				editor.requestFocusInWindow();
			}
		});
		// end of copy
	}
}
