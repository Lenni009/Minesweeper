package minesweeper.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LeaderBoard extends JPanel {

	public LeaderBoard() {

		String[] dummyData = { "1", "1:00", "idk" }; // TODO: remove this and make dynamic

		// copied from
		// https://stackoverflow.com/questions/20526917/load-arraylist-data-into-jtable
		String columnNames[] = { "Platzierung", "Zeit", "Name" };

		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

		tableModel.addRow(dummyData);
		tableModel.addRow(dummyData);
		// end copy

		// copied from GPT
		JTable table = new JTable(tableModel);
		table.setRowHeight(30); // Set row height
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		// end of copy

		this.add(new JScrollPane(table));
	}
}
