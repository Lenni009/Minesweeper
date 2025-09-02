package minesweeper.view;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class EndScreen extends JDialog {

	public EndScreen() {
		// set up dialog frame
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setTitle("Gewonnen|Verloren (Spiel zuende?)"); // TODO: proper title
		this.setSize(100, 100);

		JLabel dialogLabel = new JLabel("this is a dialog box");

		this.add(dialogLabel);

		this.setVisible(true);
	}
}
