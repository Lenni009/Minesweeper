package minesweeper.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import minesweeper.Minesweeper;

public class MainMenu extends JPanel {
    
	public MainMenu() {

		GridLayout menuGrid = new GridLayout(2, 2);
		this.setLayout(menuGrid);

		// TODO: make proper bomb/field ratios
		JButton easy = generatePlayButton(8, 10);
		JButton medium = generatePlayButton(13, 40);
		JButton hard = generatePlayButton(20, 100);

		JButton custom = new JButton("Benutzerdefiniert");
        custom.addActionListener(e -> {
        	JPanel customDifficultyView = new CustomDifficulty();
        	MwGUI.addPanel(customDifficultyView, "custom");
            MwGUI.showPanel("custom");
        });

        this.add(easy);
        this.add(medium);
        this.add(hard);
        this.add(custom);
	}

	private JButton generatePlayButton(int sideLength, int mines) {
		JButton playButton = new JButton(sideLength + " x " + sideLength + " (" + mines + ")");
		playButton.addActionListener(e -> new Minesweeper(sideLength, mines));
		return playButton;
	}
}
