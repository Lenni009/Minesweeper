package minesweeper.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class CustomDifficulty extends JPanel {

	public CustomDifficulty() {
		JSpinner length = new JSpinner();
		JSpinner minePercentage = new JSpinner();
		JLabel lengthLabel = new JLabel("Seitenlänge");
		JLabel minePercentageLabel = new JLabel("Anteil Minen (%)");
		
		JButton playButton = new JButton("Spielen");
		JButton backButton = new JButton("Zurück");
		
		playButton.addActionListener(e -> {
			
		});

		backButton.addActionListener(e -> {
			MwGUI.showPanel("menu");
		});

		this.add(lengthLabel);
		this.add(length);
		this.add(minePercentageLabel);
		this.add(minePercentage);
		this.add(playButton);
		this.add(backButton);
	}
}
