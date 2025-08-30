package minesweeper.view;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenu extends JPanel {
    
	public MainMenu() {

		// TODO: make proper bomb/field ratios
		JButton easy = new JButton("8 x 8 (10 Minen)");
		JButton medium = new JButton("13 x 13 (40 Minen)");
		JButton hard = new JButton("20 x 20 (100 Minen)");
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
