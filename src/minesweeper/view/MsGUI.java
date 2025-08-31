package minesweeper.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class MsGUI extends JFrame {
	static MsGUI instance;
	
	static CardLayout cardLayout = new CardLayout();
    static JPanel cardPanel = new JPanel(cardLayout);
    
    static JLabel statusLabel;

    private MsGUI() {
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);

			// JFrame config
			this.setSize(600, 600);
			this.setLocation(300, 300);
			this.setTitle("Minesweeper");

			// TODO: extract menubar to make more dynamic
			// Menu
			JMenuBar menuBar = new JMenuBar();
			JMenu fileMenu = new JMenu("Datei");
			JMenuItem exit = new JMenuItem("Beenden");
			JMenu onlineMenu = new JMenu("Online");
			JMenu helpMenu = new JMenu("Hilfe");
			JSeparator sep = new JSeparator();
			statusLabel = new JLabel("");

			exit.addActionListener(e -> System.exit(0));

			// assign everything
			menuBar.add(fileMenu);
			menuBar.add(onlineMenu);
			menuBar.add(helpMenu);
			fileMenu.add(exit);
			menuBar.add(sep);
			menuBar.add(statusLabel);

			// add to window
			this.setJMenuBar(menuBar);

			this.add(cardPanel);

			this.setVisible(true);
    }
    
    // in case someone asks: this is the singleton pattern!
    public static MsGUI getInstance() {
        if(instance == null) {
        	instance = new MsGUI();
        }
        return instance;
    }
    
    public void addPanel(JPanel panel, String identifier) {
    	cardPanel.add(panel, identifier);
    }

    public void showPanel(String identifier) {
    	cardLayout.show(cardPanel, identifier);
    }
}
