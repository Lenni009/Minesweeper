package minesweeper.view;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import minesweeper.Minesweeper;

public class EndScreen extends JDialog {

	public EndScreen() {
		// set up dialog frame
		this.setModalityType(ModalityType.APPLICATION_MODAL); // make game window un-interactable
		this.setTitle("Gewonnen|Verloren (Spiel zuende?)"); // TODO: proper title
		this.setSize(100, 100);

		JPanel wrapper = new JPanel();
		BoxLayout layout = new BoxLayout(wrapper, BoxLayout.Y_AXIS);
		wrapper.setLayout(layout);

		JButton mainMenu = new JButton("Hauptmenü");
		JButton restart = new JButton("Nochmal");

		mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		restart.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainMenu.addActionListener(e -> {
			Minesweeper.getInstance().backToMainMenu();
			this.setVisible(false);
		});
		restart.addActionListener(e -> {
			Minesweeper.getInstance().restartGame();
			this.setVisible(false);
		});

		// TODO: Bestenliste

		wrapper.add(restart);
		wrapper.add(mainMenu);

		this.add(wrapper);

		this.setVisible(true);
	}
}
