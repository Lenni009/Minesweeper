package minesweeper.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import minesweeper.Minesweeper;

public class PauseScreen extends JPanel {

	private static PauseScreen instance;

	private PauseScreen() {
		this.setLayout(new BorderLayout());

		JButton resumeButton = new JButton("Fortsetzen");
		resumeButton.addActionListener(e -> {
			Minesweeper.getInstance().resumeGame();
		});
	}

	// hello singleton
	public static PauseScreen getInstance() {
		if (instance == null) {
			instance = new PauseScreen();
		}
		return instance;
	}
}
