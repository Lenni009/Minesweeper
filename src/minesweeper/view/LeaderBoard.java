package minesweeper.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class LeaderBoard extends JPanel {

	public LeaderBoard() {

		// copied from
		// https://www.paes.eit.htwk-leipzig.de/Java-Insel/javainsel_15_024.htm#Rxx365java150240400054F1F02A10D
		String rowData[][] = { { "Japan", "245", "idk" }, { "USA", "240", "" }, { "Italien", "220", "" },
				{ "Spanien", "217", "" } };

		String columnNames[] = { "Platzierung", "Zeit", "Name" };
		// end of copy

		// copied from GPT
		JTable table = new JTable(rowData, columnNames);
		table.setRowHeight(30); // Set row height
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		// end of copy

		this.add(new JScrollPane(table));
	}
}
