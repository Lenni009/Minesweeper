package minesweeper.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MwGUI extends JFrame {
	static CardLayout cardLayout = new CardLayout();
    static JPanel cardPanel = new JPanel(cardLayout);

    public MwGUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// JFrame config
		this.setSize(600, 600);
		this.setLocation(300, 300);
		this.setTitle("Minesweeper");

		// Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Datei");
		JMenuItem exit = new JMenuItem("Beenden");
		JMenu onlineMenu = new JMenu("Online");
		JMenu helpMenu = new JMenu("Hilfe");
		exit.addActionListener(e -> {
			System.exit(0);
		});

		// assign everything
		menuBar.add(fileMenu);
		menuBar.add(onlineMenu);
		menuBar.add(helpMenu);
		fileMenu.add(exit);

		// add to window
		this.setJMenuBar(menuBar);

        this.add(cardPanel);

		this.setVisible(true);
    }
    
    public static void addPanel(JPanel panel, String identifier) {
    	cardPanel.add(panel, identifier);
    	// GPT generated
    	cardPanel.revalidate();
    	cardPanel.repaint();
    	// end GPT
    }

    public static void showPanel(String identifier) {
    	cardLayout.show(cardPanel, identifier);
    }
}
