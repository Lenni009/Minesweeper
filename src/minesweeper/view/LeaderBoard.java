package minesweeper.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import minesweeper.Minesweeper;
import minesweeper.controller.GameTimer;
import minesweeper.model.LeaderBoardData;
import minesweeper.model.MsData;

public class LeaderBoard extends JPanel {

	public LeaderBoard() {

		MsData model = Minesweeper.getInstance().getModel();
		int fieldLen = model.getFieldLen();
		int amountOfMines = model.getAmountOfMines();
		String elapsedTime = GameTimer.getInstance().getTime();

		String[] userData = { elapsedTime, "" };

		String columnNames[] = { "Platzierung", "Zeit", "Name" };

		LeaderBoardData tableModel = new LeaderBoardData(columnNames, 0);
		tableModel.importData(fieldLen, amountOfMines);

		tableModel.addTableRow(userData);

		// copied from GPT
		JTable table = new JTable(tableModel);

		int rowHeight = 30;
		table.setRowHeight(rowHeight); // Set row height

		// fit 5 items at a time
		int tableHeight = 5 * rowHeight;

		Dimension preferredTableSize = new Dimension(table.getPreferredSize().width, tableHeight);
		table.setPreferredScrollableViewportSize(preferredTableSize);
		// end of copy

		this.add(new JScrollPane(table)); // we need a scrollplane, otherwise the header isn't shown

		int editableRow = tableModel.getLastRowIndex();
		int lastCol = table.getColumnCount() - 1;

		// copied from GPT
		SwingUtilities.invokeLater(() -> {
			table.editCellAt(editableRow, lastCol);
			Component editor = table.getEditorComponent();
			if (editor != null) {
				editor.requestFocusInWindow();
				table.scrollRectToVisible(table.getCellRect(editableRow, lastCol, true)); // copied from Bing AI GPT (a few hours later)
			}
		});
		// end of copy

		// listen for events on the parent
		this.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorRemoved(AncestorEvent event) {
				// forcefully end the editing by providing invalid indices
				table.editCellAt(-1, -1);
				String userName = tableModel.getValueAt(editableRow, lastCol).toString();
				tableModel.removeTableRow(editableRow);
				String[] newTableRow = { elapsedTime, userName };
				tableModel.addTableRow(newTableRow);

				tableModel.exportData(fieldLen, amountOfMines);
			}

			// these must be here, otherwise Java complains about unimplemented methods
			@Override
			public void ancestorAdded(AncestorEvent event) {
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}
		});
	}
}
