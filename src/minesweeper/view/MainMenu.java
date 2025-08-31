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
		JButton easy = new JButton("8 x 8 (10 Minen)");
		easy.addActionListener(e -> new Minesweeper(8, 10));

		JButton medium = new JButton("13 x 13 (40 Minen)");
		medium.addActionListener(e -> new Minesweeper(13, 40));

		JButton hard = new JButton("20 x 20 (100 Minen)");
		hard.addActionListener(e -> new Minesweeper(20, 100));

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
}
