package minesweeper.view;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainMenu extends JFrame {
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem exit;

	public MainMenu() {
		// copied from GPT
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        JPanel mainMenuView = new JPanel();
        // end of copy
        
		// TODO: this is duplicated in MwGUI.java
		this.setSize(600, 600);
		this.setLocation(300, 300);
		this.setTitle("Minesweeper");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// this.setLayout(new GridLayout(2,2));

		// Menu
		// TODO: extract this into independent method
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Datei");
		exit = new JMenuItem("Beenden");
		exit.addActionListener(e -> System.exit(0));

		// assign everything
		menuBar.add(fileMenu);
		fileMenu.add(exit);

		// add to window
		this.setJMenuBar(menuBar);

		// TODO: make proper bomb/field ratios
		JButton easy = new JButton("8 x 8 (10 Minen)");
		JButton medium = new JButton("13 x 13 (40 Minen)");
		JButton hard = new JButton("20 x 20 (100 Minen)");
		JButton custom = new JButton("Benutzerdefiniert");
        custom.addActionListener(e -> {
        	JPanel customDifficultyView = new CustomDifficulty().getCustomDifficultyView();
        	// start GPT
            cardPanel.add(customDifficultyView, "Custom");
            // end GPT
            cardLayout.show(cardPanel, "Custom");
        });
        
        mainMenuView.add(easy);
        mainMenuView.add(medium);
        mainMenuView.add(hard);
        mainMenuView.add(custom);
		
        // start GPT
        cardPanel.add(mainMenuView, "Main");
        
        this.add(cardPanel);
        // end GPT

		this.setVisible(true);
	}
}
