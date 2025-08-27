package minesweeper.view;

import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenu extends JFrame {
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem exit;

	public MainMenu() {
		// TODO: this is duplicated in MwGUI.java
		this.setSize(600, 600);
		this.setLocation(300, 300);
		this.setTitle("Minesweeper");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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

		this.setVisible(true);
	}
}
