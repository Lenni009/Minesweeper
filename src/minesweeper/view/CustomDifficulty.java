package minesweeper.view;

import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;

public class CustomDifficulty extends JFrame {

	public CustomDifficulty() {
		// JFrame-Konfiguration
		this.setSize(600, 600);
		this.setLocation(300, 300);
		this.setTitle("Minesweeper");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JSpinner length = new JSpinner();
		JSpinner minePercentage = new JSpinner();
		JLabel lengthLabel = new JLabel("Seitenlänge");
		JLabel minePercentageLabel = new JLabel("Anteil Minen (%)");
		
		JButton playButton = new JButton("Spielen");
		
		add(lengthLabel);
		add(length);
		add(minePercentageLabel);
		add(minePercentage);
		add(playButton);
		
		this.setVisible(true);
	}
	
}
