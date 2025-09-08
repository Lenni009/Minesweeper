package minesweeper.view;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import minesweeper.model.LeaderBoardData;

public class LeaderBoard extends JPanel {

	public LeaderBoard() {

		String[] dummyData = { "01:00", "idk" }; // TODO: remove this and make dynamic
		String[] dummyData2 = { "00:59", "idk" }; // TODO: remove this and make dynamic

		String columnNames[] = { "Platzierung", "Zeit", "Name" };

		LeaderBoardData tableModel = new LeaderBoardData(columnNames, 0);

		// copied from StackOverflow as above
		tableModel.addTableRow(dummyData);
		tableModel.addTableRow(dummyData2);
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
