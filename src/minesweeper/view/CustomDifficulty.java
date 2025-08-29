package minesweeper.view;

import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class CustomDifficulty extends JFrame {

	private JPanel customDifficultyView;
	
	public CustomDifficulty() {
		JSpinner length = new JSpinner();
		JSpinner minePercentage = new JSpinner();
		JLabel lengthLabel = new JLabel("Seitenlänge");
		JLabel minePercentageLabel = new JLabel("Anteil Minen (%)");
		
		JButton playButton = new JButton("Spielen");
		customDifficultyView = new JPanel();
		
		customDifficultyView.add(lengthLabel);
		customDifficultyView.add(length);
		customDifficultyView.add(minePercentageLabel);
		customDifficultyView.add(minePercentage);
		customDifficultyView.add(playButton);
	}

	public JPanel getCustomDifficultyView() {
		return customDifficultyView;
	}

}
